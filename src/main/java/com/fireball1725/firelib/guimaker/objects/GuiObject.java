/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker.objects;

import com.fireball1725.firelib.FireMod;
import com.fireball1725.firelib.util.ControlState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sun.rmi.log.LogHandler;

import java.awt.*;
import java.io.IOException;

public abstract class GuiObject implements IGuiObject {
    protected final ResourceLocation DarkSkin = new ResourceLocation("firelib", "textures/gui/dark.png");

    protected final int controlID;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected int mouseX;
    protected int mouseY;

    protected ControlState controlState = ControlState.NORMAL;

    private int offsetX;
    private int offsetY;

    public GuiObject(int controlId) {
        this.controlID = controlId;
    }

    // ----------

    @Override
    public void initGui(GuiContainer guiContainer) {

    }

    @Override
    public void drawScreen(GuiContainer guiContainer, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public void drawGuiContainerForegroundLayer(GuiContainer guiContainer, int mouseX, int mouseY) {

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(GuiContainer guiContainer, float partialTicks, int mouseX, int mouseY) {
        Rectangle rectangle = new Rectangle(guiContainer.getGuiLeft() + this.x, guiContainer.getGuiTop() + y, this.width, this.height);
        boolean mouseOver = rectangle.contains(mouseX, mouseY);

        switch (this.controlState) {
            default:
            case DISABLED:
            case DISABLED_SELECTED:
            case INVISIBLE:
                break;
            case NORMAL:
                if (mouseOver) {
                    this.controlState = ControlState.HOVERED;
                }
                break;
            case HOVERED:
                if (!mouseOver) {
                    this.controlState = ControlState.NORMAL;
                }
                break;
            case SELECTED:
                if (mouseOver) {
                    this.controlState = ControlState.SELECTED_HOVER;
                }
                break;
            case SELECTED_HOVER:
                if (!mouseOver) {
                    this.controlState = ControlState.SELECTED;
                }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void mouseClicked(GuiContainer guiContainer, int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.controlState == ControlState.DISABLED ||
                this.controlState == ControlState.DISABLED_SELECTED ||
                this.controlState == ControlState.INVISIBLE
                ) {
            return;
        }

        Rectangle rectangle = new Rectangle(guiContainer.getGuiLeft() + this.x, guiContainer.getGuiTop() + y, this.width, this.height);
        boolean mouseOver = rectangle.contains(mouseX, mouseY);

        if (mouseOver && mouseButton == 0) {
            switch (this.controlState) {
                default:
                case NORMAL:
                case HOVERED:
                    this.controlState = ControlState.SELECTED;
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                    break;
                case SELECTED:
                case SELECTED_HOVER:
                    this.controlState = ControlState.NORMAL;
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                    break;
            }
            FireMod.instance.getLogger().info(">>> CLICK " + this.controlState.name() + " - " + mouseButton);
        }
    }

    @Override
    public void keyTyped(GuiContainer guiContainer, char typedChar, int keyCode) throws IOException {

    }

    @Override
    public void onGuiClosed(GuiContainer guiContainer) {

    }

    @Override
    public void updateScreen(GuiContainer guiContainer) {

    }

    // --------


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
