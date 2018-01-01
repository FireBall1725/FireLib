/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.testmod.common.tileentities;

import com.fireball1725.firelib.guimaker.GuiMaker;
import com.fireball1725.firelib.guimaker.containers.GuiWindow;
import com.fireball1725.firelib.guimaker.controls.GuiCheckbox;
import com.fireball1725.firelib.guimaker.controls.GuiDrawItemStack;
import com.fireball1725.firelib.guimaker.util.IGuiMaker;
import com.fireball1725.firelib.tileentities.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityTestBlock2 extends TileEntityBase implements IGuiMaker {
    public GuiWindow guiWindow = new GuiWindow("window", 100, 40);
    public GuiMaker guiMaker = new GuiMaker(guiWindow);

    public GuiCheckbox guiCheckbox = new GuiCheckbox("test button 1");
    public GuiCheckbox guiCheckbox2 = new GuiCheckbox("test button 2");
    public GuiDrawItemStack guiDrawItemStack = new GuiDrawItemStack("test stack");

    private int test = 0;

    public TileEntityTestBlock2() {
        //guiWindow = new GuiWindow("window", 100, 40);

        //guiMaker = new GuiMaker(guiWindow);

        //guiCheckbox = new GuiCheckbox("test button 1");
        guiCheckbox.setControlPosition(4, 4);
        guiCheckbox.setLabel("Test Checkbox", 0xff69b4);
        guiWindow.addGuiObject(guiCheckbox);

        //guiCheckbox2 = new GuiCheckbox("test button 2");
        guiCheckbox2.setControlPosition(4, 20);
        guiWindow.addGuiObject(guiCheckbox2);

        guiDrawItemStack.setControlPosition(20, 20);
        guiDrawItemStack.addItemStack(OreDictionary.getOres("slimeball"));
        guiDrawItemStack.addItemStack(OreDictionary.getOres("logWood"));
        guiDrawItemStack.addItemStack(OreDictionary.getOres("dye"));
        guiWindow.addGuiObject(guiDrawItemStack);
    }

    @Override
    public GuiMaker getGuiMaker() {
        return guiMaker;
    }

    @Override
    public boolean canInteractWith() {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        test = compound.getInteger("test");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("test", test);

        return super.writeToNBT(compound);
    }
}
