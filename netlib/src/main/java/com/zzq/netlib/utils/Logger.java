package com.zzq.netlib.utils;

import android.util.Log;

import java.util.Hashtable;

/**
 * @auther tangedegushi
 * @creat 2018/8/15
 * @Decribe 打印日志的工具类
 */
public class Logger {

    private final static boolean logFlag = true;
    public final static String tag = "tangedegushi";
    private final static int logLevel = Log.VERBOSE;
    private static Hashtable<String, Logger> sLoggerTable = new Hashtable<String, Logger>();
    private String who;
    private final int MAX_LENGTH = 4000;

    private static Logger log;
    private static Logger whoLog;
    private static Logger zzqLog;
    private static final String DEFAULT = "[default]";
    private static final String ZZQ = "[zzq]";
    private static final String WHO = "[who]";

    private Logger(String name) {
        who = name;
    }

    /**
     * @param className
     * @return
     */
    @SuppressWarnings("unused")
    private static Logger getLogger(String className) {
        Logger classLogger = (Logger) sLoggerTable.get(className);
        if (classLogger == null) {
            classLogger = new Logger(className);
            sLoggerTable.put(className, classLogger);
        }
        return classLogger;
    }

    public static Logger Log() {
        if (log == null) {
            log = new Logger(DEFAULT);
        }
        return log;
    }

    public static Logger zzqLog() {
        if (zzqLog == null) {
            zzqLog = new Logger(ZZQ);
        }
        return zzqLog;
    }

    public static Logger whoLog() {
        if (whoLog == null) {
            whoLog = new Logger(WHO);
        }
        return whoLog;
    }

    /**
     * Get The Current Function Name
     *
     * @return
     */
    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return who + "  " + st.toString() + " :  " ;
//            return mClassName + "[ " + Thread.currentThread().getName() + ": " + st.getFileName() + ":"
//                    + st.getClassName() + ":" + st.getMethodName() + ":" + st.getLineNumber() + " ]";
        }
        return null;
    }

    /**
     * The Log Level:i
     *
     * @param str
     */
    public void i(Object str) {
        if (logFlag) {
            if (logLevel <= Log.INFO) {
                String name = getFunctionName();
                if (name != null) {
                    // Log.i(tag, name + " - " + str);
                    printLog(Log.INFO, name + " - " + str);
                } else {
                    // Log.i(tag, str.toString());
                    printLog(Log.INFO, str.toString());
                }
            }
        }

    }

    /**
     * The Log Level:d
     *
     * @param str
     */
    public void d(Object str) {
        if (logFlag) {
            if (logLevel <= Log.DEBUG) {
                String name = getFunctionName();
                if (name != null) {
                    // Log.d(tag, name + " - " + str);
                    printLog(Log.DEBUG, name + " - " + str);
                } else {
                    // Log.d(tag, str.toString());
                    printLog(Log.DEBUG, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:V
     *
     * @param str
     */
    public void v(Object str) {
        if (logFlag) {
            if (logLevel <= Log.VERBOSE) {
                String name = getFunctionName();
                if (name != null) {
                    // Log.v(tag, name + " - " + str);
                    printLog(Log.VERBOSE, name + " - " + str);
                } else {
                    // Log.v(tag, str.toString());
                    printLog(Log.VERBOSE, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:w
     *
     * @param str
     */
    public void w(Object str) {
        if (logFlag) {
            if (logLevel <= Log.WARN) {
                String name = getFunctionName();
                if (name != null) {
                    // Log.w(tag, name + " - " + str);
                    printLog(Log.WARN, name + " - " + str);
                } else {
                    // Log.w(tag, str.toString());
                    printLog(Log.WARN, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param str
     */
    public void e(Object str) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                String name = getFunctionName();
                if (name != null) {
                    // Log.e(tag, name + " - " + str);
                    printLog(Log.ERROR, name + " - " + str);
                } else {
                    // Log.e(tag, str.toString());
                    printLog(Log.ERROR, str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param ex
     */
    public void e(Exception ex) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                Log.e(tag, "error", ex);
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param log
     * @param tr
     */
    public void e(String log, Throwable tr) {
        if (logFlag) {
            String line = getFunctionName();
            Log.e(tag, "{Thread:" + Thread.currentThread().getName() + "}" + "[" + who + line
                    + ":] " + log + "\n", tr);
        }
    }

    /***
     *
     * printLog:因为logcat输出log的字符长度是4k，超过长度字符串会被丢弃，所以对超过4k长度的log做分段输出. <br/>
     *
     * @author mozk
     * @param level
     * @param logText
     */
    public void printLog(int level, String logText) {
        int index = 0;
        String sub;

        logText = logText.trim();

        while (index < logText.length()) {
            // java的字符不允许指定超过总的长度
            if (logText.length() <= index + MAX_LENGTH) {
                sub = logText.substring(index);
            } else {
                sub = logText.substring(index, index + MAX_LENGTH);
            }

            index += MAX_LENGTH;

            switch (level) {
                case Log.INFO:
                    Log.i(tag, sub.trim());
                    break;
                case Log.DEBUG:
                    Log.d(tag, sub.trim());
                    break;
                case Log.ERROR:
                    Log.e(tag, sub.trim());
                    break;
                case Log.WARN:
                    Log.w(tag, sub.trim());
                    break;
                case Log.VERBOSE:
                    Log.v(tag, sub.trim());
                    break;
                default:
                    Log.e(tag, sub.trim());
                    break;
            }
        }
    }
}
