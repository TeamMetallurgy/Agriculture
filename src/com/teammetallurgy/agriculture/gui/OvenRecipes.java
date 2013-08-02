package com.teammetallurgy.agriculture.gui;

import java.util.HashMap;
import java.util.Map;

import com.teammetallurgy.agriculture.AgricultureItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OvenRecipes
{
	private static Map<Integer, OvenRecipes> recipes;
	
	private int requiredHeat;
	private ItemStack source;
	private ItemStack result;
	
	static
	{
		addRecipe(new ItemStack(Item.sugar), new ItemStack(AgricultureItems.carmel), 100000);
	}
	
	private OvenRecipes(ItemStack source, ItemStack result, int heat)
	{
		this.source = source;
		this.result = result;
		this.requiredHeat = heat;
	}
	
	public static void addRecipe(ItemStack source, ItemStack result, int heat)
	{
		OvenRecipes recipe = new OvenRecipes(source, result, heat);
		
		int hash = (source.itemID << 8) + source.getItemDamage();
		
		if(recipes == null)
		{
			recipes = new HashMap<Integer, OvenRecipes>();
		}
		
		recipes.put(hash, recipe);
	}
	
	public static ItemStack getResult(ItemStack input, int heat)
	{
		int hash = (input.itemID << 8) + input.getItemDamage();
		OvenRecipes result = recipes.get(hash);
		
		if(result != null)
		{
			if(result.getRequiredHeat() <= heat)
			{
				return result.getResult();
			}
		}
		
		return null;
	}

	private ItemStack getResult()
	{
		return result;
	}

	private int getRequiredHeat()
	{
		return requiredHeat;
	}
}
