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

import com.fireball1725.firelib.guimaker.IGuiMaker;
import com.fireball1725.firelib.guimaker.capability.GuiMakerCapability;
import com.fireball1725.firelib.guimaker.capability.GuiMakerProvider;
import com.fireball1725.firelib.guimaker.capability.GuiMakerStorage;
import com.fireball1725.firelib.guimaker.capability.IGuiMakerCapability;
import com.fireball1725.firelib.proxy.IProxy;
import com.fireball1725.firelib.proxy.base.IProxyBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    @SubscribeEvent
    public void attachCapabilitiesEvent(AttachCapabilitiesEvent <TileEntity>event) {
        if (event.getObject() instanceof IGuiMaker) {
            event.addCapability(new ResourceLocation("GuiMaker"), new GuiMakerProvider());
        }
    }
}
