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
package com.google.zxing.client.rim;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiEngine;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

/**
 * The screen used to display the application 'about' information.
 *
 * This code was contributed by LifeMarks.
 *
 * @author Matt York (matt@lifemarks.mobi)
 */
final class AboutScreen extends MainScreen {

     AboutScreen() {
        setTitle(new LabelField("ZXing - About", DrawStyle.ELLIPSIS | USE_ALL_WIDTH));
        Manager vfm = new VerticalFieldManager(FIELD_HCENTER);
        Field title = new LabelField("ZXing - BlackBerry Client", FIELD_HCENTER);
        Field uri = new LabelField("http://code.google.com/p/zxing/", FIELD_HCENTER);
        vfm.add(title);
        vfm.add(uri);
        Field okButton = new ButtonField("OK", FIELD_HCENTER | ButtonField.CONSUME_CLICK);
        okButton.setChangeListener(new ButtonListener(this));
        vfm.add(okButton);
        add(vfm);
    }

    /**
   * Used to close the screen when the ok button is pressed.
   */
    private static class ButtonListener implements FieldChangeListener {

        private final Screen screen;

        private  ButtonListener(Screen screen) {
            this.screen = screen;
        }

        public void fieldChanged(Field field, int context) {
            UiEngine ui = Ui.getUiEngine();
            ui.popScreen(screen);
        }
    }
}
