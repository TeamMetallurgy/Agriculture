package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;

public class FrierRecipes
{
	/** The static instance of this class */
	private static final FrierRecipes instance = new FrierRecipes();

	/**
	 * Returns the static instance of this class
	 */
	public static final FrierRecipes getInstance()
	{
		return instance;
	}

	/** A list of all the recipes added */
	private final List recipes = new ArrayList();

	private FrierRecipes()
	{
		addRecipe(AgricultureItems.rawChickenNuggets.getItemStack(), AgricultureItems.chickenNuggets.getItemStack(), 50);
		addRecipe(AgricultureItems.dicedPotatoes.getItemStack(), AgricultureItems.frenchFries.getItemStack(), 50);
		addRecipe(AgricultureItems.breadedChicken.getItemStack(), AgricultureItems.friedChicken.getItemStack(), 50);
	}

	public void addRecipe(ItemStack item, ItemStack result, int cookTime)
	{
		recipes.add(new FrierRecipe(item, result, cookTime));
	}

	public ItemStack findMatchingRecipe(ItemStack stack, int time)
	{
		for (int j = 0; j < recipes.size(); ++j)
		{
			final FrierRecipe irecipe = (FrierRecipe) recipes.get(j);

			if (irecipe.matches(stack, time))
			{
				return irecipe.getCraftingResult();
			}
		}

		return null;
	}
}
