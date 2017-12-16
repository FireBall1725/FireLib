package com.fireball1725.firelib;

import com.fireball1725.firelib.proxy.base.IProxyBase;
import com.fireball1725.firelib.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION_BUILD)
public class FireLib extends FireMod {

    @Mod.Instance(ModInfo.MOD_ID)
    public static FireLib instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Override
    public IProxyBase proxy() {
        return proxy;
    }

    @Override
    public String getModId() {
        return ModInfo.MOD_ID;
    }
}
