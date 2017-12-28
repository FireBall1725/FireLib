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
import com.fireball1725.firelib.guimaker.objects.GuiWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;

/**
 * GuiMaker Client Side Container
 */
public class GuiMakerGuiContainer extends GuiContainer {
    private final InventoryPlayer inventoryPlayer;
    private final TileEntity tileEntity;
    private final GuiMaker guiMaker;

    public GuiMakerGuiContainer(InventoryPlayer inventoryPlayer, TileEntity tileEntity, int id) {
        super(new GuiMakerContainer(inventoryPlayer, tileEntity, id));

        this.inventoryPlayer = inventoryPlayer;
        this.tileEntity = tileEntity;

        this.guiMaker = GuiMaker.getGuiMaker(id);

        if (this.guiMaker == null) {
            FireLib.instance.getLogger().fatal("GuiMaker is returning a null instance, this is a problem...");
        }

    }

    @Override
    public void initGui() {
        GuiWindow guiWindow = guiMaker.getGuiWindow();
        if (guiWindow != null) {
            this.xSize = guiWindow.getWidth();
            this.ySize = guiWindow.getHeight();
        }

        super.initGui();

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.initGui(this);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        scissorCut(this.guiLeft, this.guiTop, this.xSize, this.ySize);

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.drawGuiContainerBackgroundLayer(this, partialTicks, mouseX, mouseY);
            }
        }

        scissorsEnd();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        scissorCut(this.guiLeft + 2, this.guiTop + 2, this.xSize - 4, this.ySize - 4);

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.drawGuiContainerForegroundLayer(this, mouseX, mouseY);
            }
        }

        scissorsEnd();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.drawScreen(this, mouseX, mouseY, partialTicks);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.mouseClicked(this, mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.keyTyped(this, typedChar, keyCode);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.onGuiClosed(this);
            }
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        ArrayList<GuiObject> guiObjects = guiMaker.getGuiObjects();

        for (GuiObject guiObject : guiObjects) {
            if (guiObject != null) {
                guiObject.updateScreen(this);
            }
        }
    }

    // --------

    @SideOnly(Side.CLIENT)
    public void scissorCut(int x, int y, int w, int h) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int scale = scaledResolution.getScaleFactor();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(x * scale, y * scale, w * scale, h * scale);
    }

    @SideOnly(Side.CLIENT)
    public void scissorsEnd() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public TileEntity getTileEntity() {
        return tileEntity;
    }
}
