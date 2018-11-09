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
import com.fireball1725.firelib.guimaker.base.GuiBaseContainer;
import com.fireball1725.firelib.guimaker.containers.GuiWindow;
import com.fireball1725.firelib.guimaker.controls.GuiCheckbox;
import com.fireball1725.firelib.guimaker.controls.GuiDrawItemStack;
import com.fireball1725.firelib.guimaker.controls.GuiLabel;
import com.fireball1725.firelib.guimaker.util.IGuiMaker;
import com.fireball1725.firelib.tileentities.TileEntityBase;
import com.fireball1725.testmod.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityTestBlock2 extends TileEntityBase implements IGuiMaker, ITickable {
    // Gui Stuff
    GuiWindow mainWindow = new GuiWindow("MainWindow", 200, 200);
    GuiLabel labelWindowTitle = new GuiLabel("Label");
    GuiCheckbox checkboxTest = new GuiCheckbox("TestCheckBox");

    GuiWindow testWindow = new GuiWindow("TestWindow", 100, 20);
    GuiCheckbox checkboxTest2 = new GuiCheckbox("TestCheckBox2");

    private int test = 0;

    public TileEntityTestBlock2() {
        labelWindowTitle.setLabel("Hello World");
        //labelWindowTitle.setLocation(2, 2);
        labelWindowTitle.setColor(0xFF69B4);
        labelWindowTitle.setLeft(10);
        labelWindowTitle.setTop(50);
        mainWindow.addGuiObject(labelWindowTitle);

        //checkboxTest.setLocation(2, 10);
        checkboxTest.setLeft(30);
        mainWindow.addGuiObject(checkboxTest);

        //checkboxTest2.setLocation(2, 2);
        testWindow.addGuiObject(checkboxTest2);

        testWindow.setLeft(10);
        testWindow.setTop(20);

        mainWindow.addGuiObject(testWindow);
    }

    @Override
    public boolean canInteractWith() {
        return true;
    }

    @Override
    public void guiControlInteraction(String guiControlName, NBTTagCompound nbtTagCompound) {
        TestMod.instance.getLogger().info(">>> INTERACTION " + guiControlName);
        // todo something here...

        if (guiControlName.equals("testcheckbox")) {
            testWindow.setContainerVisible(checkboxTest.isChecked());
        }
    }

    @Override
    public GuiBaseContainer getGuiWindow() {
        return mainWindow;
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

    @Override
    public void update() {

    }
}
