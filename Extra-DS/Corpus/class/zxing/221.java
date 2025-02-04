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
import com.google.zxing.common.BitArray;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 * @author Eduardo Castillejo, University of Deusto (eduardo.castillejo@deusto.es)
 */
final class AI01AndOtherAIs extends AI01decoder {

    //first bit encodes the linkage flag,
    private static final int HEADER_SIZE = 1 + 1 + 2;

    //the second one is the encodation method, and the other two are for the variable length
     AI01AndOtherAIs(BitArray information) {
        super(information);
    }

    public String parseInformation() throws NotFoundException {
        StringBuffer buff = new StringBuffer();
        buff.append("(01)");
        int initialGtinPosition = buff.length();
        int firstGtinDigit = this.generalDecoder.extractNumericValueFromBitArray(HEADER_SIZE, 4);
        buff.append(firstGtinDigit);
        this.encodeCompressedGtinWithoutAI(buff, HEADER_SIZE + 4, initialGtinPosition);
        return this.generalDecoder.decodeAllCodes(buff, HEADER_SIZE + 44);
    }
}
