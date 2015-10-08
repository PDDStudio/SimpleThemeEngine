package com.pddstudio.themeengine;

import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

/**
 * This Class was created by Patrick J
 * on 10.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public interface OnThemeChangedListener {
    void onPrimaryColorChanged();
    void onPrimaryColorDarkChanged();
    void onAccentColorChanged();
    void onFontColorChanged(ThemeFontColor themeFontColor);
    void onApplicationStyleChanged(ApplicationTheme applicationTheme);
}
