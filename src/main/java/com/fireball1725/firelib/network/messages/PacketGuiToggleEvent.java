/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.network.messages;

import com.fireball1725.firelib.guimaker.GuiControlState;
import com.fireball1725.firelib.guimaker.IGuiMaker;
import com.fireball1725.firelib.guimaker.capability.GuiMakerCapability;
import com.fireball1725.firelib.guimaker.capability.GuiMakerProvider;
import com.fireball1725.firelib.guimaker.capability.IGuiMakerCapability;
import com.fireball1725.firelib.network.PacketHandler;
import com.fireball1725.firelib.tileentities.TileEntityBase;
import com.fireball1725.firelib.util.TileHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class PacketGuiToggleEvent implements IMessage {
    public UUID controlUUID;
    public boolean toggleState;
    public BlockPos blockPos;

    public PacketGuiToggleEvent() {

    }

    public PacketGuiToggleEvent(UUID controlID, boolean toggleState, BlockPos blockPos) {
        this.controlUUID = controlID;
        this.toggleState = toggleState;
        this.blockPos = blockPos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.controlUUID = UUID.fromString(ByteBufUtils.readUTF8String(buf));
        this.toggleState = buf.readByte() == 1;
        this.blockPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, controlUUID.toString());
        buf.writeByte(toggleState ? 1 : 0);
        buf.writeInt(blockPos.getX());
        buf.writeInt(blockPos.getY());
        buf.writeInt(blockPos.getZ());
    }

    public static class Handler implements IMessageHandler<PacketGuiToggleEvent, IMessage> {

        @Override
        public IMessage onMessage(PacketGuiToggleEvent message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketGuiToggleEvent message, MessageContext ctx) {
            if (ctx.side == Side.SERVER) {
                PacketGuiToggleEvent packetGuiToggleEvent = new PacketGuiToggleEvent(message.controlUUID, message.toggleState, message.blockPos);
                //PacketHandler.NETWORK_INSTANCE.sendToAllAround(packetGuiToggleEvent, new NetworkRegistry.TargetPoint(ctx.getServerHandler().player.dimension, message.blockPos.getX(), message.blockPos.getY(), message.blockPos.getZ(), 100));
                PacketHandler.NETWORK_INSTANCE.sendToAll(packetGuiToggleEvent);
            }

            if (ctx.side == Side.SERVER) {
                updateServer(message, ctx);
            } else {
                updateClient(message, ctx);
            }
        }

        private void updateServer(PacketGuiToggleEvent message, MessageContext ctx) {
            TileEntityBase tileEntity = TileHelper.getTileEntity(ctx.getServerHandler().player.world, message.blockPos, TileEntityBase.class);

            if (tileEntity == null) {
                return;
            }

            if (tileEntity instanceof IGuiMaker) {
                if (message.toggleState) {
                    ((IGuiMaker) tileEntity).getGuiMaker().addGuiControlState(GuiControlState.SELECTED, message.controlUUID);
                } else {
                    ((IGuiMaker) tileEntity).getGuiMaker().removeGuiControlState(GuiControlState.SELECTED, message.controlUUID);
                }
            }

            NBTTagCompound controlStates = tileEntity.getControlStates();

            if (controlStates == null) {
                controlStates = new NBTTagCompound();
            }

            controlStates.setBoolean(message.controlUUID.toString(), message.toggleState);

            IGuiMakerCapability gui = tileEntity.getCapability(GuiMakerProvider.GUI_MAKER_CAPABILITY, EnumFacing.NORTH);



            tileEntity.setControlStates(controlStates);
            tileEntity.markDirty();
        }

        private void updateClient(PacketGuiToggleEvent message, MessageContext ctx) {
            TileEntityBase tileEntity = TileHelper.getTileEntity(Minecraft.getMinecraft().world, message.blockPos, TileEntityBase.class);

            if (tileEntity == null) {
                return;
            }

            if (tileEntity instanceof IGuiMaker) {
                if (message.toggleState) {
                    ((IGuiMaker) tileEntity).getGuiMaker().addGuiControlState(GuiControlState.SELECTED, message.controlUUID);
                } else {
                    ((IGuiMaker) tileEntity).getGuiMaker().removeGuiControlState(GuiControlState.SELECTED, message.controlUUID);
                }
            }

        }
    }
}