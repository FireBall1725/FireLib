package com.fireball1725.firelib;

import com.fireball1725.firelib.proxy.base.IProxyBase;
import com.fireball1725.firelib.util.FireLog;
import com.fireball1725.firelib.util.ModEventHandlerHack;
import com.google.common.base.Stopwatch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
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
}