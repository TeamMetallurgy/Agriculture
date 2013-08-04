package com.teammetallurgy.agriculture.gui;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class OvenRecipes
{
	private static Map<Integer, OvenRecipes> recipes;
	
	private int requiredHeat;
	private ItemStack source;
	private ItemStack result;
	
	static
	{
		addRecipe(AgricultureItems.carmel.getItemStack(), new ItemStack(Item.sugar), 100000);
		addRecipe(AgricultureItems.shortcake, AgricultureItems.batter, 100000);
		addRecipe(AgricultureItems.carrotCake, AgricultureItems.carrotCakeBatter, 100000);
		addRecipe(AgricultureItems.cheeseSandwich, AgricultureItems.grilledCheese, 100000);
		addRecipe(AgricultureItems.loafOfBread, AgricultureItems.dough, 100000);
		addRecipe(AgricultureItems.pasta, AgricultureItems.pastaDough, 100000);
		addRecipe(AgricultureItems.toastedPbjSandwichApple, AgricultureItems.pbjSandwichApple, 100000);
		addRecipe(AgricultureItems.toastedPbjSandwichStrawberry, AgricultureItems.pbjSandwichStrawberry, 100000);
		addRecipe(AgricultureItems.roastedPeanuts, AgricultureItems.peanuts, 100000);
		addRecipe(AgricultureItems.applePie, AgricultureItems.rawApplePie, 100000);
		addRecipe(AgricultureItems.frenchToast, AgricultureItems.rawFrenchToast, 100000);
		addRecipe(AgricultureItems.hamburgerPatty, AgricultureItems.rawHamburgerPatty, 100000);
		addRecipe(AgricultureItems.strawberryPie, AgricultureItems.rawStrawberryPie, 100000);
		addRecipe(AgricultureItems.beefJerkey, AgricultureItems.saltedBeef, 100000);
		addRecipe(AgricultureItems.bacon, AgricultureItems.saltedPork, 100000);
		addRecipe(AgricultureItems.toast, AgricultureItems.sliceOfBread, 100000);
	}
	
	private OvenRecipes(ItemStack source, ItemStack result, int heat)
	{
		this.source = source;
		this.result = result;
		this.requiredHeat = heat;
	}
	
	public static void addRecipe(SubItem result, SubItem source, int heat)
	{
		addRecipe(result.getItemStack(), source.getItemStack(), heat);
	}
	
	public static void addRecipe(ItemStack result, ItemStack source, int heat)
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
				ItemStack itemStack = result.getResult();
				itemStack.stackSize = 1;
				return itemStack;
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
