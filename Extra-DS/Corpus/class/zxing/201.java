/*
 * Copyright (C) 2010 ZXing authors
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
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 */
public class AI01_3103_DecoderTest extends AbstractDecoderTest {

    private static final String header = "..X..";

    public void test01_3103_1() throws Exception {
        String data = header + compressedGtin_900123456798908 + compressed15bitWeight_1750;
        String expected = "(01)90012345678908(3103)001750";
        assertCorrectBinaryString(data, expected);
    }

    public void test01_3103_2() throws Exception {
        String data = header + compressedGtin_900000000000008 + compressed15bitWeight_0;
        String expected = "(01)90000000000003(3103)000000";
        assertCorrectBinaryString(data, expected);
    }

    public void test01_3103_invalid() throws Exception {
        String data = header + compressedGtin_900123456798908 + compressed15bitWeight_1750 + "..";
        try {
            assertCorrectBinaryString(data, "");
            fail(NotFoundException.class.getName() + " expected");
        } catch (NotFoundException nfe) {
        }
    }
}
