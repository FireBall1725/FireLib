/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.util;

import com.fireball1725.firelib.FireMod;
import com.fireball1725.firelib.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Locale;

public class RegistrationHelper {
    public static Block registerBlock(IForgeRegistry event, Class<? extends BlockBase> blockClass) {
        Block block = null;
        String internalName;

        try {
            block = blockClass.getConstructor().newInstance();

            internalName = "hello_world"; //((BlockBase)block).getInternalName;

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block is %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized names cannot be blank! Block is %s", blockClass.getCanonicalName()));

            block.setRegistryName(internalName);
            block.setUnlocalizedName(internalName);

            event.register(block);

            // todo: if (block instanceof IBlockRenderer) {

            FireMod.instance.getLogger().info(String.format("Registered block (%s)", blockClass.getCanonicalName()));
        } catch (Exception ex) {
            FireMod.instance.getLogger().fatal(String.format("Fatal error while registering block (%s)", blockClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return block;
    }

    public static void registerItemBlock(IForgeRegistry event, Block block, Class<? extends ItemBlock> itemBlockClass) {
        ItemBlock itemBlock;

        try {
            itemBlock = itemBlockClass.getConstructor(Block.class).newInstance(block);
            itemBlock.setRegistryName(block.getRegistryName());

            event.register(itemBlock);

            FireMod.instance.getLogger().info(String.format("Registered block (%s)", itemBlockClass.getCanonicalName()));
        } catch (Exception ex) {
            FireMod.instance.getLogger().fatal(String.format("Fatal error while registering block (%s)", itemBlockClass.getCanonicalName()));
            ex.printStackTrace();
        }
    }
}
