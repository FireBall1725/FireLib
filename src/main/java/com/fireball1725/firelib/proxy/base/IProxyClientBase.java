package com.fireball1725.firelib.proxy.base;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public interface IProxyClientBase extends IProxyBase {
    @Override
    default EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    @Override
    default World getClientWorld() {
        return Minecraft.getMinecraft().world;
    }

    @Override
    default World getWorldByDimensionId(int dimension) {
        if (this.getEffectiveSide() == Side.SERVER) {
            return FMLClientHandler.instance().getServer().getWorld(dimension);
        }
        return getClientWorld();
    }

    @Override
    default Side getPhysicalSide() {
        return Side.CLIENT;
    }

    @Override
    default Side getEffectiveSide() {
        return FMLCommonHandler.instance().getEffectiveSide();
    }
}
