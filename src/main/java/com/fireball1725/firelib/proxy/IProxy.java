package com.fireball1725.firelib.proxy;

import com.fireball1725.firelib.proxy.base.IProxyBase;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy extends IProxyBase {

    @Override
    default void registerCapabilities() {

    }

    @Override
    default void initConfiguration(FMLPreInitializationEvent event) {

    }

    @Override
    default void registerEventHandlers() {

    }
}
