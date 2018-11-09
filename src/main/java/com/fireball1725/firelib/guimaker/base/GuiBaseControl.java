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

import com.fireball1725.firelib.guimaker.GuiMakerGuiContainer;
import com.fireball1725.firelib.guimaker.network.PacketGuiObjectUpdate;
import com.fireball1725.firelib.guimaker.util.GuiControlOption;
import com.fireball1725.firelib.guimaker.util.GuiControlState;
import com.fireball1725.firelib.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public abstract class GuiBaseControl extends GuiObject {

    private final int controlWidth, controlHeight;
    private boolean controlVisible = true;
    private boolean controlEnabled = true;
    private float scaleX, scaleY, scaleZ;
    private boolean controlHovered;

    public GuiBaseControl(String controlName, int controlWidth, int controlHeight) {
        super(controlName);
        this.controlWidth = controlWidth;
        this.controlHeight = controlHeight;
        this.setWidth(controlWidth);
        this.setHeight(controlHeight);
    }

    public GuiBaseControl(String controlName, int left, int top, int controlWidth, int controlHeight) {
        super(controlName, left, top);
        this.controlWidth = controlWidth;
        this.controlHeight = controlHeight;
        this.setWidth(controlWidth);
        this.setHeight(controlHeight);
    }

    public GuiBaseControl(String controlName, int left, int top, int width, int height, int controlWidth, int controlHeight) {
        super(controlName, left, top, width, height);
        this.controlWidth = controlWidth;
        this.controlHeight = controlHeight;
        this.setWidth(controlWidth);
        this.setHeight(controlHeight);
    }

    public boolean isControlHovered() {
        return controlHovered;
    }

    public boolean isControlVisible() {
        return controlVisible;
    }

    public void setControlVisible(boolean controlVisible) {
        this.controlVisible = controlVisible;
    }

    public boolean isControlEnabled() {
        return controlEnabled;
    }

    public void setControlEnabled(boolean controlEnabled) {
        this.controlEnabled = controlEnabled;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        if (!this.controlVisible) {
            return;
        }

        Rectangle rectangle = new Rectangle(this.getLeft(), this.getTop(), this.getWidth(), this.getHeight());
        this.controlHovered = rectangle.contains(mouseX, mouseY);
    }
}
