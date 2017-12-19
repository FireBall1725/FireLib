/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib;

import com.fireball1725.firelib.proxy.base.IProxyBase;
import com.fireball1725.firelib.util.FireLog;
import com.fireball1725.firelib.util.ModEventHandlerHack;
import com.google.common.base.Stopwatch;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public abstract class FireMod {
    private final FireLog logger;

    public FireMod() {
        this.logger = new FireLog(this);
        ModEventHandlerHack.doHack(this);
    }

    public final FireLog getLogger() {
        return this.logger;
    }

    public abstract String getModId();

    public abstract IProxyBase proxy();

    public Object getModBlockRegistry() { return null; }

    public Object getModItemRegistry() { return null; }


    // FML Events

    @Mod.EventHandler
    public final void preInit(FMLPreInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        this.getLogger().info("Pre Initialization (Started)");

        // Check java version to make sure we are on Java 1.8
        if (!SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8)) {
            //throw new OutdatedJavaException(String.format("%s requires Java 8 or newer, Please update your java", ModInfo.MOD_NAME));
        }

        this.proxy().registerEventHandler(this);
        proxy().initConfiguration(event);
        proxy().preInitStart(event);
        proxy().preInitEnd(event);

        this.getLogger().info("Pre Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public final void init(FMLInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        this.getLogger().info("Initialization (Started)");

        proxy().initStart(event);
        proxy().registerCapabilities();
        proxy().registerEventHandlers();
        proxy().initEnd(event);

        this.getLogger().info("Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public final void postInit(FMLPostInitializationEvent event) {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        this.getLogger().info("Post Initialization (Started)");

        proxy().postInitStart(event);
        proxy().postInitEnd(event);

        this.getLogger().info("Post Initialization (Ended after " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms)");
    }

    @Mod.EventHandler
    public final void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        proxy().onServerAboutToStart(event);
    }

    @Mod.EventHandler
    public final void onServerStarting(FMLServerStartingEvent event) {
        proxy().onServerStarting(event);
    }

    @Mod.EventHandler
    public final void onServerStarted(FMLServerStartedEvent event) {
        proxy().onServerStarted(event);
    }

    @Mod.EventHandler
    public final void onServerStopping(FMLServerStoppingEvent event) {
        proxy().onServerStopping(event);
    }

    @Mod.EventHandler
    public final void onServerStopped(FMLServerStoppedEvent event) {
        proxy().onServerStopped(event);
    }

    @SubscribeEvent
    public final void registerBlocks(RegistryEvent.Register<Block> event) {
        //proxy().registerBlocks(this, event.getRegistry());
    }
}