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
import com.fireball1725.firelib.blocks.IFireBlocks;
import com.fireball1725.firelib.items.IFireItems;
import com.fireball1725.firelib.items.ItemBase;
import com.fireball1725.firelib.items.ItemBaseTool;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Locale;

public class RegistrationHelper {
    private FireMod fireMod;

    public RegistrationHelper(FireMod fireMod) {
        this.fireMod = fireMod;
    }

    public static <E extends Enum<E>> void registerRecipes(Class<E> enumData, RegistryEvent.Register<IRecipe> event) {
        if (enumData == null) {
            return;
        }

        for (Enum<E> eEnumObject : enumData.getEnumConstants()) {
            if (eEnumObject instanceof IProvideRecipe)
                ((IProvideRecipe) eEnumObject).registerRecipes(event);
        }
    }

    @SubscribeEvent
    public final void registerBlocks(RegistryEvent.Register<Block> event) {
        registerEnum(fireMod.getBlockEnum(), event.getRegistry());

    }

    @SubscribeEvent
    public final void registerItems(RegistryEvent.Register<Item> event) {
        registerEnum(fireMod.getBlockEnum(), event.getRegistry());
        registerEnum(fireMod.getItemEnum(), event.getRegistry());
    }

    @SubscribeEvent
    public final void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        registerRecipes(fireMod.getBlockEnum(), event);
        registerRecipes(fireMod.getItemEnum(), event);
    }

    /**
     * Register enum blocks, itemblocks, and items
     *
     * @param enumData enum class
     * @param event    RegistryEvent event
     */
    public <E extends Enum<E>> void registerEnum(Class<E> enumData, IForgeRegistry event) {
        if (enumData == null) {
            return;
        }

        for (Enum<E> enumObject : enumData.getEnumConstants()) {
            if (event.getRegistrySuperType() == Block.class && enumObject instanceof IFireBlocks) {
                Block block = registerBlock(event, ((IFireBlocks) enumObject).getBlockClass());
                ((IFireBlocks) enumObject).setBlock(block);
            }

            if (event.getRegistrySuperType() == Item.class && enumObject instanceof IFireBlocks) {
                registerItemBlock(event, ((IFireBlocks) enumObject).getBlock(), ((IFireBlocks) enumObject).getItemBlockClass());
            }

            if (event.getRegistrySuperType() == Item.class && enumObject instanceof IFireItems) {
                Item item = registerItem(event, ((IFireItems) enumObject).getItemClass());
                ((IFireItems) enumObject).setItem(item);
            }
        }
    }

    private Block registerBlock(IForgeRegistry event, Class<? extends BlockBase> blockClass) {
        Block block = null;
        String internalName;

        try {
            block = blockClass.getConstructor().newInstance();

            internalName = ((BlockBase) block).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Block is %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized names cannot be blank! Block is %s", blockClass.getCanonicalName()));

            block.setRegistryName(internalName);
            block.setUnlocalizedName(internalName);

            event.register(block);

            if (block instanceof IBlockRenderer && fireMod.proxy().getEffectiveSide() == Side.CLIENT)
                ((IBlockRenderer) block).registerBlockRenderer();

            fireMod.getLogger().info(String.format("Registered block (%s)", blockClass.getCanonicalName()));
        } catch (Exception ex) {
            fireMod.getLogger().fatal(String.format("Fatal error while registering block (%s)", blockClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return block;
    }

    private void registerItemBlock(IForgeRegistry event, Block block, Class<? extends ItemBlock> itemBlockClass) {
        ItemBlock itemBlock;

        try {
            itemBlock = itemBlockClass.getConstructor(Block.class).newInstance(block);
            itemBlock.setRegistryName(block.getRegistryName());

            event.register(itemBlock);

            if (block instanceof IBlockRenderer && fireMod.proxy().getEffectiveSide() == Side.CLIENT) {
                ((IBlockRenderer) block).registerBlockItemRenderer();
            }

            fireMod.getLogger().info(String.format("Registered block (%s)", itemBlockClass.getCanonicalName()));
        } catch (Exception ex) {
            fireMod.getLogger().fatal(String.format("Fatal error while registering block (%s)", itemBlockClass.getCanonicalName()));
            ex.printStackTrace();
        }
    }

    private Item registerItem(IForgeRegistry event, Class<? extends Item> itemClass) {
        Item item = null;
        String internalName = "";

        try {
            item = itemClass.getConstructor().newInstance();

            if (item instanceof ItemBase)
                internalName = ((ItemBase) item).getInternalName();

            if (item instanceof ItemBaseTool)
                internalName = ((ItemBaseTool) item).getInternalName();

            if (!internalName.equals(internalName.toLowerCase(Locale.US)))
                throw new IllegalArgumentException(String.format("Unlocalized names need to be all lowercase! Item: %s", internalName));

            if (internalName.isEmpty())
                throw new IllegalArgumentException(String.format("Unlocalized name cannot be blank! Item: %s", itemClass.getCanonicalName()));

            item.setRegistryName(fireMod.getModId(), internalName);
            item.setUnlocalizedName(internalName);

            event.register(item);

            if (item instanceof IItemRenderer && fireMod.proxy().getEffectiveSide() == Side.CLIENT)
                ((IItemRenderer) item).registerItemRenderer();

            fireMod.getLogger().info(String.format("Registered item (%s)", itemClass.getCanonicalName()));
        } catch (Exception ex) {
            fireMod.getLogger().fatal(String.format("Fatal error while registering item (%s)", itemClass.getCanonicalName()));
            ex.printStackTrace();
        }

        return item;
    }
}
