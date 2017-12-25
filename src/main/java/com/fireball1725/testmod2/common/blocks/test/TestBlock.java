/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.testmod2.common.blocks.test;

import com.fireball1725.firelib.blocks.BlockTileBase;
import com.fireball1725.firelib.guimaker.GuiMaker;
import com.fireball1725.firelib.guimaker.objects.GuiLabel;
import com.fireball1725.firelib.guimaker.objects.GuiWindow;
import com.fireball1725.firelib.util.IProvideRecipe;
import com.fireball1725.testmod2.TestMod2;
import com.fireball1725.testmod2.common.tileentities.TileEntityTestBlock2;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;

public class TestBlock extends BlockTileBase implements IProvideRecipe {
    private GuiMaker guiMaker = new GuiMaker();
    private GuiWindow guiWindow = new GuiWindow(200, 100);
    private GuiLabel guiLabel = new GuiLabel(4, 4);
    private GuiLabel guiLabel2 = new GuiLabel(4, 16);

    public TestBlock() {
        super(Material.IRON, "testblock", TestMod2.instance);
        this.setInternalName("testblock");
        this.setTileEntity(TileEntityTestBlock2.class);

        guiMaker.registerGuiObject(guiWindow);
        guiMaker.registerGuiObject(guiLabel);
        guiMaker.registerGuiObject(guiLabel2);

        guiLabel.setLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel magna ac nunc congue egestas sed a erat. Nam a odio elementum, pharetra turpis sit amet, blandit magna. Fusce vestibulum risus enim, id porta");
        guiLabel.setColor(0xFF69B4);

        guiLabel2.setLabel("This is a test...");
        guiLabel2.setColor(0xFFFFFF);
    }

    @Override
    public boolean hasGravity(World worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canRotate() {
        return true;
    }


    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        guiMaker.show(worldIn, playerIn, pos);

        return true;
    }
}
