package com.pddstudio.themeengine.data;

import com.pddstudio.themeengine.ThemeEngine;
import com.pddstudio.themeengine.helpers.ThemeDefaults;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

/**
 * This Class was created by Patrick J
 * on 15.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ThemeSettingsObject {

    private static ThemeSettingsObject themeSettingsObject;

    private ThemeObject currentThemeObject;
    private ApplicationTheme currentApplicationTheme;
    private ThemeFontColor currentThemeFontColor;

    private ThemeSettingsObject(ThemeObject themeObject, ApplicationTheme applicationTheme, ThemeFontColor themeFontColor) {
        this.currentThemeObject = themeObject;
        this.currentThemeFontColor = themeFontColor;
        this.currentApplicationTheme = applicationTheme;
        ThemeEngine.callBackendNotificationInterface().onThemeObjectChanged(this.currentThemeObject);
        ThemeEngine.callBackendNotificationInterface().onApplicationThemeObjectChanged(this.currentApplicationTheme);
        ThemeEngine.callBackendNotificationInterface().onThemeFontColorObjectChanged(this.currentThemeFontColor);
    }

    public static boolean hasInstance() {
        if(themeSettingsObject == null) return false;
        return true;
    }

    private static void missingInstances() {
        throw new NullPointerException("unable to create ThemeSettingsObject! Missing custom values and unable to find fallback values");
    }

    public static void create() {
        if(ThemeDefaults.hasDefaultThemeObject() && ThemeDefaults.hasDefaultApplicationTheme() && ThemeDefaults.hasDefaultApplicationFontColor()) {
            themeSettingsObject = new ThemeSettingsObject(ThemeDefaults.getDefaultThemeObject(), ThemeDefaults.getDefaultApplicationTheme(), ThemeDefaults.getDefaultApplicationFontColor());
        } else {
            missingInstances();
        }
    }

    public static void create(ThemeObject themeObject) {
        if(ThemeDefaults.hasDefaultApplicationFontColor() && ThemeDefaults.hasDefaultApplicationTheme()) {
            themeSettingsObject = new ThemeSettingsObject(themeObject, ThemeDefaults.getDefaultApplicationTheme(), ThemeDefaults.getDefaultApplicationFontColor());
        } else {
            missingInstances();
        }
    }

    public static void create(ThemeObject themeObject, ApplicationTheme applicationTheme) {
        if(ThemeDefaults.hasDefaultApplicationFontColor()) {
            themeSettingsObject = new ThemeSettingsObject(themeObject, applicationTheme, ThemeDefaults.getDefaultApplicationFontColor());
        } else {
            missingInstances();
        }
    }

    public static void create(ThemeObject themeObject, ApplicationTheme applicationTheme, ThemeFontColor themeFontColor) {
        themeSettingsObject = new ThemeSettingsObject(themeObject, applicationTheme, themeFontColor);
    }

    public static ThemeSettingsObject current() {
        if(themeSettingsObject == null) throw new NullPointerException("Current ThemeSettingsObject isn't initialized! Unable to access {null}");
        return themeSettingsObject;
    }

    public ThemeObject getThemeObject() {
        return themeSettingsObject.currentThemeObject;
    }

    public void setThemeObject(ThemeObject newThemeObject) {
        themeSettingsObject.currentThemeObject = newThemeObject;
        ThemeEngine.callBackendNotificationInterface().onThemeObjectChanged(newThemeObject);
    }

    public ApplicationTheme getApplicationTheme() {
        return themeSettingsObject.currentApplicationTheme;
    }

    public void setApplicationTheme(ApplicationTheme newApplicationTheme) {
        themeSettingsObject.currentApplicationTheme = newApplicationTheme;
        ThemeEngine.callBackendNotificationInterface().onApplicationThemeObjectChanged(newApplicationTheme);
    }

    public ThemeFontColor getThemeFontColor() {
        return themeSettingsObject.currentThemeFontColor;
    }

    public void setThemeFontColor(ThemeFontColor newThemeFontColor) {
        themeSettingsObject.currentThemeFontColor = newThemeFontColor;
        ThemeEngine.callBackendNotificationInterface().onThemeFontColorObjectChanged(newThemeFontColor);
    }

}

