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

import com.fireball1725.firelib.guimaker.GuiMaker;
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
import java.util.List;

public abstract class GuiObject implements IGuiObject {
    protected final ResourceLocation DarkSkin = new ResourceLocation("firelib", "textures/gui/dark.png");
    protected final String controlName;
    protected GuiMaker guiMaker;
    @SideOnly(Side.CLIENT)
    protected GuiContainer guiContainer;
    private GuiObject parent;
    private int x;
    private int y;
    private int w;
    private int h;

    public GuiObject(String controlName) {
        // Set the control name
        this.controlName = controlName.toLowerCase().replace(' ', '-');
    }

    public void setGuiMaker(GuiMaker guiMaker) {
        this.guiMaker = guiMaker;
    }

    @SideOnly(Side.CLIENT)
    public void setGuiContainer(GuiContainer guiContainer) {
        this.guiContainer = guiContainer;
    }

    public void setParent(GuiObject guiObject) {
        this.parent = guiObject;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int w) {
        this.w = w;
    }

    public void setHeight(int h) {
        this.h = h;
    }

    public int getWidth() {
        //todo: scale..

        return this.w;
    }

    public int getHeight() {
        // todo: scale

        return this.h;
    }

    public int getContainerLeft() {
        // todo: scale / scroll

        int parentLeft = this.parent == null ? 0 : this.parent.x;
        return this.guiContainer.getGuiLeft() + this.x + parentLeft;
    }

    public int getContainerTop() {
        // todo: scale / scroll

        int parentTop = this.parent == null ? 0 : this.parent.y;
        return this.guiContainer.getGuiTop() + this.y + parentTop;
    }

    public int getLeft() {
        // todo: scale / scroll

        int parentLeft = this.parent == null ? 0 : this.parent.x;
        return this.x + parentLeft;
    }

    public int getTop() {
        // todo: scale / scroll

        int parentTop = this.parent == null ? 0 : this.parent.y;
        return this.y + parentTop;
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
