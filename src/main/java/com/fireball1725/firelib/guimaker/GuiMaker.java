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
import com.fireball1725.firelib.guimaker.base.GuiBaseContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class GuiMaker {
    private static HashMap<Integer, GuiMaker> guiInstances = new HashMap<>();

    private final int guiID;

    private GuiBaseContainer defaultGuiContainer = null;
    private GuiBaseContainer openContainer = null;

    public GuiMaker() {
        this.guiID = guiInstances.size();
        guiInstances.put(this.guiID, this);
    }

    public GuiMaker(GuiBaseContainer defaultGuiContainer) {
        this.guiID = guiInstances.size();
        guiInstances.put(this.guiID, this);

        this.defaultGuiContainer = defaultGuiContainer;
        this.openContainer = defaultGuiContainer;
    }

    public static GuiMaker getGuiMaker(int guiID) {
        if (guiInstances.containsKey(guiID)) {
            return guiInstances.get(guiID);
        }

        return null;
    }

    public int getGuiID() {
        return this.guiID;
    }

    public void show(World world, EntityPlayer player, BlockPos pos) {
        show(world, player, pos, this.defaultGuiContainer);
    }

    public void show(World world, EntityPlayer player, BlockPos pos, GuiBaseContainer guiContainer) {
        this.openContainer = guiContainer;
        player.openGui(FireLib.instance, this.guiID, world, pos.getX(), pos.getY(), pos.getZ());
    }

    public void setDefaultGuiContainer(GuiBaseContainer defaultGuiContainer) {
        this.defaultGuiContainer = defaultGuiContainer;
    }

    public GuiBaseContainer getGuiContainer() {
        return this.openContainer;
    }
}
