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
 */
abstract class AI013x0xDecoder extends AI01weightDecoder {

    private static final int headerSize = 4 + 1;

    private static final int weightSize = 15;

     AI013x0xDecoder(BitArray information) {
        super(information);
    }

    public String parseInformation() throws NotFoundException {
        if (this.information.size != headerSize + gtinSize + weightSize) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuffer buf = new StringBuffer();
        encodeCompressedGtin(buf, headerSize);
        encodeCompressedWeight(buf, headerSize + gtinSize, weightSize);
        return buf.toString();
    }
}
