package com.language.switcher;

import java.awt.event.KeyEvent;

public enum Key {
    COMMAND(KeyEvent.VK_META),
    WINDOWS(KeyEvent.VK_WINDOWS),
    SPACE(KeyEvent.VK_SPACE),
    SHIFT(KeyEvent.VK_SHIFT),
    CONTROL(KeyEvent.VK_CONTROL),
    ALT(KeyEvent.VK_ALT);

    private final int key;

    Key(int key) {
        this.key = key;
    }

    public int value() {
        return key;
    }
}
