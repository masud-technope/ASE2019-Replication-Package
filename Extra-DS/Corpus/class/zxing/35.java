/*
 * Copyright 2009 ZXing authors
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
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import junit.framework.TestCase;

/**
 * @author Ari Pollak
 */
public final class EAN8WriterTestCase extends TestCase {

    public void testEncode() throws WriterException {
        String testStr = "0001010001011010111101111010110111010101001110111001010001001011100101000";
        BitMatrix result = new EAN8Writer().encode("96385074", BarcodeFormat.EAN_8, testStr.length(), 0);
        for (int i = 0; i < testStr.length(); i++) {
            assertEquals("Element " + i, testStr.charAt(i) == '1', result.get(i, 0));
        }
    }
}
