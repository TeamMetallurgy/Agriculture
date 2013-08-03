package com.teammetallurgy.agriculture.recipes;

import java.util.HashMap;
import java.util.Map;

import com.teammetallurgy.agriculture.AgricultureItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProcessorRecipes
{
	private static Map<Integer, ProcessorRecipes> recipes;

	private ItemStack source;
	private ItemStack result;

	static
	{
		addRecipe(new ItemStack(Item.wheat), AgricultureItems.flour.getItemStack());
	}

	private ProcessorRecipes(ItemStack source, ItemStack result)
	{
		this.source = source;
		this.result = result;
	}

	public static void addRecipe(ItemStack source, ItemStack result)
	{
		ProcessorRecipes recipe = new ProcessorRecipes(source, result);

		int hash = (source.itemID << 8) + source.getItemDamage();

		if (recipes == null)
		{
			recipes = new HashMap<Integer, ProcessorRecipes>();
		}

		recipes.put(hash, recipe);
	}

	public static ItemStack getResult(ItemStack input)
	{
		int hash = (input.itemID << 8) + input.getItemDamage();
		ProcessorRecipes result = recipes.get(hash);

		if (result != null)
		{
			return result.getResult();
		}

		return null;
	}

	private ItemStack getResult()
	{
		return result;
	}

	public static int getProcessTime(ItemStack stackInSlot)
	{
		return stackInSlot == null ? 0 : 20;
	}
}
