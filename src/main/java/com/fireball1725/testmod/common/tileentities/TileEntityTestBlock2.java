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
import com.fireball1725.firelib.guimaker.IGuiMaker;
import com.fireball1725.firelib.guimaker.objects.GuiCheckbox;
import com.fireball1725.firelib.guimaker.objects.GuiWindow;
import com.fireball1725.firelib.tileentities.TileEntityBase;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTestBlock2 extends TileEntityBase implements IGuiMaker {
    public GuiMaker guiMaker = new GuiMaker();
    public GuiWindow guiWindow = new GuiWindow(100, 40);

    public GuiCheckbox guiCheckbox = new GuiCheckbox(GuiMaker.generateUUIDFromName("buttonSomething"));
    public GuiCheckbox guiCheckbox2 = new GuiCheckbox(GuiMaker.generateUUIDFromName("buttonSomething2"));

    private int test = 0;

    public TileEntityTestBlock2() {
        guiMaker.registerGuiObject(guiWindow);
        guiMaker.registerGuiObject(guiCheckbox);
        guiMaker.registerGuiObject(guiCheckbox2);

        guiCheckbox.setLocation(4, 4);
        guiCheckbox2.setLocation(4, 18);
    }

    @Override
    public GuiMaker getGuiMaker() {
        return guiMaker;
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
