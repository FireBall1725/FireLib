/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker.controls;

import com.fireball1725.firelib.guimaker.base.GuiBaseControl;
import com.fireball1725.firelib.guimaker.util.GuiControlOption;
import com.fireball1725.firelib.guimaker.util.GuiControlState;
import com.fireball1725.firelib.guimaker.util.ToolTipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class GuiDrawItemStack extends GuiBaseControl {
    private long displayTime = System.currentTimeMillis();
    private NonNullList<ItemStack> itemStacks = NonNullList.create();
    private int displayID = 0;
    private boolean showItemToolTip = true;

    public GuiDrawItemStack(String controlName) {
        super(controlName, 16, 16);
        //this.setSize(16, 16);

    }

    public void addItemStack(ItemStack itemStack) {
        if (!itemStack.getHasSubtypes() && !itemExists(itemStack)) {
            this.itemStacks.add(itemStack);
            return;
        }

        for (CreativeTabs creativeTab : itemStack.getItem().getCreativeTabs()) {
            NonNullList<ItemStack> itemStacks = NonNullList.create();
            itemStack.getItem().getSubItems(creativeTab, itemStacks);

            for (ItemStack itemStackSubItem : itemStacks) {
                if (!itemExists(itemStackSubItem)) {
                    this.itemStacks.add(itemStackSubItem);
                }
            }
        }
    }

    public void setShowItemToolTip(boolean showItemToolTip) {
        this.showItemToolTip = showItemToolTip;
    }

    public void addItemStack(NonNullList<ItemStack> itemStacks) {
        for (ItemStack itemStack : itemStacks) {
            addItemStack(itemStack);
        }
    }

    private boolean itemExists(ItemStack itemIn) {
        for (ItemStack itemStack : this.itemStacks) {
            if (ItemStack.areItemStacksEqual(itemStack, itemIn)) {
                return true;
            }
        }

        return false;
    }

//    @Override
//    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
//
//        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
//
//        ItemStack itemStack = this.itemStacks.get(displayID);
//
//        GlStateManager.pushMatrix();
//
//        GlStateManager.enableDepth();
//        RenderHelper.enableGUIStandardItemLighting();
//
//        renderItem.renderItemAndEffectIntoGUI(itemStack, this.getContainerLeft(), this.getContainerTop());
//
//        GlStateManager.popMatrix();
//
//        if (System.currentTimeMillis() > this.displayTime + 1500) {
//            this.displayTime = System.currentTimeMillis();
//            displayID = (++displayID) % itemStacks.size();
//        }
//
//
//    }

//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        super.drawScreen(mouseX, mouseY, partialTicks);
//
//        if (this.showItemToolTip && this.hasGuiControlState(GuiControlState.HOVERED)) {
//            List<String> toolTip = ToolTipHelper.getItemStackToolTip(this.itemStacks.get(displayID));
//
//            GuiUtils.drawHoveringText(this.itemStacks.get(displayID), toolTip, mouseX, mouseY, this.guiContainer.width, this.guiContainer.height, 200, Minecraft.getMinecraft().fontRenderer);
//            //todo: make a better hover text helper...
//        }
//    }
}
