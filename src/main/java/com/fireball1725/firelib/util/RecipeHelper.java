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

import com.fireball1725.firelib.FireLib;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class RecipeHelper {
    public static void addRecipe(String name, IRecipe recipe) {
        Item item = recipe.getRecipeOutput().getItem();
        //todo: check to see if output is disabled.

        if (recipe.getRegistryName() == null) {
            recipe.setRegistryName(new ResourceLocation(FireLib.instance.getModId(), name));
        }

        //todo: add recipe to list to register, or register
    }

//    public static void addOldShaped(ItemStack itemOutput, Object... itemInput){
//        ShapedPrimer primer = CraftingHelper.parseShaped(itemInput);
//
//        addRecipe(j++, new ShapedRecipes(new ResourceLocation(MODID, "recipes"+j).toString(), primer.width,
//                primer.height, primer.input, itemOutput));
//    }
}
