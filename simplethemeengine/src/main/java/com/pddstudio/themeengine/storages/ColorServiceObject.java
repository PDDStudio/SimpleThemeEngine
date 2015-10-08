package com.pddstudio.themeengine.storages;

import com.pddstudio.themeengine.OnThemeChangedListener;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.ThemeFontColor;

/**
 * This Class was created by Patrick J
 * on 16.09.15. For more Details and Licensing
 * have a look at the README.md
 */

public class ColorServiceObject implements OnThemeChangedListener{

    private final String identifier;
    private OnThemeChangedListener objectOnThemeChangedListener;

    private ColorServiceObject(String onIdentifier) {
        this.identifier = onIdentifier;
    }

    private ColorServiceObject(String onIdentifier, OnThemeChangedListener onThemeChangedListener) {
        this.identifier = onIdentifier;
        this.objectOnThemeChangedListener = onThemeChangedListener;
    }

    public ColorServiceObject() {
        this.identifier = null;
    }

    public ColorServiceObject withIdentifier(String identifier) {
        return new ColorServiceObject(identifier);
    }

    public ColorServiceObject withIdentifier(String identifier, OnThemeChangedListener objectOnThemeChangedListener) {
        return new ColorServiceObject(identifier, objectOnThemeChangedListener);
    }

    public String getIdentifier() {
        return identifier;
    }

    private boolean hasInterfaceAttached() {
        if(objectOnThemeChangedListener != null) return false;
        else return true;
    }

    public boolean hasInterface() {
        return true;
    }

    /*
     * The current OnThemeChangedListener Interface used by the activities.
     */

    @Override
    public void onPrimaryColorChanged() {
        if(hasInterfaceAttached()) objectOnThemeChangedListener.onPrimaryColorChanged();
    }

    @Override
    public void onPrimaryColorDarkChanged() {
        if(hasInterfaceAttached()) objectOnThemeChangedListener.onPrimaryColorDarkChanged();
    }

    @Override
    public void onAccentColorChanged() {
        if(hasInterfaceAttached()) objectOnThemeChangedListener.onAccentColorChanged();
    }

    @Override
    public void onFontColorChanged(ThemeFontColor themeFontColor) {
        if(hasInterfaceAttached()) objectOnThemeChangedListener.onFontColorChanged(themeFontColor);
    }

    @Override
    public void onApplicationStyleChanged(ApplicationTheme applicationTheme) {
        if(hasInterfaceAttached()) objectOnThemeChangedListener.onApplicationStyleChanged(applicationTheme);
    }

    public ColorServiceObject getColorServiceObject() {
        return this;
    }

    public OnThemeChangedListener getObjectOnThemeChangedListener() {
        return objectOnThemeChangedListener;
    }

    public void setObjectOnThemeChangedListener(OnThemeChangedListener objectOnThemeChangedListener) {
        this.objectOnThemeChangedListener = objectOnThemeChangedListener;
    }
}
