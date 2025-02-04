/*
 * Copyright 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Hashtable;

/**
 * <p>Decodes Code 39 barcodes. This does not support "Full ASCII Code 39" yet.</p>
 *
 * @author Sean Owen
 * @see Code93Reader
 */
public final class Code39Reader extends OneDReader {

    static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";

    private static final char[] ALPHABET = ALPHABET_STRING.toCharArray();

    /**
   * These represent the encodings of characters, as patterns of wide and narrow bars.
   * The 9 least-significant bits of each int correspond to the pattern of wide and narrow,
   * with 1s representing "wide" and 0s representing narrow.
   */
    static final int[] CHARACTER_ENCODINGS = { // 0-9
    0x034, // 0-9
    0x121, // 0-9
    0x061, // 0-9
    0x160, // 0-9
    0x031, // 0-9
    0x130, // 0-9
    0x070, // 0-9
    0x025, // 0-9
    0x124, // 0-9
    0x064, // A-J
    0x109, // A-J
    0x049, // A-J
    0x148, // A-J
    0x019, // A-J
    0x118, // A-J
    0x058, // A-J
    0x00D, // A-J
    0x10C, // A-J
    0x04C, // A-J
    0x01C, // K-T
    0x103, // K-T
    0x043, // K-T
    0x142, // K-T
    0x013, // K-T
    0x112, // K-T
    0x052, // K-T
    0x007, // K-T
    0x106, // K-T
    0x046, // K-T
    0x016, // U-*
    0x181, // U-*
    0x0C1, // U-*
    0x1C0, // U-*
    0x091, // U-*
    0x190, // U-*
    0x0D0, // U-*
    0x085, // U-*
    0x184, // U-*
    0x0C4, // U-*
    0x094, // $-%
    0x0A8, // $-%
    0x0A2, // $-%
    0x08A, // $-%
    0x02A };

    private static final int ASTERISK_ENCODING = CHARACTER_ENCODINGS[39];

    private final boolean usingCheckDigit;

    private final boolean extendedMode;

    /**
   * Creates a reader that assumes all encoded data is data, and does not treat the final
   * character as a check digit. It will not decoded "extended Code 39" sequences.
   */
    public  Code39Reader() {
        usingCheckDigit = false;
        extendedMode = false;
    }

    /**
   * Creates a reader that can be configured to check the last character as a check digit.
   * It will not decoded "extended Code 39" sequences.
   *
   * @param usingCheckDigit if true, treat the last data character as a check digit, not
   * data, and verify that the checksum passes.
   */
    public  Code39Reader(boolean usingCheckDigit) {
        this.usingCheckDigit = usingCheckDigit;
        this.extendedMode = false;
    }

    /**
   * Creates a reader that can be configured to check the last character as a check digit,
   * or optionally attempt to decode "extended Code 39" sequences that are used to encode
   * the full ASCII character set.
   *
   * @param usingCheckDigit if true, treat the last data character as a check digit, not
   * data, and verify that the checksum passes.
   * @param extendedMode if true, will attempt to decode extended Code 39 sequences in the
   * text.
   */
    public  Code39Reader(boolean usingCheckDigit, boolean extendedMode) {
        this.usingCheckDigit = usingCheckDigit;
        this.extendedMode = extendedMode;
    }

    public Result decodeRow(int rowNumber, BitArray row, Hashtable hints) throws NotFoundException, ChecksumException, FormatException {
        int[] start = findAsteriskPattern(row);
        int nextStart = start[1];
        int end = row.getSize();
        // Read off white space
        while (nextStart < end && !row.get(nextStart)) {
            nextStart++;
        }
        StringBuffer result = new StringBuffer(20);
        int[] counters = new int[9];
        char decodedChar;
        int lastStart;
        do {
            recordPattern(row, nextStart, counters);
            int pattern = toNarrowWidePattern(counters);
            if (pattern < 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            decodedChar = patternToChar(pattern);
            result.append(decodedChar);
            lastStart = nextStart;
            for (int i = 0; i < counters.length; i++) {
                nextStart += counters[i];
            }
            // Read off white space
            while (nextStart < end && !row.get(nextStart)) {
                nextStart++;
            }
        } while (decodedChar != '*');
        // remove asterisk
        result.deleteCharAt(result.length() - 1);
        // Look for whitespace after pattern:
        int lastPatternSize = 0;
        for (int i = 0; i < counters.length; i++) {
            lastPatternSize += counters[i];
        }
        int whiteSpaceAfterEnd = nextStart - lastStart - lastPatternSize;
        // (but if it's whitespace to the very end of the image, that's OK)
        if (nextStart != end && whiteSpaceAfterEnd / 2 < lastPatternSize) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (usingCheckDigit) {
            int max = result.length() - 1;
            int total = 0;
            for (int i = 0; i < max; i++) {
                total += ALPHABET_STRING.indexOf(result.charAt(i));
            }
            if (result.charAt(max) != ALPHABET[total % 43]) {
                throw ChecksumException.getChecksumInstance();
            }
            result.deleteCharAt(max);
        }
        if (result.length() == 0) {
            // Almost surely a false positive
            throw NotFoundException.getNotFoundInstance();
        }
        String resultString;
        if (extendedMode) {
            resultString = decodeExtended(result);
        } else {
            resultString = result.toString();
        }
        float left = (float) (start[1] + start[0]) / 2.0f;
        float right = (float) (nextStart + lastStart) / 2.0f;
        return new Result(resultString, null, new ResultPoint[] { new ResultPoint(left, (float) rowNumber), new ResultPoint(right, (float) rowNumber) }, BarcodeFormat.CODE_39);
    }

    private static int[] findAsteriskPattern(BitArray row) throws NotFoundException {
        int width = row.getSize();
        int rowOffset = 0;
        while (rowOffset < width) {
            if (row.get(rowOffset)) {
                break;
            }
            rowOffset++;
        }
        int counterPosition = 0;
        int[] counters = new int[9];
        int patternStart = rowOffset;
        boolean isWhite = false;
        int patternLength = counters.length;
        for (int i = rowOffset; i < width; i++) {
            boolean pixel = row.get(i);
            if (pixel ^ isWhite) {
                counters[counterPosition]++;
            } else {
                if (counterPosition == patternLength - 1) {
                    if (toNarrowWidePattern(counters) == ASTERISK_ENCODING) {
                        // Look for whitespace before start pattern, >= 50% of width of start pattern
                        if (row.isRange(Math.max(0, patternStart - (i - patternStart) / 2), patternStart, false)) {
                            return new int[] { patternStart, i };
                        }
                    }
                    patternStart += counters[0] + counters[1];
                    for (int y = 2; y < patternLength; y++) {
                        counters[y - 2] = counters[y];
                    }
                    counters[patternLength - 2] = 0;
                    counters[patternLength - 1] = 0;
                    counterPosition--;
                } else {
                    counterPosition++;
                }
                counters[counterPosition] = 1;
                isWhite = !isWhite;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    // For efficiency, returns -1 on failure. Not throwing here saved as many as 700 exceptions
    // per image when using some of our blackbox images.
    private static int toNarrowWidePattern(int[] counters) {
        int numCounters = counters.length;
        int maxNarrowCounter = 0;
        int wideCounters;
        do {
            int minCounter = Integer.MAX_VALUE;
            for (int i = 0; i < numCounters; i++) {
                int counter = counters[i];
                if (counter < minCounter && counter > maxNarrowCounter) {
                    minCounter = counter;
                }
            }
            maxNarrowCounter = minCounter;
            wideCounters = 0;
            int totalWideCountersWidth = 0;
            int pattern = 0;
            for (int i = 0; i < numCounters; i++) {
                int counter = counters[i];
                if (counters[i] > maxNarrowCounter) {
                    pattern |= 1 << (numCounters - 1 - i);
                    wideCounters++;
                    totalWideCountersWidth += counter;
                }
            }
            if (wideCounters == 3) {
                // counter is more than 1.5 times the average:
                for (int i = 0; i < numCounters && wideCounters > 0; i++) {
                    int counter = counters[i];
                    if (counters[i] > maxNarrowCounter) {
                        wideCounters--;
                        // totalWideCountersWidth = 3 * average, so this checks if counter >= 3/2 * average
                        if ((counter << 1) >= totalWideCountersWidth) {
                            return -1;
                        }
                    }
                }
                return pattern;
            }
        } while (wideCounters > 3);
        return -1;
    }

    private static char patternToChar(int pattern) throws NotFoundException {
        for (int i = 0; i < CHARACTER_ENCODINGS.length; i++) {
            if (CHARACTER_ENCODINGS[i] == pattern) {
                return ALPHABET[i];
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String decodeExtended(StringBuffer encoded) throws FormatException {
        int length = encoded.length();
        StringBuffer decoded = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            char c = encoded.charAt(i);
            if (c == '+' || c == '$' || c == '%' || c == '/') {
                char next = encoded.charAt(i + 1);
                char decodedChar = '\0';
                switch(c) {
                    case '+':
                        // +A to +Z map to a to z
                        if (next >= 'A' && next <= 'Z') {
                            decodedChar = (char) (next + 32);
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case '$':
                        // $A to $Z map to control codes SH to SB
                        if (next >= 'A' && next <= 'Z') {
                            decodedChar = (char) (next - 64);
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case '%':
                        // %A to %E map to control codes ESC to US
                        if (next >= 'A' && next <= 'E') {
                            decodedChar = (char) (next - 38);
                        } else if (next >= 'F' && next <= 'W') {
                            decodedChar = (char) (next - 11);
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case '/':
                        // /A to /O map to ! to , and /Z maps to :
                        if (next >= 'A' && next <= 'O') {
                            decodedChar = (char) (next - 32);
                        } else if (next == 'Z') {
                            decodedChar = ':';
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                }
                decoded.append(decodedChar);
                // bump up i again since we read two characters
                i++;
            } else {
                decoded.append(c);
            }
        }
        return decoded.toString();
    }
}
