/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker.base;

import com.fireball1725.firelib.FireLib;
import com.fireball1725.firelib.guimaker.GuiMaker;
import com.fireball1725.firelib.guimaker.util.GuiControlOption;
import com.fireball1725.firelib.guimaker.util.GuiControlState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GuiObject implements IGuiObject {
    //todo: this really needs to be fixed...
    protected final ResourceLocation DarkSkin = new ResourceLocation("firelib", "textures/gui/dark.png");

    @SideOnly(Side.CLIENT)
    protected GuiContainer guiContainer;
    private GuiObject parent;
    protected final String controlName;
    private int left, top, width, height;

    public GuiObject(String controlName) {
        this.controlName = controlName.toLowerCase().replace(' ', '-');
    }

    public GuiObject(String controlName, int left, int top) {
        this(controlName);
        this.left = left;
        this.top = top;
    }

    public GuiObject(String controlName, int left, int top, int width, int height) {
        this(controlName, left, top);
        this.width = width;
        this.height = height;
    }

    @SideOnly(Side.CLIENT)
    public void setGuiContainer(GuiContainer guiContainer) {
        this.guiContainer = guiContainer;
    }

    public GuiObject getParent() {
        return parent;
    }

    public void setParent(GuiObject parent) {
        this.parent = parent;
    }

    public int getLeft() {
        int padding = this.parent == null ? this.guiContainer.getGuiLeft() : this.parent.getLeft();
        if (this.parent instanceof GuiBaseContainer) {
            padding += ((GuiBaseContainer)this.parent).getPaddingLeft();
        }
        return left + padding;
    }

    public int getForegroundLeft() {
        int padding = this.parent == null ? 0 : this.parent.getForegroundLeft();
        if (this.parent instanceof GuiBaseContainer) {
            padding += ((GuiBaseContainer)this.parent).getPaddingLeft();
        }
        return left + padding;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        int padding = this.parent == null ? this.guiContainer.getGuiTop() : this.parent.getTop();
        if (this.parent instanceof GuiBaseContainer) {
            padding += ((GuiBaseContainer)this.parent).getPaddingTop();
        }
        return top + padding;
    }

    public int getForegroundTop() {
        int padding = this.parent == null ? 0 : this.parent.getForegroundTop();
        if (this.parent instanceof GuiBaseContainer) {
            padding += ((GuiBaseContainer)this.parent).getPaddingTop();
        }
        return top + padding;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        int paddingLeft = 0;
        int paddingRight = 0;
        if (this.parent instanceof GuiBaseContainer) {
            paddingLeft = ((GuiBaseContainer)this.parent).getPaddingLeft();
            paddingRight = ((GuiBaseContainer)this.parent).getPaddingRight();
        }
        return width;// - paddingLeft - paddingRight;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        int paddingTop = 0;
        int paddingBottom = 0;
        if (this.parent instanceof GuiBaseContainer) {
            paddingTop = ((GuiBaseContainer)this.parent).getPaddingTop();
            paddingBottom = ((GuiBaseContainer)this.parent).getPaddingBottom();
        }
        return height;//- paddingTop - paddingBottom;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initGui() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onGuiClosed() {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateScreen() {

    }

    @Override
    public NBTTagCompound writeNBT() {
        return null;
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {

    }

    public String getControlName() {
        return controlName;
    }

    @SideOnly(Side.CLIENT)
    public void renderItemStackToolTip(ItemStack itemStack, int x, int y) {
        FontRenderer font = itemStack.getItem().getFontRenderer(itemStack) == null ? Minecraft.getMinecraft().fontRenderer : itemStack.getItem().getFontRenderer(itemStack);

        Minecraft minecraft = Minecraft.getMinecraft();


        List<String> list = itemStack.getTooltip(Minecraft.getMinecraft().player, Minecraft.getMinecraft().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);

        for (int i = 0; i < list.size(); ++i) {
            if (i != 0) {
                list.set(i, TextFormatting.GRAY + list.get(i));
                continue;
            }

            list.set(i, itemStack.getRarity().rarityColor + list.get(i));
        }

        GuiUtils.drawHoveringText(list, x, y, this.guiContainer.width, this.guiContainer.height, 100, font);
    }
}
