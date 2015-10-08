package com.pddstudio.themeengine;

import com.pddstudio.themeengine.data.ThemeObject;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

/**
 * This Class was created by Patrick J
 * on 15.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public interface IBackendNotificationInterface {

    //this function will be called in case the ThemeObject has changed
    void onThemeObjectChanged(ThemeObject themeObject);
    //this function will be called in case the ApplicationTheme has changed
    void onApplicationThemeObjectChanged(ApplicationTheme applicationTheme);
    //this function will be called in case the ThemeFontColor has changed
    void onThemeFontColorObjectChanged(ThemeFontColor themeFontColor);

    //function to load the saved ThemeObject - returns null if nothing exists
    ThemeObject loadSavedThemeObject();
    //function to load the saved ApplicationTheme - returns null if nothing exists
    ApplicationTheme loadSavedApplicationTheme();
    //function to load the saved ThemeFontColor - returns null if nothing exists
    ThemeFontColor loadSavedThemeFontColor();

}
