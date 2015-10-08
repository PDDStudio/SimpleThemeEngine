package com.pddstudio.themeengine.helpers;


import com.pddstudio.themeengine.data.ThemeObject;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

/**
 * This Class was created by Patrick J
 * on 09.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ThemeDefaults {

    /**
     * static functions which create the called default object
     */


    private static ThemeObject defaultThemeObject;
    private static ApplicationTheme defaultApplicationTheme;
    private static ThemeFontColor defaultApplicationFontColor;

    public static void setDefaultThemeObject(ThemeObject themeObject) {
        defaultThemeObject = themeObject;
        ThemeLogger.addLogMessage(ThemeDefaults.class, "setDefaultThemes() : assigned fallback ThemeObject");
    }

    public static ThemeObject getDefaultThemeObject() {
        if(defaultThemeObject == null) {
            throw new NullPointerException("Can't return default ThemeObject!");
        }
        return defaultThemeObject;
    }

    public static boolean hasDefaultThemeObject() {
        if(defaultThemeObject == null) return false;
        return true;
    }

    public static void setDefaultApplicationTheme(ApplicationTheme applicationTheme) {
        defaultApplicationTheme = applicationTheme;
        ThemeLogger.addLogMessage(ThemeDefaults.class, "setDefaultApplicationTheme() : assigned fallabck value -> " + applicationTheme.toString());
    }

    public static ApplicationTheme getDefaultApplicationTheme() {
        if(defaultApplicationTheme == null) throw new NullPointerException("unable to return fallback ApplicationTheme! It's null.");
        return defaultApplicationTheme;
    }

    public static boolean hasDefaultApplicationTheme() {
        if(defaultApplicationTheme == null) return false;
        return true;
    }

    public static void setDefaultApplicationFontColor(ThemeFontColor themeFontColor) {
        defaultApplicationFontColor = themeFontColor;
        ThemeLogger.addLogMessage(ThemeDefaults.class, "setDefaultApplicationFontColor() : assigned fallback value -> " + themeFontColor.toString());
    }

    public static ThemeFontColor getDefaultApplicationFontColor() {
        if(defaultApplicationFontColor == null) throw new NullPointerException("unable to return fallback ThemeFontColor. Variable is null!");
        return defaultApplicationFontColor;
    }

    public static boolean hasDefaultApplicationFontColor() {
        if(defaultApplicationFontColor == null) return false;
        return true;
    }

}

