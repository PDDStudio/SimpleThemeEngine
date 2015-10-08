package com.pddstudio.themeengine.data;

import android.support.annotation.ColorInt;

/**
 * This Class was created by Patrick J
 * on 09.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ThemeObject {

    //values for the application's main colors
    private int primaryColor;
    private int primaryColorDark;
    private int accentColor;
    //misc colors for the application
    private int applicationFontColor;
    private int applicationBackgroundColor;


    public ThemeObject(int primaryColor, int primaryColorDark, int accentColor, int themeBgColor, int fontColor) {
        this.primaryColor = primaryColor;
        this.primaryColorDark = primaryColorDark;
        this.accentColor = accentColor;
        this.applicationFontColor = fontColor;
        this.applicationBackgroundColor = themeBgColor;
    }

    public ThemeObject() {}

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getPrimaryColorDark() {
        return primaryColorDark;
    }

    public int getAccentColor() {
        return accentColor;
    }

    public int getApplicationFontColor() { return applicationFontColor; }

    public int getApplicationBackgroundColor() { return applicationBackgroundColor; }

    public void setPrimaryColor(@ColorInt int color) {
        this.primaryColor = color;
    }

    public void setPrimaryColorDark(@ColorInt int colorDark) {
        this.primaryColorDark = colorDark;
    }

    public void setAccentColor(@ColorInt int colorAccent) {
        this.accentColor = colorAccent;
    }

    public void setApplicationFontColor(@ColorInt int fontColor) { this.applicationFontColor = fontColor; }

    public void setApplicationBackgroundColor(@ColorInt int backgroundColor) { this.applicationBackgroundColor = backgroundColor; }

}
