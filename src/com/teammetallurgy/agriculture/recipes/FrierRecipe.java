package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class FrierRecipe
{

	ItemStack base;
	ItemStack result;
	int cookTime;

	public FrierRecipe(ItemStack item, ItemStack result, int cookTime)
	{
		base = item;
		this.result = result;
		this.cookTime = cookTime;
	}

	public ItemStack getCraftingResult()
	{
		return result.copy();
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

}
