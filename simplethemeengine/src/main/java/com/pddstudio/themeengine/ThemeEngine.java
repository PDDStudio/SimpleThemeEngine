package com.pddstudio.themeengine;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;

import com.pddstudio.themeengine.data.ThemeInfo;
import com.pddstudio.themeengine.data.ThemeObject;
import com.pddstudio.themeengine.data.ThemeSettingsObject;
import com.pddstudio.themeengine.helpers.ThemeDefaults;
import com.pddstudio.themeengine.helpers.ThemeLogger;
import com.pddstudio.themeengine.storages.ColorListeners;
import com.pddstudio.themeengine.storages.ColorServiceObject;
import com.pddstudio.themeengine.types.ApplicationTheme;
import com.pddstudio.themeengine.types.CallAction;
import com.pddstudio.themeengine.types.EngineType;
import com.pddstudio.themeengine.types.ThemeFontColor;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class was created by Patrick J
 * on 09.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ThemeEngine {

    private static ThemeEngine themeEngine;
    private final Context themeContext;
    private final List<OnThemeChangedListener> themeChangedListenerList;
    private final EngineType themeEngineType;
    private static IBackendNotificationInterface backendNotificationInterface;

    private ThemeEngine(Context context, EngineType engineType) {
        ThemeLogger.init();
        themeContext = context;
        themeChangedListenerList = new ArrayList<>();
        themeEngineType = engineType;
        ColorListeners.createSingleton(true);
        ThemeLogger.addLogMessage(getClass(), "ThemeEngine initialized! [" + engineType.toString() + "]");
    }

    /**
     * function to check weather the ThemeEngine instance exists or not.
     */
    public static boolean isInit() {
        if(themeEngine == null) {
            return false;
        }
        return true;
    }

    /**
     * function to initialize the ThemeEngine with it's default settings - called on the applications startup method
     */
    public static void initThemeEngine(Context context) {
        themeEngine = new ThemeEngine(context, EngineType.DEFAULT);
    }

    /**
     * function to initialize the ThemeEngine with custom Objects and Callbacks - called on the applications startup method
     */
    public static void initThemeEngine(Context context, EngineType engineType) {
        switch(engineType) {
            case DEFAULT:
                themeEngine = new ThemeEngine(context, EngineType.DEFAULT);
                break;
            case CUSTOM:
                themeEngine = new ThemeEngine(context, EngineType.CUSTOM);
                break;
        }
    }

    /**
     * function to assign the IBackendNotificationInterface to the current ThemeEngine instance.
     */
    public static void setBackendNotificationInterface(IBackendNotificationInterface iBackendNotificationInterface) {
        themeEngine.backendNotificationInterface = iBackendNotificationInterface;
        ThemeLogger.addLogMessage(ThemeEngine.class, "setBackendNotificationInterface() : assigned interface for background synchronizing!");
    }

    /**
     * function to call methods inside the assigned IBackendNotificationInterface
     */
    public static IBackendNotificationInterface callBackendNotificationInterface() {
        if(backendNotificationInterface == null) throw new NullPointerException("Can't call function on non initialized variable!");
        return backendNotificationInterface;
    }

    /**
     * function to check weather the ThemeEngine singleton instance exists. Throws a NullPointerException if ThemeEngine is not initialized.
     */
    private static void validateSingletonInstance() {
        if(!isInit()) throw new NullPointerException("Trying to access ThemeEngine singleton instance - which is null. ThemeEngine might not be initialized");
    }

    /**
     * function to destroy the ThemeEngine instance - called on the applications exit method
     */
    public static void destroyThemeEngine() {
        //save config and assign null
        ThemeLogger.destroy();
    }

    /**
     * function to interact with the context of the main application
     */
    public Context getMainApplicationContext() {
        if(themeContext == null) {
            throw new NullPointerException("Unable to access the Context of the main application");
        }
        return themeContext;
    }

    /**
     * function to provide default colors as fallback ThemeObject. Should be called after initThemeEngine().
     */
    public static void setFallbackColors(@ColorInt int primary, @ColorInt int primaryDark, @ColorInt int primaryAccent, ApplicationTheme applicationTheme, ThemeFontColor themeFontColor) {
        ThemeDefaults.setDefaultThemeObject(new ThemeObject(primary, primaryDark, primaryAccent, applicationTheme.getColorValue(), themeFontColor.getColorValue()));
        ThemeDefaults.setDefaultApplicationTheme(applicationTheme);
        ThemeDefaults.setDefaultApplicationFontColor(themeFontColor);
        ThemeInfo.setThemeInfo(applicationTheme, themeFontColor);
    }

    /**
     * function to restore a saved ThemeObject instance - only if ThemeSettingsObject is already initialized !
     */
    public static void restoreThemeObject(ThemeObject themeObject) {
        if(ThemeSettingsObject.hasInstance()) {
            ThemeSettingsObject.current().setThemeObject(themeObject);
        }
    }

    /**
     * function to restore a saved ApplicationTheme instance - only if ThemeSettingsObject is already initialized !
     */
    public static void restoreApplicationTheme(ApplicationTheme applicationTheme) {
        if(ThemeSettingsObject.hasInstance()) ThemeSettingsObject.current().setApplicationTheme(applicationTheme);
    }

    /**
     * function to restore a saved ThemeFontColor instance - only if ThemeSettingsObject is already initialized !
     */
    public static void restoreThemeFontColor(ThemeFontColor themeFontColor) {
        if(ThemeSettingsObject.hasInstance()) ThemeSettingsObject.current().setThemeFontColor(themeFontColor);
    }

    /**
     * function to restore all saved ThemeSettingsObjects (will either create a new instance or override the current)
     */
    public static void restoreThemeSettings(ThemeObject themeObject, ApplicationTheme applicationTheme, ThemeFontColor themeFontColor) {
        if(ThemeSettingsObject.hasInstance()) {
            ThemeSettingsObject.current().setThemeObject(themeObject);
            ThemeSettingsObject.current().setApplicationTheme(applicationTheme);
            ThemeSettingsObject.current().setThemeFontColor(themeFontColor);
        } else {
            ThemeSettingsObject.create(themeObject, applicationTheme, themeFontColor);
        }
    }

    /**
     * function to check weather the database has a saved instance of the ThemeObject or not
     */
    public static boolean hasValidThemeObject() {
        validateSingletonInstance();
        if(themeEngine.backendNotificationInterface == null) throw new NullPointerException("Can't access BackendNotificationInterface! Instance is null");
        if(themeEngine.backendNotificationInterface.loadSavedThemeObject() == null) {
            return false;
        }
        return true;
    }

    /**
     * function to check weather the database has a saved instance of the ApplicationTheme or not
     */
    public static boolean hasValidApplicationTheme() {
        validateSingletonInstance();
        if(themeEngine.backendNotificationInterface == null) throw new NullPointerException("Can't access BackendNotificationInterface! Instance is null");
        if(themeEngine.backendNotificationInterface.loadSavedApplicationTheme() == null) return false;
        return true;
    }

    /**
     * function to check weather the database has a saved instance of the ThemeFontColor or not
     */
    public static boolean hasValidThemeFontColor() {
        validateSingletonInstance();
        if(themeEngine.backendNotificationInterface == null) throw new NullPointerException("Can't access BackendNotificationInterface! Instance is null");
        if(themeEngine.backendNotificationInterface.loadSavedThemeFontColor() == null) return false;
        return true;
    }

    /**
     * function to create the ThemeSettingsObject instance with the returned params
     */
    public static void loadSettingsThemeObject() {
        validateSingletonInstance();
        if(backendNotificationInterface == null) throw new NullPointerException("Can't call loadSettingsThemeObject");
        if(hasValidThemeFontColor() && hasValidApplicationTheme() && hasValidThemeObject()) {
            restoreThemeSettings(backendNotificationInterface.loadSavedThemeObject(),
                    backendNotificationInterface.loadSavedApplicationTheme(),
                    backendNotificationInterface.loadSavedThemeFontColor());
        } else {
            if(!ThemeSettingsObject.hasInstance()) {
                ThemeSettingsObject.create();
            }
            //if(hasValidThemeObject()) ThemeSettingsObject.current().setThemeObject(themeEngine.backendNotificationInterface.loadSavedThemeObject());
            //if(hasValidApplicationTheme()) ThemeSettingsObject.current().setApplicationTheme(themeEngine.backendNotificationInterface.loadSavedApplicationTheme());
            //if(hasValidThemeFontColor()) ThemeSettingsObject.current().setThemeFontColor(themeEngine.backendNotificationInterface.loadSavedThemeFontColor());
            restoreThemeObject(backendNotificationInterface.loadSavedThemeObject());
            restoreApplicationTheme(backendNotificationInterface.loadSavedApplicationTheme());
            restoreThemeFontColor(backendNotificationInterface.loadSavedThemeFontColor());
        }
        ThemeLogger.addLogMessage(ThemeEngine.class, "loadSettingsThemeObject() : finished loading the application's ThemeSettingsObject!");

    }

    /**
     * function to retrieve the ThemeEngine instance
     */
    public static ThemeEngine getCurrent() {
        if(themeEngine == null) throw new NullPointerException("ThemeEngine is not initialized");
        return themeEngine;
    }

    public void reloadApplicationTheme(ApplicationTheme applicationTheme) {
        validateSingletonInstance();
        if(ColorListeners.listEmpty()) {
            ThemeLogger.addLogMessage(getClass(), "reloadApplicationTheme() called : Stack with listeners is empty, skipping refreshing for " + applicationTheme.toString());
        } else {
            ThemeLogger.addLogMessage(getClass(), "reloadApplicationTheme() called : ApplicationTheme set to: " + applicationTheme.toString() + " ! Reloading Application");
            switch (applicationTheme) {
                case THEME_LIGHT:
                    break;
                case THEME_DARK:
                    break;
                case THEME_CUSTOM:
                    break;
            }
        }
    }

    /**
     * function to register a new OnThemeChangedListener with a unique identifier (Identifier -> Classname)
     */
    public static void registerThemeListener(Activity activity, OnThemeChangedListener onThemeChangedListener) {
        validateSingletonInstance();
        String activityName = activity.getClass().getSimpleName();
        registerListener(activityName, onThemeChangedListener);
    }

    /**
     * function to register a new OnThemeChangedListener with a unique identifier (Identifier -> String TAG)
     */
    public static void registerThemeListener(String TAG, OnThemeChangedListener onThemeChangedListener) {
        validateSingletonInstance();
        registerListener(TAG, onThemeChangedListener);
    }

    /**
     * function to register a new OnThemeChangedListener with a unique identifier (Identifier -> Classname)
     */
    public static void registerThemeListener(Class classIdentifier, OnThemeChangedListener onThemeChangedListener) {
        validateSingletonInstance();
        String TAG = classIdentifier.getSimpleName();
        registerListener(TAG, onThemeChangedListener);
    }

    /**
     * function to wrap the public methods for adding an OnThemeChangedListener to a single function
     */
    private static void registerListener(String identifier, OnThemeChangedListener onThemeChangedListener) {
        validateSingletonInstance();
        if(ColorListeners.hasIdentifier(identifier)) {
            ThemeLogger.addLogMessage(ThemeEngine.class, "registerThemeListener() : Identifier [" + identifier + "] already exists. Refreshing values instead.");
            ColorListeners.ColorService.reloadColorInterfaceActions(identifier);
        } else {
            ColorListeners.addListener(identifier, onThemeChangedListener);
        }
    }

    /**
     * function to call interface functions for a given OnThemeChangedListener and an identifier
     */
    public static void callActionOnListener(String onIdentifier, CallAction callAction) {
        validateSingletonInstance();
        callOnListener(onIdentifier, callAction);
    }

    /**
     * function to call interface functions for a given OnThemeChangedListener and an identifier
     */
    public static void callActionOnListener(Activity activity, CallAction callAction) {
        validateSingletonInstance();
        String onIdentifier = activity.getLocalClassName();
        callOnListener(onIdentifier, callAction);
    }

    /**
     * function to call interface functions for the given OnThemeChangedListener and an identifier
     */
    public static void callActionOnListener(Class classIdentifier, CallAction callAction) {
        validateSingletonInstance();
        String onIdentifier = classIdentifier.getSimpleName();
        callOnListener(onIdentifier, callAction);
    }

    /**
     * private function for public static method callActionOnListener()
     */
    private static void callOnListener(String ident, CallAction callAction) {
        validateSingletonInstance();
        if(!ColorListeners.hasIdentifier(ident)) {
            ThemeLogger.addLogMessage(ThemeEngine.class, "callOnListener() : unable to find OnThemeChangedListener for ["+ ident + "] - Skipping!");
        } else {
            ColorListeners.performCallActionOnListener(ColorListeners.getListener(ident), callAction);
        }
    }

    /**
     * function to check weather the requested identifier exists in the ColorListeners stack
     */
    public static boolean findThemeListenerByIdentifier(String identifier) {
        validateSingletonInstance();
        return ColorListeners.hasIdentifier(identifier);
    }

    /**
     * function to check weather the requested identifier exists in the ColorListeners stack
     */
    public static boolean findThemeListenerByIdentifier(Activity activity) {
        validateSingletonInstance();
        return ColorListeners.hasIdentifier(activity.getLocalClassName());
    }

    /**
     * function to check weather the requested identifier exists in the ColorListeners stack
     */
    public static boolean findThemeListenerByIdentifier(Class classIdentifier) {
        validateSingletonInstance();
        return ColorListeners.hasIdentifier(classIdentifier.getSimpleName());
    }

    /**
     * function to access the objects saved in the ThemeSettingsObject singleton.
     */
    public static ThemeSettingsObject getCurrentThemeValues() {
        if(!ThemeSettingsObject.hasInstance()) throw new NullPointerException("Unable to access ThemeSettingsObject! Instance not created yet.");
        return ThemeSettingsObject.current();
    }

    /**
     * function to directly receive the current color value for a selected colortype
     */
    public static int getColorValue(CallAction onCall) {
        validateSingletonInstance();
        if(!ThemeSettingsObject.hasInstance()) throw new NullPointerException("Unable to access ThemeSettingsObject! Instance is not created yet.");
        switch (onCall) {
            case PRIMARY_COLOR:
                return ThemeSettingsObject.current().getThemeObject().getPrimaryColor();
            case PRIMARY_COLOR_DARK:
                return ThemeSettingsObject.current().getThemeObject().getPrimaryColorDark();
            case ACCENT_COLOR:
                return ThemeSettingsObject.current().getThemeObject().getAccentColor();
            case FONT_COLOR:
                return ThemeSettingsObject.current().getThemeFontColor().getColorValue();
            case APPLICATION_COLOR:
                return ThemeSettingsObject.current().getApplicationTheme().getColorValue();
            case ALL_COLORS:
            default:
                break;
        }
        ThemeLogger.addLogMessage(ThemeEngine.class, "getColorValue() returnied the requested color value!");
        return 0;
    }

    /**
     * function to update a color for a given Action inside the ThemeSettingsObject
     */
    public static void colorChanged(CallAction callAction, @ColorInt int color) {
        validateSingletonInstance();
        if(themeEngine.backendNotificationInterface == null) throw new NullPointerException("BackendNotificationInterface is not initialized! Unable to reach the backend!");
        switch (callAction) {
            case ACCENT_COLOR:
                getCurrentThemeValues().getThemeObject().setAccentColor(color);
                callBackendNotificationInterface().onThemeObjectChanged(getCurrentThemeValues().getThemeObject());
                break;
            case PRIMARY_COLOR:
                getCurrentThemeValues().getThemeObject().setPrimaryColor(color);
                callBackendNotificationInterface().onThemeObjectChanged(getCurrentThemeValues().getThemeObject());
                break;
            case PRIMARY_COLOR_DARK:
                getCurrentThemeValues().getThemeObject().setPrimaryColorDark(color);
                callBackendNotificationInterface().onThemeObjectChanged(getCurrentThemeValues().getThemeObject());
                break;
            case APPLICATION_COLOR:
                getCurrentThemeValues().getApplicationTheme().setColorValue(color);
                callBackendNotificationInterface().onApplicationThemeObjectChanged(getCurrentThemeValues().getApplicationTheme());
                break;
            case FONT_COLOR:
                getCurrentThemeValues().getThemeFontColor().setColorValue(color);
                callBackendNotificationInterface().onThemeFontColorObjectChanged(getCurrentThemeValues().getThemeFontColor());
                break;
            default:
                break;
        }
        ThemeLogger.addLogMessage(ThemeEngine.class, "colorChanged() called with CallAction -> " + callAction.toString() + " and new Color (int) -> " + color);
    }

    /**
     * function to call a specified refresh event on ALL attached OnThemeChangedListeners!
     * NOTE: THIS TASK IS NOT ASYNCHRONOUS! MIGHT CAUSE PROBLEMS WITH RESPONSIBILITY!
     */
    public static void refreshColorListeners(CallAction callAction) {
        validateSingletonInstance();
        if(ColorListeners.isInitialized()) {
            ColorListeners.callReloadActionOnListeners(callAction);
        } else {
            ThemeLogger.addLogMessage(ThemeEngine.class, "unable to refresh color listeners! ColorListeners List is not initialized.");
        }
    }

    /**
     * function to change the ApplicationTheme in the ThemeSettinsObject
     */
    public static void changeApplicationTheme(ApplicationTheme newApplicationTheme) {
        validateSingletonInstance();
        if(ThemeSettingsObject.current().getApplicationTheme() == newApplicationTheme) {
            ThemeLogger.addLogMessage(ThemeEngine.class, "changeApplicationTheme() : current ApplicationTheme and newApplicationTheme are the same! (" + newApplicationTheme.toString() + ") ! Skipping change.");
        } else {
            ThemeSettingsObject.current().setApplicationTheme(newApplicationTheme);
            //reload assigned values?!
        }
    }

    /**
     * function to change the ThemeFontColor in the ThemeSettingsObject
     */
    public static void changeThemeFontColor(ThemeFontColor newThemeFontColor) {
        validateSingletonInstance();
        if(ThemeSettingsObject.current().getThemeFontColor() == newThemeFontColor) {
            ThemeLogger.addLogMessage(ThemeEngine.class, "changeThemeFontColor() : current ThemeFontCOlor and newThemeFontColor are the same (" + newThemeFontColor.toString() + ") ! Skipping assignment.");
        } else {
            ThemeSettingsObject.current().setThemeFontColor(newThemeFontColor);
            //reload assigned values?
        }
    }

    /**
     * function to retrieve the identifier for an OnThemeChangedListener as String (later, now it's just printing debug messages)
     */
    @Deprecated
    public static void getIdentifierForListener(OnThemeChangedListener onThemeChangedListener) {
        validateSingletonInstance();
        if(onThemeChangedListener == null) throw new NullPointerException("unable to call action on null!");
        ColorListeners.getIdentifierForListener(onThemeChangedListener);
    }

    /**
     * function to reload all colors on the provided identifier's interface
     */
    public static void reloadOnInterface(String identifier) {
        if (ColorListeners.getColorListeners().get(identifier) != null) {
            ColorListeners.getListener(identifier).onPrimaryColorChanged();
            ColorListeners.getListener(identifier).onPrimaryColorDarkChanged();
            ColorListeners.getListener(identifier).onAccentColorChanged();
            ColorListeners.getListener(identifier).onFontColorChanged(getCurrentThemeValues().getThemeFontColor());
            ColorListeners.getListener(identifier).onApplicationStyleChanged(getCurrentThemeValues().getApplicationTheme());
            ThemeLogger.addLogMessage(ThemeEngine.class, "reloadOnInterface() : called all functions on OnThemeChangedListener for identifier: " + identifier);
        } else {
            ThemeLogger.addLogMessage(ThemeEngine.class, "reloadOnInterface() : skipped reloading! No OnThemeChangedListener found with the given Identifier (" + identifier + ")");
        }
    }

    /**
     * function to reload all colors on the provided identifier's interface
     */
    public static void reloadOnInterface(Class classIdentifier) {
        validateSingletonInstance();
        String onIdentifier = classIdentifier.getSimpleName();
        reloadOnInterface(onIdentifier);
    }

    /**
     * function to reload all colors on the provided identifier's interface
     */
    public static void reloadOnInterface(Activity activityIdentifier) {
        validateSingletonInstance();
        String onIdentifier = activityIdentifier.getLocalClassName();
        reloadOnInterface(onIdentifier);
    }

    /**
     * function to check weather an ColorServiceObject exists in the current ColorService stack or not.
     * THIS CLASS IS ACTUALLY ONLY FOR DEBUGGING! - Seems to work. Needs more detailed testing
     */
    public static void printColorServiceStack() {
        validateSingletonInstance();
        if(ColorListeners.ColorService.hasItems()) {
            ThemeLogger.addLogMessage(ThemeEngine.class, "printColorServiceStack() : ColorService has currently registered " + ColorListeners.ColorService.getItemCount() + " object(s)");
            ThemeLogger.addLogMessage(ThemeEngine.class, "#############################################");
            for(ColorServiceObject colorServiceObject : ColorListeners.ColorService.getStack()) {
                ThemeLogger.addLogMessage(ThemeEngine.class, "Item No : " + ColorListeners.ColorService.getStack().indexOf(colorServiceObject)
                        + " with Identifier : " + colorServiceObject.getIdentifier() + " has custom OnThemeChangedListener : " + (colorServiceObject.hasInterface() ? "yes" : "no"));
            }
            ThemeLogger.addLogMessage(ThemeEngine.class, "#############################################");
        } else {
            ThemeLogger.addLogMessage(ThemeEngine.class, "printColorServiceStack() : ColorService hasn't registered any items yet.");
        }
    }

    /**
     * function to get the current ApplicationTheme
     */
    public static ApplicationTheme getCurrentApplicationTheme() {
        return getCurrentThemeValues().getApplicationTheme();
    }

    /**
     * public class for easier accessing the ThemeCurrent value.
     */
    public static class Color {
        public static int primary() {
            validateSingletonInstance();
            return getCurrentThemeValues().getThemeObject().getPrimaryColor();
        }
        public static int primaryDark() {
            validateSingletonInstance();
            return getCurrentThemeValues().getThemeObject().getPrimaryColorDark();
        }
        public static int accent() {
            validateSingletonInstance();
            return getCurrentThemeValues().getThemeObject().getAccentColor();
        }
        public static int fontColor() {
            validateSingletonInstance();
            return getCurrentThemeValues().getThemeFontColor().getColorValue();
        }
        public static int applicationColor() {
            validateSingletonInstance();
            return getCurrentThemeValues().getApplicationTheme().getColorValue();
        }
    }

}
