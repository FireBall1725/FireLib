package com.fireball1725.firelib.proxy;


import com.fireball1725.firelib.FireLib;
import com.fireball1725.firelib.events.EventClientGui;
import com.fireball1725.firelib.proxy.base.IProxyClientBase;
import com.fireball1725.firelib.renderer.layers.LayerSword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ClientProxy implements IProxy, IProxyClientBase {
    @Override
    public void registerEventHandlers() {
        IProxy.super.registerEventHandlers();

        // Register the Client Gui Event Handler
        FireLib.instance.getLogger().info("Registering Client Gui Event Handler");
        registerEventHandler(new EventClientGui());
    }

    @Override
    public void preInitEnd(FMLPreInitializationEvent event) {
        IProxy.super.preInitEnd(event);


    }

    @Override
    public void initEnd(FMLInitializationEvent event) {
        IProxy.super.initEnd(event);

        // Register the Benihime sword as a layer for all players
        FireLib.instance.getLogger().info("Registering Benihime Player Render Layer");
        for (RenderPlayer renderPlayer : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            renderPlayer.addLayer(new LayerSword(renderPlayer));
        }
    }
}
