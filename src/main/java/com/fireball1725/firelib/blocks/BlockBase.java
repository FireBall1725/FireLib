/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.blocks;

import com.fireball1725.firelib.FireMod;
import com.fireball1725.firelib.tileentities.TileEntityBase;
import com.fireball1725.firelib.util.IBlockRenderer;
import com.fireball1725.firelib.util.StringUtilities;
import com.fireball1725.firelib.util.TileHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBase extends Block implements IBlockRenderer {
    protected static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected final String resourcePath;
    protected String internalName = "";
    protected boolean fallInstantly = false;

    protected BlockBase(Material material, String resourcePath) {
        super(material);

        if (this.canRotate()) {
            this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        }

        this.resourcePath = resourcePath;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (canRotate()) {
            return new BlockStateContainer(this, FACING);
        }
        return super.createBlockState();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (canRotate()) {
            EnumFacing playerFacing = placer.getHorizontalFacing().getOpposite();
            if (placer.isSneaking()) {
                playerFacing = placer.getHorizontalFacing();
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, playerFacing), 2);
        }
    }

    private boolean canFallThrough(IBlockState blockState) {
        Block block = blockState.getBlock();
        Material material = blockState.getMaterial();
        return block == Blocks.FIRE || material == Material.AIR || material == Material.WATER || material == Material.LAVA;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (canRotate()) {
            return (state.getValue(FACING)).getHorizontalIndex();
        }

        return super.getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (canRotate()) {
            return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3));
        }

        return super.getStateFromMeta(meta);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if (hasGravity(worldIn, pos)) {
            worldIn.scheduleUpdate(pos, this, 2);
        }

        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
        if (hasGravity(world, observerPos)) {
            world.scheduleUpdate(observerPos, this, 2);
        }

        super.observedNeighborChange(observerState, world, observerPos, changedBlock, changedBlockPos);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (!worldIn.isRemote) {
            this.checkFallable(worldIn, pos);
        }
    }

    private void checkFallable(World worldIn, BlockPos pos) {
        if (!hasGravity(worldIn, pos)) {
            return;
        }

        if ((worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down()))) && pos.getY() >= 0) {
            int i = 32;

            if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-i, -i, -i), pos.add(i, i, i))) {
                if (!worldIn.isRemote) {
                    FireMod.instance.getLogger().info(">>> Position: " + worldIn.getBlockState(pos).toString());
                    EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));

                    this.onStartFalling(entityfallingblock);
                    worldIn.spawnEntity(entityfallingblock);
                }
            } else {
                worldIn.setBlockToAir(pos);
                BlockPos blockpos;

                for (blockpos = pos.down(); (worldIn.isAirBlock(blockpos) || canFallThrough(worldIn.getBlockState(blockpos))) && blockpos.getY() > 0; blockpos = blockpos.down()) {

                }

                if (blockpos.getY() > 0) {
                    worldIn.setBlockState(blockpos.up(), this.getDefaultState());
                }
            }
        }
    }

    protected void onStartFalling(EntityFallingBlock fallingEntity) {

    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public boolean hasGravity(World worldIn, BlockPos pos) {
        return false;
    }

    public boolean canRotate() {
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        String blockName = getUnwrappedUnlocalizedName(super.getUnlocalizedName());

        return String.format("tile.%s.%s", FireMod.instance.getModId(), blockName);
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf('.') + 1);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntityBase tileEntity = TileHelper.getTileEntity(world, pos, TileEntityBase.class);
        if (tileEntity != null && tileEntity.hasCustomName()) {
            final ItemStack itemStack = new ItemStack(this, 1, tileEntity.getBlockMetadata());
            itemStack.setStackDisplayName(tileEntity.getCustomName());

            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            drops.add(itemStack);

            return drops;
        }
        return super.getDrops(world, pos, state, fortune);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockRenderer() {
        final String resource = String.format("%s:%s", FireMod.instance.getModId(), this.resourcePath);

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @SideOnly(Side.CLIENT)
            @Override
            @MethodsReturnNonnullByDefault
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(resource, getPropertyString(state.getProperties()));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        final String resource = String.format("%s:%s", FireMod.instance.getModId(), this.resourcePath);

        NonNullList<ItemStack> subBlocks = NonNullList.create();
        getSubBlocks(null, subBlocks);

        for (ItemStack itemStack : subBlocks) {
            IBlockState blockState = this.getStateFromMeta(itemStack.getItemDamage());
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), itemStack.getItemDamage(), new ModelResourceLocation(resource, StringUtilities.getPropertyString(blockState.getProperties())));
        }
    }
}
