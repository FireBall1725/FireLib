/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.util;

import com.fireball1725.firelib.FireMod;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

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
