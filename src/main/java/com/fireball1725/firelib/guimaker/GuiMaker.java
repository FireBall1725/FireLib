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
import com.fireball1725.firelib.guimaker.objects.GuiObject;
import com.fireball1725.firelib.guimaker.objects.GuiWindow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class GuiMaker {
    private static HashMap<Integer, GuiMaker> guiInstances = new HashMap<>();
    private final int guiID;
    private HashMap<String, ArrayList<GuiObject>> guiObjects = new HashMap<>();
    private String defaultGroup = "default";

    public GuiMaker() {
        this.guiID = guiInstances.size();

        guiInstances.put(this.guiID, this);
    }

    public static GuiMaker getGuiMaker(int guiID) {
        if (guiInstances.containsKey(guiID)) {
            return guiInstances.get(guiID);
        }

        return null;
    }

    public static UUID generateUUIDFromName(String input) {
        return UUID.nameUUIDFromBytes(input.getBytes());
    }

    public void show(World world, EntityPlayer player, BlockPos pos) {
        player.openGui(FireLib.instance, this.guiID, world, pos.getX(), pos.getY(), pos.getZ());
    }

    public void registerGuiObjectGroup(String groupName) {
        if (groupExists(groupName)) {
            return;
        }

        guiObjects.put(groupName, new ArrayList<GuiObject>());
    }

    public void registerGuiObject(GuiObject guiObject) {
        if (!groupExists(defaultGroup)) {
            registerGuiObjectGroup(defaultGroup);
        }

        registerGuiObject(defaultGroup, guiObject);
    }

    public void registerGuiObject(String groupName, GuiObject guiObject) {
        if (!groupExists(groupName)) {
            return;
        }

        ArrayList<GuiObject> guiObjectsArray = guiObjects.get(groupName);
        guiObjectsArray.add(guiObject);
        guiObjects.replace(groupName, guiObjectsArray);
    }

    public ArrayList<GuiObject> getGuiObjects() {
        return getGuiObjects(defaultGroup);
    }

    public ArrayList<GuiObject> getGuiObjects(String groupName) {
        if (!groupExists(groupName)) {
            return null;
        }

        return guiObjects.get(groupName);
    }

    public void setDefaultGroup(String defaultGroup) {
        this.defaultGroup = defaultGroup;
    }

    public GuiWindow getGuiWindow() {
        return getGuiWindow(defaultGroup);
    }

    public GuiWindow getGuiWindow(String groupName) {
        if (!groupExists(groupName)) {
            return null;
        }

        ArrayList<GuiObject> guiObjectArrayList = getGuiObjects(groupName);
        for (GuiObject guiObject : guiObjectArrayList) {
            if (guiObject instanceof GuiWindow) {
                return (GuiWindow) guiObject;
            }
        }

        return null;
    }

    private boolean groupExists(String groupName) {
        return guiObjects.containsKey(groupName);
    }

    public void addGuiControlState(GuiControlState guiControlState, UUID guiControlID) {
        GuiObject guiObject = getGuiObjectFromUUID(guiControlID);

        if (guiObject == null) {
            return;
        }

        guiObject.addGuiControlState(guiControlState);
    }

    public void removeGuiControlState(GuiControlState guiControlState, UUID guiControlID) {
        GuiObject guiObject = getGuiObjectFromUUID(guiControlID);

        if (guiObject == null) {
            return;
        }

        guiObject.removeGuiControlState(guiControlState);
    }

    private GuiObject getGuiObjectFromUUID(UUID guiControlID) {
        for (GuiObject guiObject : this.getGuiObjects()) {
            if (guiObject.getControlID().equals(guiControlID)) {
                return guiObject;
            }
        }

        return null;
    }
}
