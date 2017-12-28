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
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public abstract class GuiObject implements IGuiObject {
    protected final ResourceLocation DarkSkin = new ResourceLocation("firelib", "textures/gui/dark.png");

    protected final UUID controlID;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected int mouseX;
    protected int mouseY;

    private ArrayList<GuiControlState> controlStates = new ArrayList<>();
    private boolean supportsHover = false;
    private boolean supportsToggle = false;
    private boolean supportsClick = false;

    private int offsetX;
    private int offsetY;

    public GuiObject(UUID controlId, GuiControlOption... guiOptions) {
        this.controlID = controlId;

        for (GuiControlOption guiOption : guiOptions) {
            switch (guiOption) {
                case HOVER_STATE:
                    this.supportsHover = true;
                    break;
                case TOGGLE_STATE:
                    this.supportsToggle = true;
                    break;
                case BUTTON_CLICK:
                    this.supportsClick = true;
                    break;
            }
        }
    }

    public void addGuiControlState(GuiControlState guiControlState) {
        if (!this.controlStates.contains(guiControlState)) {
            this.controlStates.add(guiControlState);
        }
    }

    public void removeGuiControlState(GuiControlState guiControlState) {
        if (this.controlStates.contains(guiControlState)) {
            this.controlStates.remove(guiControlState);
        }
    }

    public boolean hasGuiControlState(GuiControlState guiControlState) {
        return this.controlStates.contains(guiControlState);
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
        if (this.hasGuiControlState(GuiControlState.INVISIBLE)) {
            return;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(GuiContainer guiContainer, float partialTicks, int mouseX, int mouseY) {
        if (this.hasGuiControlState(GuiControlState.INVISIBLE)) {
            return;
        }

        if (this.supportsHover) {
            Rectangle rectangle = new Rectangle(guiContainer.getGuiLeft() + this.x, guiContainer.getGuiTop() + y, this.width, this.height);
            boolean mouseOver = rectangle.contains(mouseX, mouseY);

            if (mouseOver) {
                this.addGuiControlState(GuiControlState.HOVERED);
            } else {
                this.removeGuiControlState(GuiControlState.HOVERED);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void mouseClicked(GuiContainer guiContainer, int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.hasGuiControlState(GuiControlState.INVISIBLE)) {
            return;
        }

        TileEntity te = ((GuiMakerGuiContainer) guiContainer).getTileEntity();

        Rectangle rectangle = new Rectangle(guiContainer.getGuiLeft() + this.x, guiContainer.getGuiTop() + y, this.width, this.height);
        boolean mouseOver = rectangle.contains(mouseX, mouseY);

        if (this.supportsToggle && mouseButton == 0 && mouseOver) {

            boolean toggleState = this.hasGuiControlState(GuiControlState.SELECTED);

            if (toggleState) {
                this.removeGuiControlState(GuiControlState.SELECTED);
            } else {
                this.addGuiControlState(GuiControlState.SELECTED);
            }

            PacketGuiObjectUpdate packetGuiObjectUpdate = new PacketGuiObjectUpdate(this.controlID, this.writeNBT(), te.getPos()); //todo: fix this to use nbt
            PacketHandler.NETWORK_INSTANCE.sendToServer(packetGuiObjectUpdate);

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
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

    @Override
    public NBTTagCompound writeNBT() {
        return null;
    }

    @Override
    public void readNBT(NBTTagCompound nbt) {

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

    // ------------


    public UUID getControlID() {
        return controlID;
    }
}
