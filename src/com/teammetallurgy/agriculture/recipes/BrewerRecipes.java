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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BrewerRecipes
{
	/** The static instance of this class */
	private static final BrewerRecipes instance = new BrewerRecipes();

	/** A list of all the recipes added */
	private List recipes = new ArrayList();

	/**
	 * Returns the static instance of this class
	 */
	public static final BrewerRecipes getInstance()
	{
		return instance;
	}

	public void addRecipe(ItemStack item, FluidStack result)
	{
		addRecipe(item, null, result);
	}

	public void addRecipe(ItemStack item, FluidStack base, FluidStack result)
	{
		addRecipe(item, base, result, 40);
	}
	
	public void addRecipe(ItemStack item, FluidStack base, FluidStack result, int processingTime)
	{
		this.recipes.add(new BrewerRecipe(item, base, result, 40));
	}


	public FluidStack findMatchingFluid(ItemStack first, FluidStack base)
	{
		BrewerRecipe recipe = getMatchingRecipe(first, base);

		if (recipe != null)
		{
			return recipe.getCraftingResult();
		}
		return null;
	}

	public BrewerRecipe getMatchingRecipe(ItemStack first, FluidStack base)
	{
		if (first == null)
		{
			return null;
		}

		for (int j = 0; j < this.recipes.size(); ++j)
		{
			BrewerRecipe irecipe = (BrewerRecipe) this.recipes.get(j);

			if (irecipe.matches(first, base))
			{
				return irecipe;
			}
		}

		return null;
	}

	private BrewerRecipes()
	{
		addRecipe(new ItemStack(Item.bucketWater), new FluidStack(FluidRegistry.WATER, 1000));
		
		FluidRegistry.registerFluid(new Fluid("beer"));
		addRecipe(new ItemStack(Item.wheat), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("beer"), 1000), 40);

		// addRecipe(new ItemStack(Item.bucketMilk), new
		// FluidStack(FluidRegistry.getFluid("Milk"), 1000));
	}

	public int getProcessTime(ItemStack stackInSlot)
	{
		if (stackInSlot == null)
		{
			return 0;
		}

		BrewerRecipe recipe = getMatchingRecipeIgnoreBase(stackInSlot);

		if (recipe != null)
		{
			return recipe.getProcessingTime();
		}
		return 0;
	}

	private BrewerRecipe getMatchingRecipeIgnoreBase(ItemStack stackInSlot)
	{

		for (int j = 0; j < this.recipes.size(); ++j)
		{
			BrewerRecipe irecipe = (BrewerRecipe) this.recipes.get(j);

			if (irecipe.matches(stackInSlot))
			{
				return irecipe;
			}
		}

		return null;
	}
}
