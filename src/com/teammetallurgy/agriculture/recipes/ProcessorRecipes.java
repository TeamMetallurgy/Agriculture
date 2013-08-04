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

public class ProcessorRecipes
{
	/** The static instance of this class */
	private static final ProcessorRecipes instance = new ProcessorRecipes();

	/** A list of all the recipes added */
	private List recipes = new ArrayList();

	/**
	 * Returns the static instance of this class
	 */
	public static final ProcessorRecipes getInstance()
	{
		return instance;
	}

	public void addRecipe(ItemStack item, ItemStack result)
	{
		addRecipe(item, null, result);
	}

	public void addRecipe(ItemStack par1ItemStack, ItemStack baseItem, ItemStack result)
	{

		this.recipes.add(new ProcessRecipe(par1ItemStack, baseItem, result));
	}

	public ItemStack findMatchingRecipe(ItemStack first, ItemStack second)
	{
		for (int j = 0; j < this.recipes.size(); ++j)
		{
			ProcessRecipe irecipe = (ProcessRecipe) this.recipes.get(j);

			if (irecipe.matches(first, second))
			{
				return irecipe.getCraftingResult();
			}
		}

		return null;
	}

	private ProcessorRecipes()
	{
		addRecipe(new ItemStack(Item.wheat), AgricultureItems.flour.getItemStack());
		addRecipe(new ItemStack(Item.appleRed), AgricultureItems.appleMush.getItemStack());
		addRecipe(new ItemStack(Item.bone), AgricultureItems.gelatin.getItemStack());
		addRecipe(new ItemStack(Item.bread), AgricultureItems.breadCrumbs.getItemStack(8));
		addRecipe(new ItemStack(Item.beefRaw), AgricultureItems.groundBeef.getItemStack(2));
		addRecipe(new ItemStack(Item.porkRaw), AgricultureItems.groundPork.getItemStack(2));
		addRecipe(new ItemStack(Item.chickenRaw), AgricultureItems.groundChicken.getItemStack(2));
		addRecipe(AgricultureItems.roastedPeanuts.getItemStack(), AgricultureItems.crushedPeanuts.getItemStack());
		addRecipe(AgricultureItems.strawberry.getItemStack(), AgricultureItems.strawberryMush.getItemStack());
		addRecipe(AgricultureItems.crushedPeanuts.getItemStack(), AgricultureItems.peanutButter.getItemStack());
		addRecipe(AgricultureItems.milk.getItemStack(), AgricultureItems.butter.getItemStack());
	}

	public static int getProcessTime(ItemStack stackInSlot)
	{
		return stackInSlot != null ? 20 : 0;
	}
}
