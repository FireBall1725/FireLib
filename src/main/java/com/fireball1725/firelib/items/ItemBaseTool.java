/*
 * Copyright 2017 FireBall1725
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.fireball1725.firelib.items;

import com.fireball1725.firelib.FireMod;
import com.fireball1725.firelib.util.IItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class ItemBaseTool extends ItemTool implements IItemRenderer {
	protected final String resourcePath;
	protected String internalName = "";
	private String modId;

	public ItemBaseTool(float attackDamageIn, float attackSpeedIn, Item.ToolMaterial materalIn,
			Set<Block> effectiveBlockIn, String resourcePath) {
		super(attackDamageIn, attackSpeedIn, materalIn, effectiveBlockIn);
		this.resourcePath = resourcePath;
		this.modId = FireMod.instance().getModId();
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	@Override
	public String getUnlocalizedName() {
		String itemName = getUnwrappedUnlocalizedName(super.getUnlocalizedName());

		return String.format("item.%s.%s", modId, itemName);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String itemName = getUnwrappedUnlocalizedName(super.getUnlocalizedName(stack));

		return String.format("item.%s.%s", modId, itemName);
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemRenderer() {
		final String resourcePath = String.format("%s:%s", FireMod.instance().getModId(), this.resourcePath);

		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(resourcePath, "inventory"));
	}
}
