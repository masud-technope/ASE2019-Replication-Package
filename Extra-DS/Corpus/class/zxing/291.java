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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.common.AbstractBlackBoxTestCase;

public class RSSExpandedBlackBox2TestCase extends AbstractBlackBoxTestCase {

    public  RSSExpandedBlackBox2TestCase() {
        super("test/data/blackbox/rssexpanded-2", new MultiFormatReader(), BarcodeFormat.RSS_EXPANDED);
        addTest(21, 23, 0.0f);
        addTest(19, 23, 180.0f);
    }
}
