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
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import junit.framework.TestCase;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 */
public final class BinaryUtilTest extends TestCase {

    public void testBuildBitArrayFromString() {
        String data = " ..X..X.. ..XXX... XXXXXXXX ........";
        check(data);
        data = " XXX..X..";
        check(data);
        data = " XX";
        check(data);
        data = " ....XX.. ..XX";
        check(data);
        data = " ....XX.. ..XX..XX ....X.X. ........";
        check(data);
    }

    private static void check(String data) {
        BitArray binary = BinaryUtil.buildBitArrayFromString(data);
        assertEquals(data, binary.toString());
    }

    public void testBuildBitArrayFromStringWithoutSpaces() {
        String data;
        data = " ..X..X.. ..XXX... XXXXXXXX ........";
        checkWithoutSpaces(data);
        data = " XXX..X..";
        checkWithoutSpaces(data);
        data = " XX";
        checkWithoutSpaces(data);
        data = " ....XX.. ..XX";
        checkWithoutSpaces(data);
        data = " ....XX.. ..XX..XX ....X.X. ........";
        checkWithoutSpaces(data);
    }

    private static void checkWithoutSpaces(String data) {
        String dataWithoutSpaces = data.replaceAll(" ", "");
        BitArray binary = BinaryUtil.buildBitArrayFromStringWithoutSpaces(dataWithoutSpaces);
        assertEquals(data, binary.toString());
    }
}
