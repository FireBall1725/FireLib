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
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public abstract class GuiObject implements IGuiObject {
    protected final ResourceLocation DarkSkin = new ResourceLocation("firelib", "textures/gui/dark.png");

    protected final String controlName;

    // Set from GuiContainer
    protected GuiMaker guiMaker = null;

    @SideOnly(Side.CLIENT)
    protected GuiContainer guiContainer;
    // ---------------------

    public GuiObject(String controlName) {
        // Set the control name
        this.controlName = controlName.toLowerCase().replace(' ', '-');
    }

    /**
     * Set the parent gui maker
     *
     * @param guiMaker the GuiMaker instance to set
     */
    public void setGuiMaker(GuiMaker guiMaker) {
        this.guiMaker = guiMaker;
    }

    /**
     * Set the parent gui container
     *
     * @param guiContainer the GuiContainer instance to set
     */
    @SideOnly(Side.CLIENT)
    public void setGuiContainer(GuiContainer guiContainer) {
        this.guiContainer = guiContainer;
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
}
