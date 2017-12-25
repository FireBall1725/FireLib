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

import net.minecraft.client.gui.inventory.GuiContainer;

import java.io.IOException;

public interface IGuiObject {
    void initGui(GuiContainer guiContainer);

    void drawScreen(GuiContainer guiContainer, int mouseX, int mouseY, float partialTicks);

    void drawGuiContainerForegroundLayer(GuiContainer guiContainer, int mouseX, int mouseY);

    void drawGuiContainerBackgroundLayer(GuiContainer guiContainer, float partialTicks, int mouseX, int mouseY);

    void mouseClicked(GuiContainer guiContainer, int mouseX, int mouseY, int mouseButton) throws IOException;

    void keyTyped(GuiContainer guiContainer, char typedChar, int keyCode) throws IOException;

    void onGuiClosed(GuiContainer guiContainer);

    void updateScreen(GuiContainer guiContainer);
}