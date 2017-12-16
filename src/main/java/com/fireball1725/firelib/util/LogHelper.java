package com.fireball1725.firelib.util;

import com.fireball1725.firelib.ModInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper {
    public static final Logger log;

    static {
        log = LogManager.getLogger(ModInfo.MOD_ID);
    }

    private static void log(Level logLevel, Object message) {
        log.log(logLevel, message);
    }

    public static void all(Object message) {
        log(Level.ALL, message);
    }

    public static void debug(Object message) {
        log(Level.DEBUG, message);
    }

    public static void trace(Object message) {
        log(Level.TRACE, message);
    }

    public static void fatal(Object message) {
        log(Level.FATAL, message);
    }

    public static void error(Object message) {
        log(Level.ERROR, message);
    }

    public static void warn(Object message) {
        log(Level.WARN, message);
    }

    public static void info(Object message) {
        log(Level.INFO, message);
    }

    public static void off(Object message) {
        log(Level.OFF, message);
    }
}
