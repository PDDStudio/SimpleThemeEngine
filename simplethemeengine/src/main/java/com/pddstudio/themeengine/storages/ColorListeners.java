package com.pddstudio.themeengine.storages;


import com.pddstudio.themeengine.OnThemeChangedListener;
import com.pddstudio.themeengine.ThemeEngine;
import com.pddstudio.themeengine.helpers.ThemeLogger;
import com.pddstudio.themeengine.types.CallAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 14.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ColorListeners extends HashMap<String, OnThemeChangedListener> {

    private static ColorListeners colorListeners;
    private final List<ColorServiceObject> colorServiceObjectList;

    private ColorListeners() {
        ThemeLogger.addLogMessage(ColorListeners.class, "getColorListeners() : ColorListeners singleton instance created.");
        colorServiceObjectList = new ArrayList<>();
    }

    /**
     * private function to be sure the singleton is initialized. If not this will throw a NullPointerException.
     */
    private static void validateInstance() {
        if (!isInitialized())
            throw new NullPointerException("colorListener singleton is not initialized!");
    }


    /**
     * function to check weather the singleton instance is initialized or not.
     *
     * @return true if singleton exists - false if not
     */
    public static boolean isInitialized() {
        if (colorListeners == null) return false;
        return true;
    }

    /**
     * function to return the singleton instance. In case if the instance is null a new instance will be created.
     *
     * @return ColorListeners singleton
     */
    public static ColorListeners getColorListeners() {
        if (colorListeners == null) colorListeners = new ColorListeners();
        return colorListeners;
    }

    /**
     * function to initialize the singleton instance (called on ThemeEngine's constructor)
     */
    public static void createSingleton(boolean overrideIfExist) {
        if (overrideIfExist) {
            colorListeners = new ColorListeners();
        } else {
            try {
                throw new Exception("unable to create a new Singleton instance for ColorListener. Instance already exists and override is set to false!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * function to add a new OnThemeChangedListener with a given unique identifier to the stack. The request will be ignored if the identifier
     * already exists.
     *
     * @param identifier
     * @param onThemeChangedListener
     */
    public static void addListener(final String identifier, final OnThemeChangedListener onThemeChangedListener) {
        validateInstance();
        if (!hasIdentifier(identifier)) {
            colorListeners.put(identifier, onThemeChangedListener);
            ColorService.register(new ColorServiceObject().withIdentifier(identifier, onThemeChangedListener));
            ThemeLogger.addLogMessage(ColorListeners.class, "registered a new OnThemeChangedListener (Identifier -> " + identifier + ")");
            onListenerAdded(onThemeChangedListener);
        } else {
            ThemeLogger.addLogMessage(ColorListeners.class, "addListener() : the given identifier (" + identifier + ") already exists! Skipping request");
        }
    }

    /**
     * function to check weather an OnThemeChangedListener with the given identifier exists or not.
     *
     * @param identifier
     * @return true if exists - false if not
     */
    public static boolean hasIdentifier(String identifier) {
        validateInstance();
        return getColorListeners().containsKey(identifier);
    }

    /**
     * function to check weather the given OnThemeChangedListener exists or not.
     *
     * @param onThemeChangedListener
     * @return true if exists - false if not
     */
    public static boolean hasListener(OnThemeChangedListener onThemeChangedListener) {
        validateInstance();
        return getColorListeners().containsValue(onThemeChangedListener);
    }

    /**
     * function to get the OnThemeChangedListener with the given identifier (if exists, otherwise returns null)
     */
    public static OnThemeChangedListener getListener(String onIdentifier) {
        validateInstance();
        return colorListeners.get(onIdentifier);
    }

    /**
     * internal function which is called after a new OnThemeChangedListener is added
     */
    private static void onListenerAdded(OnThemeChangedListener onThemeChangedListener) {
        if (onThemeChangedListener == null) {
            ThemeLogger.addLogMessage(ColorListeners.class, "onListenerAdded() : The given onThemeChangedListener is null.");
        } else {
            onThemeChangedListener.onPrimaryColorChanged();
            onThemeChangedListener.onPrimaryColorDarkChanged();
            onThemeChangedListener.onAccentColorChanged();
            onThemeChangedListener.onFontColorChanged(ThemeEngine.getCurrentThemeValues().getThemeFontColor());
            onThemeChangedListener.onApplicationStyleChanged(ThemeEngine.getCurrentThemeValues().getApplicationTheme());
            ThemeLogger.addLogMessage(ColorListeners.class, "onListenerAdded() : Called all methods on the given interface.");
        }
    }

    /**
     * function to call an CallAction on a given OnThemeChangedListener
     */
    public static void performCallActionOnListener(OnThemeChangedListener onThemeChangedListener, CallAction callAction) {
        validateInstance();
        if (onThemeChangedListener == null)
            throw new NullPointerException("Can't perform action on null! The given onThemeChangedListener is not initialized");
        switch (callAction) {
            case ALL_COLORS:
                colorListeners.get(onThemeChangedListener).onPrimaryColorChanged();
                colorListeners.get(onThemeChangedListener).onPrimaryColorDarkChanged();
                colorListeners.get(onThemeChangedListener).onAccentColorChanged();
                colorListeners.get(onThemeChangedListener).onApplicationStyleChanged(ThemeEngine.getCurrentThemeValues().getApplicationTheme());
                colorListeners.get(onThemeChangedListener).onFontColorChanged(ThemeEngine.getCurrentThemeValues().getThemeFontColor());
                break;
            case PRIMARY_COLOR:
                getColorListeners().get(onThemeChangedListener).onPrimaryColorChanged();
                break;
            case PRIMARY_COLOR_DARK:
                getColorListeners().get(onThemeChangedListener).onPrimaryColorDarkChanged();
                break;
            case ACCENT_COLOR:
                getColorListeners().get(onThemeChangedListener).onAccentColorChanged();
                break;
            case APPLICATION_COLOR:
                getColorListeners().get(onThemeChangedListener).onApplicationStyleChanged(ThemeEngine.getCurrentThemeValues().getApplicationTheme());
                break;
            case FONT_COLOR:
                getColorListeners().get(onThemeChangedListener).onFontColorChanged(ThemeEngine.getCurrentThemeValues().getThemeFontColor());
                break;
        }
        //TODO: add support for identifier
        ThemeLogger.addLogMessage(ColorListeners.class, "performCallActionOnListener() completed on -> " + callAction.toString() + " for : TODO");
    }

    /**
     * function to call a given OnThemeChangedListener's function on all items in the stack
     */
    public static void callReloadActionOnListeners(CallAction callAction) {
        validateInstance();
        for (OnThemeChangedListener onThemeChangedListener : getColorListeners().values()) {
            switch (callAction) {
                case ALL_COLORS:
                    onThemeChangedListener.onPrimaryColorChanged();
                    onThemeChangedListener.onPrimaryColorDarkChanged();
                    onThemeChangedListener.onAccentColorChanged();
                    onThemeChangedListener.onApplicationStyleChanged(ThemeEngine.getCurrentThemeValues().getApplicationTheme());
                    onThemeChangedListener.onFontColorChanged(ThemeEngine.getCurrentThemeValues().getThemeFontColor());
                    break;
                case PRIMARY_COLOR:
                    onThemeChangedListener.onPrimaryColorChanged();
                    break;
                case PRIMARY_COLOR_DARK:
                    onThemeChangedListener.onPrimaryColorDarkChanged();
                    break;
                case ACCENT_COLOR:
                    onThemeChangedListener.onAccentColorChanged();
                    break;
                case APPLICATION_COLOR:
                    onThemeChangedListener.onApplicationStyleChanged(ThemeEngine.getCurrentThemeValues().getApplicationTheme());
                    break;
                case FONT_COLOR:
                    onThemeChangedListener.onFontColorChanged(ThemeEngine.getCurrentThemeValues().getThemeFontColor());
                    break;
            }
        }
        ThemeLogger.addLogMessage(ColorListeners.class, "finished calling all OnThemeChangedListeners for " + callAction.toString() + ". ItemCount: " + colorListeners.size());
    }

    /**
     * function to get the identifier of an OnThemeChangedListener
     */
    @Deprecated
    public static void getIdentifierForListener(OnThemeChangedListener onThemeChangedListener) {
        validateInstance();
        if (getColorListeners().size() <= 0) {
            ThemeLogger.addLogMessage(ColorListeners.class, "getIdentifierForListener() : can't lookup - no listeners attached!");
        } else if (!getColorListeners().containsValue(onThemeChangedListener)) {
            ThemeLogger.addLogMessage(ColorListeners.class, "getIdentifierForListener() : The given OnThemeChangedListener doesn't exist! Unable to return identifier.");
        } else {
            if (getColorListeners().get(onThemeChangedListener) != null) {
                for (String ident : getColorListeners().keySet()) {
                    if (getColorListeners().get(ident).equals(onThemeChangedListener)) {
                        ThemeLogger.addLogMessage(ColorListeners.class, "getIdentifierForListener() : current onThemeChangedListener identified -> " + ident);
                    }
                }
                ThemeLogger.addLogMessage(ColorListeners.class, "getIdentifierForListener() : finished lookup for onThemeChangedListener!");
            } else {
                ThemeLogger.addLogMessage(ColorListeners.class, "getIdentifierForListener() : getColorListeners() returned NULL for given OnThemeChangedListener!");
            }
        }
    }

    /**
     * function which returns weather the list is empty or not
     */
    public static boolean listEmpty() {
        return getColorListeners().isEmpty();
    }


    public static class ColorService {

        public static boolean hasItems() {
            if (getColorListeners().isEmpty()) return false;
            return true;
        }

        /**
         * function to register a new ColorServiceObject on the ColorService
         * @param colorServiceObject
         */

        public static void register(ColorServiceObject colorServiceObject) {
            colorListeners.colorServiceObjectList.add(colorServiceObject);
            ThemeLogger.addLogMessage(ColorServiceObject.class, "register() : new ColorServiceObject registered (Identifier: " + colorServiceObject.getIdentifier() + ").");
        }

        /**
         * function to get the ammount of ColorServiceObject's currently added to the ColorService
         * @return
         */
        public static int getItemCount() {
            return colorListeners.size();
        }

        /**
         * function to interact with a single ColorServiceObject - identified by @String{identifier}
         * @param identifier
         * @return ColorServiceObject
         */
        public static ColorServiceObject getObjectWithIdentifier(String identifier) {
            if(colorListeners.colorServiceObjectList.size() > 0) {
                for(ColorServiceObject colorServiceObject : colorListeners.colorServiceObjectList) {
                    if(colorServiceObject.getIdentifier().equals(identifier)) return colorServiceObject;
                }
                return null;
            } else {
                ThemeLogger.addLogMessage(ColorService.class, "getObjectWithIdentifier() : colorServiceObjectList is empty");
                return null;
            }
        }

        /**
         * function to get the complete List with all ColorServiceObject instances.
         * @return List<ColorServiceObject>
         */
        public static List<ColorServiceObject> getStack() {
            if(hasItems()) return colorListeners.colorServiceObjectList;
            throw new NullPointerException("ColorService doesn't have any assigned ColorServiceObjects!");
        }


        /**
         * function to reload all OnThemeChangedListener function's on a given ColorServiceObject (if a custom OnThemeChangedListener interface is set.)
         */
        public static void reloadColorInterfaceActions(String identifier) {
            if (colorListeners.colorServiceObjectList.isEmpty()) {
                ThemeLogger.addLogMessage(ColorService.class, "reloadColorInterfaceActions() : Can't perform action - No ColorServiceObject registered in ColorService");
            } else {
                if(getObjectWithIdentifier(identifier) != null && getObjectWithIdentifier(identifier).hasInterface()) {
                    for(ColorServiceObject colorServiceObject : colorListeners.colorServiceObjectList) {
                        if(colorServiceObject.getIdentifier().equals(identifier)) {
                            colorServiceObject.onPrimaryColorChanged();
                            colorServiceObject.onPrimaryColorDarkChanged();
                            colorServiceObject.onAccentColorChanged();
                            colorServiceObject.onApplicationStyleChanged(ThemeEngine.getCurrentThemeValues().getApplicationTheme());
                            colorServiceObject.onFontColorChanged(ThemeEngine.getCurrentThemeValues().getThemeFontColor());
                            ThemeLogger.addLogMessage(ColorService.class, "reloadColorInterfaceActions() : calling all interface actions on ColorServiceObject{ID:" + identifier +"}");
                        }
                    }
                }
                ThemeLogger.addLogMessage(ColorListeners.ColorService.class, "reloadColorInterfaceActions() finishing call.");
            }

        }

    }
}