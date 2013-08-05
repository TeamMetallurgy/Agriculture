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

		if (first != null && baseItem != null)
		{
			if (first2 != null && second != null)
			{
				if (first.isItemEqual(first2) && baseItem.isItemEqual(second))
				{
					return true;
				} else if (first.isItemEqual(second) && baseItem.isItemEqual(first2))
				{
					return true;
				}
			}
		} else if (first == null)
		{
			if (first2 == null)
			{
				if (baseItem.isItemEqual(second))
				{
					return true;
				}
			} else if (second == null)
			{
				if (baseItem.isItemEqual(first2))
				{
					return true;
				}
			}
		} else if (baseItem == null)
		{
			if (first2 == null)
			{
				if (first.isItemEqual(second))
				{
					return true;
				}
			} else if (second == null)
			{
				if (first.isItemEqual(first2))
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
