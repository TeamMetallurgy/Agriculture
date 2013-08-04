package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class ProcessRecipe
{

	private ItemStack first;
	private ItemStack baseItem;
	private ItemStack result;

	public ProcessRecipe(ItemStack first, ItemStack baseItem, ItemStack result)
	{
		this.first = first;
		this.baseItem = baseItem;
		this.result = result;
	}

	public boolean matches(ItemStack first2, ItemStack second)
	{
		if (first2 == null && second == null)
		{
			return false;
		}

		if (second != null)
		{
			if (first.isItemEqual(second))
			{
				if (baseItem == null && first2 == null)
				{
					return true;
				}
				if (baseItem != null)
				{
					if (baseItem.isItemEqual(first2))
					{
						return true;
					}
				}
			}
		} else
		{
			if (first.isItemEqual(first2))
			{
				if (baseItem == null && second == null)
				{
					return true;
				}
			}
		}

		return false;
	}

	public ItemStack getCraftingResult()
	{
		return result;
	}
}
