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

import com.google.zxing.common.BitArray;

/**
 * @author Pablo Orduña, University of Deusto (pablo.orduna@deusto.es)
 */
abstract class AI01weightDecoder extends AI01decoder {

     AI01weightDecoder(BitArray information) {
        super(information);
    }

    protected void encodeCompressedWeight(StringBuffer buf, int currentPos, int weightSize) {
        int originalWeightNumeric = this.generalDecoder.extractNumericValueFromBitArray(currentPos, weightSize);
        addWeightCode(buf, originalWeightNumeric);
        int weightNumeric = checkWeight(originalWeightNumeric);
        int currentDivisor = 100000;
        for (int i = 0; i < 5; ++i) {
            if (weightNumeric / currentDivisor == 0) {
                buf.append('0');
            }
            currentDivisor /= 10;
        }
        buf.append(weightNumeric);
    }

    protected abstract void addWeightCode(StringBuffer buf, int weight);

    protected abstract int checkWeight(int weight);
}
