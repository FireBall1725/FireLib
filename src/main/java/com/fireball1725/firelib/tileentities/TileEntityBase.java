/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileEntityBase extends TileEntity {
    private String customName;
    private int renderedFragment = 0;
    private NBTTagCompound controlStates;

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
        super.onDataPacket(networkManager, s35PacketUpdateTileEntity);
        readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
        this.getWorld().markBlockRangeForRenderUpdate(this.pos, this.pos);
        markForUpdate();
    }

    public void markForUpdate() {
        if (this.renderedFragment > 0) {
            this.renderedFragment |= 0x1;
        } else if (this.getWorld() != null) {
            Block block = this.getWorld().getBlockState(this.pos).getBlock();
            //todo: look at this, is it correct?
            this.getWorld().notifyBlockUpdate(this.pos, this.getWorld().getBlockState(this.pos), this.getWorld().getBlockState(this.pos), 3);

            int xCoord = this.pos.getX();
            int yCoord = this.pos.getY();
            int zCoord = this.pos.getZ();

            // Todo: update detectors?
            this.getWorld().notifyNeighborsOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord), block, false);
            this.getWorld().notifyNeighborsOfStateChange(new BlockPos(xCoord, yCoord + 1, zCoord), block, false);
            this.getWorld().notifyNeighborsOfStateChange(new BlockPos(xCoord - 1, yCoord, zCoord), block, false);
            this.getWorld().notifyNeighborsOfStateChange(new BlockPos(xCoord + 1, yCoord, zCoord), block, false);
            this.getWorld().notifyNeighborsOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord - 1), block, false);
            this.getWorld().notifyNeighborsOfStateChange(new BlockPos(xCoord, yCoord - 1, zCoord + 1), block, false);
        }
    }

    public void markForLightUpdate() {
        if (this.getWorld().isRemote) {
            this.getWorld().notifyBlockUpdate(this.pos, this.getWorld().getBlockState(this.pos), this.getWorld().getBlockState(this.pos), 3);
        }

        this.getWorld().checkLightFor(EnumSkyBlock.BLOCK, this.pos);
    }

    public void onChunkLoad() {
        if (this.isInvalid())
            this.validate();

        markForUpdate();
    }

    @Override
    public void onChunkUnload() {
        if (!this.isInvalid())
            this.invalidate();
    }

    public TileEntity getTile() {
        return this;
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public boolean hasCustomName() {
        return (this.customName != null) && (this.customName.length() > 0);
    }

    public String getUnlocalizedName() {
        Item item = Item.getItemFromBlock(this.getWorld().getBlockState(this.pos).getBlock());
        ItemStack itemStack = new ItemStack(item, 1, getBlockMetadata());

        return itemStack.getUnlocalizedName() + ".name";
    }

    public void setName(String name) {
        this.customName = name;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        if (this.customName != null)
            nbtTagCompound.setString("CustomName", this.customName);

        if (this.controlStates != null)
            nbtTagCompound.setTag("ControlState", this.controlStates);

        return super.writeToNBT(nbtTagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);

        this.customName = nbtTagCompound.hasKey("CustomName") ? nbtTagCompound.getString("CustomName") : null;

        this.controlStates = nbtTagCompound.hasKey("ControlState") ? nbtTagCompound.getCompoundTag("ControlState") : null;
    }

    public IBlockState getBlockState() {
        if (this.getWorld() == null)
            return null;

        return this.getWorld().getBlockState(pos);
    }

    public NBTTagCompound getControlStates() {
        return controlStates;
    }

    public void setControlStates(NBTTagCompound controlStates) {
        this.controlStates = controlStates;
    }

    //    @Override
//    public List<String> getWailaHeadToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
//        if (customName != null)
//            currentTip.add(String.format("%s%s%s", TextFormatting.BLUE, TextFormatting.ITALIC, customName));
//
//        return currentTip;
//    }

//    public void dropItems() {
//        TileHelper.dropItems(this);
//    }
}
