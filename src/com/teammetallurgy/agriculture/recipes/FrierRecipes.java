package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FrierRecipes
{
	/** The static instance of this class */
	private static final FrierRecipes instance = new FrierRecipes();

	/** A list of all the recipes added */
	private List recipes = new ArrayList();

	/**
	 * Returns the static instance of this class
	 */
	public static final FrierRecipes getInstance()
	{
		return instance;
	}

	public void addRecipe(ItemStack item, ItemStack result, int cookTime)
	{
		this.recipes.add(new FrierRecipe(item, result, cookTime));
	}

	public ItemStack findMatchingRecipe(ItemStack stack, int time)
	{
		for (int j = 0; j < this.recipes.size(); ++j)
		{
			FrierRecipe irecipe = (FrierRecipe) this.recipes.get(j);

			if (irecipe.matches(stack, time))
			{
				return irecipe.getCraftingResult();
			}
		}

		return null;
	}

	private FrierRecipes()
	{
		addRecipe(AgricultureItems.rawChickenNuggets.getItemStack(), AgricultureItems.chickenNuggets.getItemStack(), 50);
		addRecipe(AgricultureItems.dicedPotatoes.getItemStack(), AgricultureItems.frenchFries.getItemStack(), 50);
		addRecipe(AgricultureItems.breadedChicken.getItemStack(), AgricultureItems.friedChicken.getItemStack(), 50);
	}
}
