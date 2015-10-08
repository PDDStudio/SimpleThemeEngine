package com.pddstudio.themeengine.data;

import com.pddstudio.themeengine.helpers.ThemeLogger;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

/**
 * This Class was created by Patrick J
 * on 12.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ThemeInfo {

    /**
     * Object to store current Theme informations.
     */

    private static ThemeInfo themeInfo;

    private ApplicationTheme currentApplicationTheme;
    private ThemeFontColor currentFontColor;

    private ThemeInfo() {}

    public void setCurrentApplicationTheme(ApplicationTheme applicationTheme) {
        ThemeLogger.addLogMessage(getClass(), "setCurrentApplicationTheme() called : value changed -> " + applicationTheme.toString());
        themeInfo.currentApplicationTheme = applicationTheme;

    }

    public void setCurrentFontColor(ThemeFontColor fontColor) {
        ThemeLogger.addLogMessage(getClass(), "setCurrentFontColor() called : value changed -> " + fontColor.toString());
        themeInfo.currentFontColor = fontColor;
    }

    public static void setThemeInfo(ApplicationTheme applicationTheme, ThemeFontColor themeFontColor) {
        themeInfo = new ThemeInfo();
        themeInfo.setCurrentApplicationTheme(applicationTheme);
        themeInfo.setCurrentFontColor(themeFontColor);
    }

    public static ThemeInfo getThemeInfo() {
        if(themeInfo == null) throw new NullPointerException("Can't return ThemeInfo. Instance is NULL");
        return themeInfo;
    }

    public static ApplicationTheme getCurrentApplicationTheme() {
        if(themeInfo == null) throw new NullPointerException("Can't return themeInfo's ApplicationTheme. ThemeInfo is NULL");
        return themeInfo.currentApplicationTheme;
    }

    public static ThemeFontColor getCurrentThemeFontColor() {
        if(themeInfo == null) throw new NullPointerException("Can't return themeInfo's current ThemeFontColor. ThemeInfo is NULL");
        return themeInfo.currentFontColor;
    }

    public static boolean exist() {
        if(themeInfo == null) return false;
        return true;
    }

}
