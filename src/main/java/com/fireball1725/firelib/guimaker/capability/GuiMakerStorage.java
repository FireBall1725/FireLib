/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.guimaker.capability;

import com.fireball1725.firelib.FireLib;
import com.fireball1725.firelib.guimaker.GuiMaker;
import com.fireball1725.firelib.guimaker.base.GuiBaseContainer;
import com.fireball1725.firelib.guimaker.base.GuiObject;
import com.fireball1725.firelib.guimaker.util.IGuiMaker;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class GuiMakerStorage implements Capability.IStorage<IGuiMaker> {
    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IGuiMaker> capability, IGuiMaker instance, EnumFacing side) {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        GuiBaseContainer guiBaseContainer = instance.getGuiWindow();

        return writeNBT(guiBaseContainer.getGuiObjects(), nbtTagCompound);
    }

    private NBTTagCompound writeNBT(ArrayList<GuiObject> guiObjectArrayList, NBTTagCompound nbtTagCompound) {
        for (GuiObject guiObject : guiObjectArrayList) {
            if (guiObject instanceof GuiBaseContainer)
                writeNBT(((GuiBaseContainer)guiObject).getGuiObjects(), nbtTagCompound);

            NBTTagCompound guiObjectTag = guiObject.writeNBT();

            if (guiObjectTag != null)
                nbtTagCompound.setTag(guiObject.getControlName(), guiObjectTag);
        }

        return nbtTagCompound;
    }

    @Override
    public void readNBT(Capability<IGuiMaker> capability, IGuiMaker instance, EnumFacing side, NBTBase nbt) {
        GuiBaseContainer guiBaseContainer = instance.getGuiWindow();
        NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;

        readNBT(guiBaseContainer.getGuiObjects(), nbtTagCompound);
    }

    private void readNBT(ArrayList<GuiObject> guiObjectArrayList, NBTTagCompound nbtTagCompound) {
        for (GuiObject guiObject : guiObjectArrayList) {
            if (guiObject instanceof GuiBaseContainer)
                readNBT(((GuiBaseContainer)guiObject).getGuiObjects(), nbtTagCompound);

            String controlID = guiObject.getControlName();
            if (nbtTagCompound.hasKey(controlID))
                guiObject.readNBT(nbtTagCompound.getCompoundTag(controlID));
        }
    }
}
