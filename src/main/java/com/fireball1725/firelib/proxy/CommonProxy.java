/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.proxy;

import com.fireball1725.firelib.FireLib;
import com.fireball1725.firelib.guimaker.capability.GuiMakerCapability;
import com.fireball1725.firelib.guimaker.capability.GuiMakerHandler;
import com.fireball1725.firelib.guimaker.capability.GuiMakerStorage;
import com.fireball1725.firelib.guimaker.events.GuiMakerGuiHandler;
import com.fireball1725.firelib.guimaker.util.IGuiMaker;
import com.fireball1725.firelib.network.PacketHandler;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class CommonProxy implements IProxy {
    @Override
    public void preInitEnd(FMLPreInitializationEvent event) {
        PacketHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(FireLib.instance, new GuiMakerGuiHandler());

        CapabilityManager.INSTANCE.register(IGuiMaker.class, new GuiMakerStorage(), GuiMakerCapability.class);

        this.registerEventHandler(new GuiMakerHandler());
    }
}
