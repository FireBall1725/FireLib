package com.fireball1725.firelib.proxy.base;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;

public interface IProxyBase {

    // FML Pre-Init Phase //

    /**
     * Performs all needed operations for the proxy's side during FML's pre-init stage
     * Called before FireLib does its standard operations
     *
     * @param event FML Pre-Init event data
     */
    default void preInitStart(FMLPreInitializationEvent event) {}

    /**
     * Performs all needed operations for the proxy's side during FML's pre-init stage
     * Called after FireLib does its standard operations
     *
     * @param event FML Pre-Init event data
     */
    default void preInitEnd(FMLPreInitializationEvent event) {}

    // FML Init Phase //

    /**
     * Performs all needed operations for the proxy's side during FML's init stage
     * Called before FireLib does its standard operations
     *
     * @param event FML Init event data
     */
    default void initStart(FMLInitializationEvent event) {}

    /**
     * Performs all needed operations for the proxy's side during FML's init stage
     * Called after FireLib does its standard operations
     *
     * @param event FML Init event data
     */
    default void initEnd(FMLInitializationEvent event) {}

    // FML Post-Init Phase //

    /**
     * Performs all needed operations for the proxy's side during FML's post-init stage
     * Called before FireLib does its standard operations
     *
     * @param event FML Post-Init event data
     */
    default void postInitStart(FMLPostInitializationEvent event) {}

    /**
     * Performs all needed operations for the proxy's side during FML's post-init stage
     * Called after FireLib does its standard operations
     *
     * @param event FML Post-Init event data
     */
    default void postInitEnd(FMLPostInitializationEvent event) {}

    // Server Events //

    default void onServerAboutToStart(FMLServerAboutToStartEvent event) {}

    default void onServerStarting(FMLServerStartingEvent event) {}

    default void onServerStarted(FMLServerStartedEvent event) {}

    default void onServerStopping(FMLServerStoppingEvent event) {}

    default void onServerStopped(FMLServerStoppedEvent event) {}

    // Methods //

    void initConfiguration(FMLPreInitializationEvent event);

    void registerEventHandlers();

    void registerCapabilities();

    default void registerEventHandler(Object handler) {
        MinecraftForge.EVENT_BUS.register(handler);
    }

    // Utility Methods //

    Side getPhysicalSide();

    Side getEffectiveSide();

    default MinecraftServer getMinecraftServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    EntityPlayer getClientPlayer();

    World getClientWorld();

    World getWorldByDimensionId(int dimension);

    default Entity getEntityById(int dimension, int id) {
        return getEntityById(getWorldByDimensionId(dimension), id);
    }

    default Entity getEntityById(World world, int id) {
        return world == null ? null : world.getEntityByID(id);
    }
}
