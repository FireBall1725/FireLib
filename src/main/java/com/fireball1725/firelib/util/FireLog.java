package com.fireball1725.firelib.util;

import com.fireball1725.firelib.FireMod;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.TracingPrintStream;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.text.MessageFormat;
import java.util.Locale;

public class FireLog {
    private final FireMod mod;
    private final Logger logger;

    public FireLog(FireMod mod) {
        this.mod = mod;
        this.logger = LogManager.getLogger(mod.getModId());
        Side side = FMLLaunchHandler.side();
        if (side == null) side = Side.CLIENT;
        ThreadContext.put("side", side.name().toLowerCase(Locale.ENGLISH));
    }

    private void log(Level logLevel, Object message) {
        this.logger.log(logLevel, String.valueOf(message));
    }

    public void all(Object message) {
        log(Level.ALL, message);
    }

    public void debug(Object message) {
        log(Level.DEBUG, message);
    }

    public void trace(Object message) {
        log(Level.TRACE, message);
    }

    public void fatal(Object message) {
        log(Level.FATAL, message);
    }

    public void error(Object message) {
        log(Level.ERROR, message);
    }

    public void warn(Object message) {
        log(Level.WARN, message);
    }

    public void info(Object message) {
        log(Level.INFO, message);
    }

    public void off(Object message) {
        log(Level.OFF, message);
    }
}
