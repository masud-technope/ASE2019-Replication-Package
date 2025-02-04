/*
 * Copyright 2010 ZXing authors
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

import java.util.Hashtable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;

/**
 * <p>Decodes Code 93 barcodes.</p>
 *
 * @author Sean Owen
 * @see Code39Reader
 */
public final class Code93Reader extends OneDReader {

    // Note that 'abcd' are dummy characters in place of control characters.
    private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";

    private static final char[] ALPHABET = ALPHABET_STRING.toCharArray();

    /**
   * These represent the encodings of characters, as patterns of wide and narrow bars.
   * The 9 least-significant bits of each int correspond to the pattern of wide and narrow.
   */
    private static final int[] CHARACTER_ENCODINGS = { // 0-9
    0x114, // 0-9
    0x148, // 0-9
    0x144, // 0-9
    0x142, // 0-9
    0x128, // 0-9
    0x124, // 0-9
    0x122, // 0-9
    0x150, // 0-9
    0x112, // 0-9
    0x10A, // A-J
    0x1A8, // A-J
    0x1A4, // A-J
    0x1A2, // A-J
    0x194, // A-J
    0x192, // A-J
    0x18A, // A-J
    0x168, // A-J
    0x164, // A-J
    0x162, // A-J
    0x134, // K-T
    0x11A, // K-T
    0x158, // K-T
    0x14C, // K-T
    0x146, // K-T
    0x12C, // K-T
    0x116, // K-T
    0x1B4, // K-T
    0x1B2, // K-T
    0x1AC, // K-T
    0x1A6, // U-Z
    0x196, // U-Z
    0x19A, // U-Z
    0x16C, // U-Z
    0x166, // U-Z
    0x136, // U-Z
    0x13A, // - - %
    0x12E, // - - %
    0x1D4, // - - %
    0x1D2, // - - %
    0x1CA, // - - %
    0x16E, // - - %
    0x176, // - - %
    0x1AE, // Control chars? $-*
    0x126, // Control chars? $-*
    0x1DA, // Control chars? $-*
    0x1D6, // Control chars? $-*
    0x132, // Control chars? $-*
    0x15E };

    private static final int ASTERISK_ENCODING = CHARACTER_ENCODINGS[47];

    public Result decodeRow(int rowNumber, BitArray row, Hashtable hints) throws NotFoundException, ChecksumException, FormatException {
        int[] start = findAsteriskPattern(row);
        int nextStart = start[1];
        int end = row.getSize();
        // Read off white space
        while (nextStart < end && !row.get(nextStart)) {
            nextStart++;
        }
        StringBuffer result = new StringBuffer(20);
        int[] counters = new int[6];
        char decodedChar;
        int lastStart;
        do {
            recordPattern(row, nextStart, counters);
            int pattern = toPattern(counters);
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
        // Should be at least one more black module
        if (nextStart == end || !row.get(nextStart)) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (result.length() < 2) {
            // Almost surely a false positive
            throw NotFoundException.getNotFoundInstance();
        }
        checkChecksums(result);
        // Remove checksum digits
        result.setLength(result.length() - 2);
        String resultString = decodeExtended(result);
        float left = (float) (start[1] + start[0]) / 2.0f;
        float right = (float) (nextStart + lastStart) / 2.0f;
        return new Result(resultString, null, new ResultPoint[] { new ResultPoint(left, (float) rowNumber), new ResultPoint(right, (float) rowNumber) }, BarcodeFormat.CODE_93);
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
        int[] counters = new int[6];
        int patternStart = rowOffset;
        boolean isWhite = false;
        int patternLength = counters.length;
        for (int i = rowOffset; i < width; i++) {
            boolean pixel = row.get(i);
            if (pixel ^ isWhite) {
                counters[counterPosition]++;
            } else {
                if (counterPosition == patternLength - 1) {
                    if (toPattern(counters) == ASTERISK_ENCODING) {
                        return new int[] { patternStart, i };
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

    private static int toPattern(int[] counters) {
        int max = counters.length;
        int sum = 0;
        for (int i = 0; i < max; i++) {
            sum += counters[i];
        }
        int pattern = 0;
        for (int i = 0; i < max; i++) {
            int scaledShifted = (counters[i] << INTEGER_MATH_SHIFT) * 9 / sum;
            int scaledUnshifted = scaledShifted >> INTEGER_MATH_SHIFT;
            if ((scaledShifted & 0xFF) > 0x7F) {
                scaledUnshifted++;
            }
            if (scaledUnshifted < 1 || scaledUnshifted > 4) {
                return -1;
            }
            if ((i & 0x01) == 0) {
                for (int j = 0; j < scaledUnshifted; j++) {
                    pattern = (pattern << 1) | 0x01;
                }
            } else {
                pattern <<= scaledUnshifted;
            }
        }
        return pattern;
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
            if (c >= 'a' && c <= 'd') {
                char next = encoded.charAt(i + 1);
                char decodedChar = '\0';
                switch(c) {
                    case 'd':
                        // +A to +Z map to a to z
                        if (next >= 'A' && next <= 'Z') {
                            decodedChar = (char) (next + 32);
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case 'a':
                        // $A to $Z map to control codes SH to SB
                        if (next >= 'A' && next <= 'Z') {
                            decodedChar = (char) (next - 64);
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case 'b':
                        // %A to %E map to control codes ESC to US
                        if (next >= 'A' && next <= 'E') {
                            decodedChar = (char) (next - 38);
                        } else if (next >= 'F' && next <= 'W') {
                            decodedChar = (char) (next - 11);
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                        break;
                    case 'c':
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

    private static void checkChecksums(StringBuffer result) throws ChecksumException {
        int length = result.length();
        checkOneChecksum(result, length - 2, 20);
        checkOneChecksum(result, length - 1, 15);
    }

    private static void checkOneChecksum(StringBuffer result, int checkPosition, int weightMax) throws ChecksumException {
        int weight = 1;
        int total = 0;
        for (int i = checkPosition - 1; i >= 0; i--) {
            total += weight * ALPHABET_STRING.indexOf(result.charAt(i));
            if (++weight > weightMax) {
                weight = 1;
            }
        }
        if (result.charAt(checkPosition) != ALPHABET[total % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
