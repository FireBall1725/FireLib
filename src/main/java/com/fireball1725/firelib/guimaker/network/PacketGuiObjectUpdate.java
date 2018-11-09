/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker.network;

import com.fireball1725.firelib.FireLib;
import com.fireball1725.firelib.guimaker.GuiMaker;
import com.fireball1725.firelib.guimaker.base.GuiBaseContainer;
import com.fireball1725.firelib.guimaker.base.GuiObject;
import com.fireball1725.firelib.guimaker.util.IGuiMaker;
import com.fireball1725.firelib.guimaker.util.NetworkHelper;
import com.fireball1725.firelib.network.PacketHandler;
import com.fireball1725.firelib.util.TileHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;

public class PacketGuiObjectUpdate implements IMessage {
    public String controlName;
    public NBTTagCompound controlTagCompound;
    public BlockPos blockPos;

    public PacketGuiObjectUpdate() {
        /* Required for Forge Simple Network Wrapper */
    }

    public PacketGuiObjectUpdate(String controlName, NBTTagCompound controlTagCompound, BlockPos blockPos) {
        this.controlName = controlName;
        this.controlTagCompound = controlTagCompound;
        this.blockPos = blockPos;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.controlName = ByteBufUtils.readUTF8String(byteBuf);
        this.controlTagCompound = ByteBufUtils.readTag(byteBuf);
        this.blockPos = NetworkHelper.readBlockPos(byteBuf);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        ByteBufUtils.writeUTF8String(byteBuf, this.controlName);
        ByteBufUtils.writeTag(byteBuf, this.controlTagCompound);
        NetworkHelper.writeBlockPos(byteBuf, this.blockPos);
    }

    public static class Handler implements IMessageHandler<PacketGuiObjectUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketGuiObjectUpdate packetGuiObjectUpdate, MessageContext messageContext) {
            FMLCommonHandler.instance().getWorldThread(messageContext.netHandler).addScheduledTask(() -> handle(packetGuiObjectUpdate, messageContext));

            return null;
        }

        private void handle(PacketGuiObjectUpdate packetGuiObjectUpdate, MessageContext messageContext) {
            TileEntity tileEntity;

            if (messageContext.side == Side.SERVER) {
                PacketGuiObjectUpdate packet = new PacketGuiObjectUpdate(packetGuiObjectUpdate.controlName, packetGuiObjectUpdate.controlTagCompound, packetGuiObjectUpdate.blockPos);
                PacketHandler.NETWORK_INSTANCE.sendToAllAround(packet, NetworkHelper.getTargetPoint(messageContext.getServerHandler().player.dimension, packetGuiObjectUpdate.blockPos, 50));

                tileEntity = getServerSideTileEntity(packetGuiObjectUpdate, messageContext);
            } else {
                tileEntity = getClientSideTileEntity(packetGuiObjectUpdate, messageContext);
            }

            // Verify that the tile entity isn't null and it is an instance of IGuiMaker
            if (tileEntity == null || (!(tileEntity instanceof IGuiMaker))) {
                return;
            }

            findControl(((IGuiMaker)tileEntity).getGuiWindow().getGuiObjects(), packetGuiObjectUpdate, tileEntity);

            tileEntity.markDirty();
        }

        private void findControl (ArrayList<GuiObject> guiObjectArrayList, PacketGuiObjectUpdate packetGuiObjectUpdate, TileEntity tileEntity) {
            for (GuiObject guiObject : guiObjectArrayList) {
                if (guiObject instanceof GuiBaseContainer) {
                    findControl(((GuiBaseContainer) guiObject).getGuiObjects(), packetGuiObjectUpdate, tileEntity);
                }
                if (guiObject.getControlName().equals(packetGuiObjectUpdate.controlName)) {
                    guiObject.readNBT(packetGuiObjectUpdate.controlTagCompound);
                    ((IGuiMaker) tileEntity).guiControlInteraction(packetGuiObjectUpdate.controlName, packetGuiObjectUpdate.controlTagCompound);
                }
            }
        }

        private TileEntity getServerSideTileEntity(PacketGuiObjectUpdate packetGuiObjectUpdate, MessageContext messageContext) {
            return TileHelper.getTileEntity(messageContext.getServerHandler().player.world, packetGuiObjectUpdate.blockPos, TileEntity.class);
        }

        private TileEntity getClientSideTileEntity(PacketGuiObjectUpdate packetGuiObjectUpdate, MessageContext messageContext) {
            return TileHelper.getTileEntity(Minecraft.getMinecraft().world, packetGuiObjectUpdate.blockPos, TileEntity.class);
        }
    }
}
