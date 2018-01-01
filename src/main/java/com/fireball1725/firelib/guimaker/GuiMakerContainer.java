/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker;

import com.fireball1725.firelib.FireLib;
import com.fireball1725.firelib.guimaker.base.GuiObject;
import com.fireball1725.firelib.guimaker.util.IGuiMaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * GuiMaker Server Side Container
 */
public class GuiMakerContainer extends Container {
    private final InventoryPlayer inventoryPlayer;
    private final TileEntity tileEntity;
    private final GuiMaker guiMaker;

    public GuiMakerContainer(InventoryPlayer inventoryPlayer, TileEntity tileEntity, int id) {
        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = tileEntity;
        this.guiMaker = ((IGuiMaker) tileEntity).getGuiMaker();

        if (this.guiMaker == null) {
            FireLib.instance.getLogger().fatal("GuiMaker is returning a null instance, this is a problem...");
        }

        initContainer();
    }

    public void initContainer() {
        this.inventoryItemStacks.clear();
        this.inventorySlots.clear();

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiContainer().getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.setGuiMaker(this.guiMaker);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        if (tileEntity instanceof IGuiMaker) {
            return ((IGuiMaker) tileEntity).canInteractWith();
        }

        return false;
    }
}
