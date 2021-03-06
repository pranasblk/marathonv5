/*******************************************************************************
 * Copyright 2016 Jalian Systems Pvt. Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sourceforge.marathon.javafxagent;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;

@SuppressWarnings("deprecation") public class KeyboardMap {

    private char c;

    private static final class KeyboardMapEntry {
        public int modifiersEx;
        public char c;
        public int keyCode;

        public KeyboardMapEntry(int m, char c, int keyCode) {
            modifiersEx = m;
            this.c = c;
            this.keyCode = keyCode;
        }
    }

    private static Map<Character, KeyboardMapEntry> keyMap;

    static {
        keyMap = new HashMap<Character, KeyboardMap.KeyboardMapEntry>();
        for (char c = 'a'; c <= 'z'; c++) {
            keyMap.put(c, new KeyboardMapEntry(0, c, KeyCode.A.impl_getCode() + c - 'a'));
            keyMap.put(Character.toUpperCase(c), new KeyboardMapEntry(KeyCode.SHIFT.impl_getCode(), Character.toUpperCase(c),
                    KeyCode.A.impl_getCode() + c - 'a'));
        }
        for (char c = '0'; c < '9'; c++) {
            keyMap.put(c, new KeyboardMapEntry(0, c, KeyCode.DIGIT0.impl_getCode() + c - '0'));
        }
        keyMap.put('\n', new KeyboardMapEntry(0, '\n', KeyCode.ENTER.impl_getCode()));
    }

    public KeyboardMap(char c) {
        this.c = c;
    }

    public int getModifiersEx() {
        KeyboardMapEntry entry = keyMap.get(c);
        if (entry == null) {
            return 0;
        }
        return entry.modifiersEx;
    }

    public char getChar() {
        KeyboardMapEntry entry = keyMap.get(c);
        if (entry == null) {
            return c;
        }
        return entry.c;
    }

    public int getKeyCode() {
        KeyboardMapEntry entry = keyMap.get(c);
        if (entry == null) {
            return KeyCode.UNDEFINED.impl_getCode();
        }
        return entry.keyCode;
    }

}
