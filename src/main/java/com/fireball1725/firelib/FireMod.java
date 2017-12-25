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

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;

import com.fireball1725.firelib.proxy.base.IProxyBase;
import com.fireball1725.firelib.util.FireLog;
import com.fireball1725.firelib.util.ModEventHandlerHack;
import com.fireball1725.firelib.util.RegistrationHelper;
import com.google.common.base.Stopwatch;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public abstract class FireMod {

	public static FireMod instance() {
		FireMod loader = null;
		try {
			if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
				return (FireMod) Loader.instance().activeModContainer().getMod();
			} else {
				throw new Exception("Calling Instance after load. SHOULD NOT BE ALLOWED!");
			}
		} catch (Exception e) {
			return FireLib.instance;
		}

	}

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

	public Object getModBlockRegistry() {
		return null;
	}

	public Object getModItemRegistry() {
		return null;
	}

	public Class getBlockEnum() {
		return null;
	}

	public Class getItemEnum() {
		return null;
	}

	// FML Events

	@Mod.EventHandler
	public final void preInit(FMLPreInitializationEvent event) {
		final Stopwatch stopwatch = Stopwatch.createStarted();
		this.getLogger().info("Pre Initialization (Started)");

		// Check java version to make sure we are on Java 1.8
		if (!SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8)) {
			// throw new OutdatedJavaException(String.format("%s requires Java 8 or newer,
			// Please update your java", ModInfo.MOD_NAME));
		}

		this.proxy().registerEventHandler(this);
		proxy().initConfiguration(event);
		proxy().preInitStart(event);
		proxy().registerEventHandler(new RegistrationHelper());
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
	public final void registerModels(ModelRegistryEvent event) {

	}
}