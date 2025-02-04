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
import junit.framework.TestCase;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 * @author Eduardo Castillejo, University of Deusto (eduardo.castillejo@deusto.es)
 */
public class FieldParserTest extends TestCase {

    private static void checkFields(String expected) throws NotFoundException {
        String field = expected.replace("(", "").replace(")", "");
        String actual = FieldParser.parseFieldsInGeneralPurpose(field);
        assertEquals(expected, actual);
    }

    public void testParseField() throws Exception {
        checkFields("(15)991231(3103)001750(10)12A");
    }

    public void testParseField2() throws Exception {
        checkFields("(15)991231(15)991231(3103)001750(10)12A");
    }
}
