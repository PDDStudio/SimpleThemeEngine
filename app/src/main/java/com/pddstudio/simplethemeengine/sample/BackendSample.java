package com.pddstudio.simplethemeengine.sample;

import com.pddstudio.themeengine.IBackendNotificationInterface;
import com.pddstudio.themeengine.data.ThemeObject;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

import io.paperdb.Paper;

/**
 * This Class was created by Patrick J
 * on 08.10.15. For more Details and Licensing
 * have a look at the README.md
 */
public class BackendSample {

    private static final String TE_THEME_OBJECT = "sample_te_obj";
    private static final String TE_FONT_COLOR = "sample_te_fc";
    private static final String TE_APP_THEME = "sample_te_at";

    public static final IBackendNotificationInterface BACKEND_NOTIFICATION_INTERFACE = new IBackendNotificationInterface() {
        @Override
        public void onThemeObjectChanged(ThemeObject themeObject) {
            Paper.put(TE_THEME_OBJECT, themeObject);
        }

        @Override
        public void onApplicationThemeObjectChanged(ApplicationTheme applicationTheme) {
            Paper.put(TE_APP_THEME, applicationTheme);
        }

        @Override
        public void onThemeFontColorObjectChanged(ThemeFontColor themeFontColor) {
            Paper.put(TE_FONT_COLOR, themeFontColor);
        }

        @Override
        public ThemeObject loadSavedThemeObject() {
            return Paper.get(TE_THEME_OBJECT, null);
        }

        @Override
        public ApplicationTheme loadSavedApplicationTheme() {
            return Paper.get(TE_APP_THEME, null);
        }

        @Override
        public ThemeFontColor loadSavedThemeFontColor() {
            return Paper.get(TE_FONT_COLOR, null);
        }
    };

}
