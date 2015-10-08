package com.pddstudio.themeengine.types;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * This Class was created by Patrick J
 * on 08.10.15. For more Details and Licensing
 * have a look at the README.md
 */
public enum ThemeFontColor {

    /**
     * Enum to represent the state of the current Theme color.
     * E.g by using ApplicationTheme.THEME_DARK using LIGHT_FONT_COLOR or ACCENT_FONT_COLOR will
     * change the font color to the specified color for continuous readability.
     */

    LIGHT_FONT_COLOR(Color.WHITE),
    DARK_FONT_COLOR(Color.BLACK),
    ACCENT_FONT_COLOR(Color.WHITE);

    private int mColor;

    ThemeFontColor(@ColorInt int color) {
        this.mColor = color;
    }

    public int getColorValue() {
        return mColor;
    }

    public void setColorValue(@ColorInt int colorValue) {
        this.mColor = colorValue;
    }

}
