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

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;

public abstract class GuiBaseContainer extends GuiObject {
    private final int paddingLeft, paddingTop, paddingBottom, paddingRight;
    protected int zLevel = 1;
    private ArrayList<GuiObject> guiObjects = new ArrayList<>();
    private boolean containerVisible = true;

    public boolean isContainerVisible() {
        return containerVisible;
    }

    public void setContainerVisible(boolean containerVisible) {
        this.containerVisible = containerVisible;
    }

    public GuiBaseContainer(String controlName, int paddingLeft, int paddingTop, int paddingBottom, int paddingRight) {
        super(controlName);
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingRight = paddingRight;
    }

    public void addGuiObject(GuiObject guiObject) {
        if (guiObject != null) {
            guiObject.setParent(this);
            this.guiObjects.add(guiObject);

            if (guiObject instanceof GuiBaseContainer) {
                ((GuiBaseContainer) guiObject).zLevel += 10;
            }
        }
    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public ArrayList<GuiObject> getGuiObjects() {
        return this.guiObjects;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initGui() {
        super.initGui();

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                //guiObject.setGuiMaker(this.guiMaker);
                guiObject.setGuiContainer(this.guiContainer);

                guiObject.initGui();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.drawScreen(mouseX, mouseY, partialTicks);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.drawGuiContainerForegroundLayer(mouseX, mouseY);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.keyTyped(typedChar, keyCode);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.onGuiClosed();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateScreen() {
        super.updateScreen();

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.updateScreen();
            }
        }
    }
}