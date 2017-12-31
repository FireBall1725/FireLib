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
    protected int left = 0;
    protected int top = 0;
    protected int width;
    protected int height;
    private ArrayList<GuiControlState> controlStates = new ArrayList<>();

    public GuiBaseControl(String controlName) {
        super(controlName);
    }

    /**
     * Add a state to the gui control
     *
     * @param guiControlState State to add
     */
    public void addGuiControlState(GuiControlState guiControlState) {
        if (!this.controlStates.contains(guiControlState)) {
            this.controlStates.add(guiControlState);
        }
    }

    /**
     * Remove a state from the gui control
     *
     * @param guiControlState State to remove
     */
    public void removeGuiControlState(GuiControlState guiControlState) {
        if (this.controlStates.contains(guiControlState)) {
            this.controlStates.remove(guiControlState);
        }
    }

    /**
     * Check if a gui control has a specific state
     *
     * @param guiControlState State to check
     * @return boolean of if control state is set
     */
    public boolean hasGuiControlState(GuiControlState guiControlState) {
        return this.controlStates.contains(guiControlState);
    }

    public void setControlPosition(int x, int y) {
        this.left = x;
        this.top = y;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        if (this.hasGuiControlState(GuiControlState.INVISIBLE)) {
            return;
        }

        // todo: check if control supports hover state...

        Rectangle rectangle = new Rectangle(guiContainer.getGuiLeft() + this.left, guiContainer.getGuiTop() + this.top, this.width, this.height);
        boolean mouseOver = rectangle.contains(mouseX, mouseY);

        if (mouseOver) {
            this.addGuiControlState(GuiControlState.HOVERED);
        } else {
            this.removeGuiControlState(GuiControlState.HOVERED);
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.hasGuiControlState(GuiControlState.INVISIBLE)) {
            return;
        }

        TileEntity te = ((GuiMakerGuiContainer) guiContainer).getTileEntity();

        Rectangle rectangle = new Rectangle(guiContainer.getGuiLeft() + this.left, guiContainer.getGuiTop() + this.top, this.width, this.height);
        boolean mouseOver = rectangle.contains(mouseX, mouseY);

        // todo: check if control supports toggle
        if (mouseOver && mouseButton == 0) {
            //if (this.supportsToggle && mouseButton == 0 && mouseOver) {

            boolean toggleState = this.hasGuiControlState(GuiControlState.SELECTED);

            if (toggleState) {
                this.removeGuiControlState(GuiControlState.SELECTED);
            } else {
                this.addGuiControlState(GuiControlState.SELECTED);
            }

            PacketGuiObjectUpdate packetGuiObjectUpdate = new PacketGuiObjectUpdate(this.controlName, this.writeNBT(), te.getPos()); //todo: fix this to use nbt
            PacketHandler.NETWORK_INSTANCE.sendToServer(packetGuiObjectUpdate);

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
}
