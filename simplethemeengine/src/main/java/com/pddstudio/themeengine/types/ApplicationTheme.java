package com.pddstudio.themeengine.types;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * This Class was created by Patrick J
 * on 08.10.15. For more Details and Licensing
 * have a look at the README.md
 */
public enum ApplicationTheme {

    /**
     * The ApplicationTheme enum is for providing the possibility to switch from light <--> dark theme and vice versa.
     */

    //TODO: add matching colors for application theme
    THEME_LIGHT(Color.WHITE),
    THEME_DARK(Color.BLACK),
    THEME_CUSTOM(Color.WHITE);

    private int mColor;

    ApplicationTheme(@ColorInt int color) {
        this.mColor = color;
    }

    public int getColorValue() {
        return mColor;
    }

    public void setColorValue(@ColorInt int color) { this.mColor = color; }

}
