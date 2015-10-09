package com.pddstudio.themeengine.helpers;

import android.support.annotation.Nullable;
import android.util.Log;

import com.pddstudio.themeengine.types.LogMessageType;

/**
 * This Class was created by Patrick J
 * on 09.09.15. For more Details and Licensing
 * have a look at the README.md
 */
public class ThemeLogger {

    /**
     * Class for merging all debug messages through a single object which can be turned on / off
     */


    private static final boolean showDebugMessages = false;
    private static ThemeLogger themeLogger;
    private static LogMessageType defaultMessageType = LogMessageType.DEFAULT;

    private ThemeLogger() {
        if(showDebugMessages) {
            Log.i(ThemeLogger.class.getSimpleName(), "ThemeLogger instance created. DebugMessages are enabled.");
        } else {
            Log.i(ThemeLogger.class.getSimpleName(), "ThemeLogger instance created. DebugMessages are disabled.");
        }
    }

    private void log(@Nullable LogMessageType logMessageType, String tag, String msg) {
        LogMessageType lmt = logMessageType;
        if(lmt == null) lmt = defaultMessageType;

        if(validateString(tag) && validateString(msg)) {
            switch (lmt) {
                default:
                case DEFAULT:
                case INFO:
                    Log.i(tag, msg);
                    break;
                case DEBUGGING:
                    Log.d(tag, msg);
                    break;
                case ERROR:
                    Log.e(tag, msg);
                    break;
                case WARNING:
                    Log.w(tag, msg);
                    break;
            }
        }
    }

    /**
     * check if a string is not empty
     * @param str
     * @return true if string is not empty, else returns false
     */
    private boolean validateString(String str) {
        if(str.equals("") || str.isEmpty()) return false;
        return true;
    }

    /**
     * function to initialize the Logger instance on the startup
     */
    public static void init() {
        themeLogger = new ThemeLogger();
    }

    /**
     * function to destroy the Logger instance
     */
    public static void destroy() {
        if(themeLogger != null) themeLogger = null;
        Log.i(ThemeLogger.class.getSimpleName(), "ThemeLogger instance destroyed.");
    }

    /**
     * function to add a log message
     * @param message
     */
    public static void addLogMessage(String message) {
        if(themeLogger.validateString(message)) themeLogger.log(defaultMessageType, "NOT_SET", message);
    }

    /**
     * function to add a log message with the class name as tag
     * @param classTag
     * @param message
     */
    public static void addLogMessage(Class<?> classTag, String message) {
        String className = classTag.getSimpleName();
        if(showDebugMessages && themeLogger.validateString(className) && themeLogger.validateString(message)) themeLogger.log(defaultMessageType, className, message);
    }

    /**
     * function to add a log message with the class tag as name and a custom log message Type
     * @param logType
     * @param classTag
     * @param msg
     */
    public static void addLogMessage(LogMessageType logType, Class<?> classTag, String msg) {
        String className = classTag.getSimpleName();
        if(themeLogger.validateString(className) && themeLogger.validateString(msg)) themeLogger.log(logType, className, msg);
    }

}
