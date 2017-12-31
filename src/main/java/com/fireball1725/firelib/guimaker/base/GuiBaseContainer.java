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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.ArrayList;

public abstract class GuiBaseContainer extends GuiObject {
    protected int width = 0;
    protected int height = 0;
    protected int zLevel = 1;
    private int scrollOffsetX = 0;
    private int scrollOffsetY = 0;
    private ArrayList<GuiObject> guiObjects = new ArrayList<>();

    public GuiBaseContainer(String controlName) {
        super(controlName);
    }

    public void addGuiObject(GuiObject guiObject) {
        if (guiObject != null) {
            this.guiObjects.add(guiObject);

            if (guiObject instanceof GuiBaseContainer) {
                ((GuiBaseContainer) guiObject).zLevel += 10;
            }
        }
    }

    public ArrayList<GuiObject> getGuiObjects() {
        return this.guiObjects;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void initGui(GuiContainer guiContainer) {
        super.initGui(guiContainer);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.setGuiMaker(this.guiMaker);

                guiObject.initGui(guiContainer);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawScreen(GuiContainer guiContainer, int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(guiContainer, mouseX, mouseY, partialTicks);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.drawScreen(guiContainer, mouseX, mouseY, partialTicks);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerForegroundLayer(GuiContainer guiContainer, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(guiContainer, mouseX, mouseY);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.drawGuiContainerForegroundLayer(guiContainer, mouseX, mouseY);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(GuiContainer guiContainer, float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(guiContainer, partialTicks, mouseX, mouseY);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.drawGuiContainerBackgroundLayer(guiContainer, partialTicks, mouseX, mouseY);
            }
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void mouseClicked(GuiContainer guiContainer, int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(guiContainer, mouseX, mouseY, mouseButton);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.mouseClicked(guiContainer, mouseX, mouseY, mouseButton);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void keyTyped(GuiContainer guiContainer, char typedChar, int keyCode) throws IOException {
        super.keyTyped(guiContainer, typedChar, keyCode);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.keyTyped(guiContainer, typedChar, keyCode);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onGuiClosed(GuiContainer guiContainer) {
        super.onGuiClosed(guiContainer);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.onGuiClosed(guiContainer);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void updateScreen(GuiContainer guiContainer) {
        super.updateScreen(guiContainer);

        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject != null) {
                guiObject.updateScreen(guiContainer);
            }
        }
    }
}