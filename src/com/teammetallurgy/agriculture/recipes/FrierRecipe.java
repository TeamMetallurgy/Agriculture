package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class FrierRecipe
{

	ItemStack base;
	ItemStack result;
	int cookTime;

	public FrierRecipe(ItemStack item, ItemStack result, int cookTime)
	{
		this.base = item;
		this.result = result;
		this.cookTime = cookTime;
	}

	public boolean matches(ItemStack stack, int time)
	{
		if (stack != null)
		{
			if (stack.isItemEqual(base) && time >= cookTime)
			{
				return true;
			}
		}

		return false;
	}

	public ItemStack getCraftingResult()
	{
		return result.copy();
	}

}
