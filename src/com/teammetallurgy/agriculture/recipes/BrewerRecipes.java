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
import net.minecraftforge.fluids.FluidRegistry.FluidRegisterEvent;

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

	private void addRecipe(ItemStack itemStack, FluidStack base, ItemStack result, int processingTime)
	{
		this.recipes.add(new BrewerRecipe(itemStack, base, result, 40));

	}

	public FluidStack findMatchingFluid(ItemStack first, FluidStack base)
	{
		BrewerRecipe recipe = getMatchingRecipe(first, base);

		if (recipe != null)
		{
			Object result = recipe.getCraftingResult();

			if (result instanceof FluidStack)
				return (FluidStack) result;

			return null;
		}
		return null;
	}

	public ItemStack findMatchingItem(ItemStack first, FluidStack base)
	{
		BrewerRecipe recipe = getMatchingRecipe(first, base);

		if (recipe != null)
		{
			Object result = recipe.getCraftingResult();

			if (result instanceof ItemStack)
				return (ItemStack) result;

			return null;
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
		addRecipe(new ItemStack(Item.bucketMilk), new FluidStack(FluidRegistry.getFluid("milk"), 1000));
		
		addRecipe(new ItemStack(Item.wheat), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("beer"), 1000));
		addRecipe(AgricultureItems.chocolate.getItemStack(), new FluidStack(FluidRegistry.getFluid("milk"), 1000), new FluidStack(FluidRegistry.getFluid("hotcocoa"), 1000));
		addRecipe(new ItemStack(Item.reed), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("vinegar"), 1000));
		addRecipe(new ItemStack(Item.potato), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("vodka"), 1000));
		addRecipe(new ItemStack(Item.appleRed), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("cider"), 1000));
				
		addRecipe(AgricultureItems.ceramicCup.getItemStack(), new FluidStack(FluidRegistry.getFluid("beer"), 1000), AgricultureItems.beer.getItemStack(), 40);
		addRecipe(AgricultureItems.ceramicCup.getItemStack(), new FluidStack(FluidRegistry.getFluid("vinegar"), 1000), AgricultureItems.vinegar.getItemStack(), 40);
		addRecipe(AgricultureItems.ceramicCup.getItemStack(), new FluidStack(FluidRegistry.getFluid("hotcocoa"), 1000), AgricultureItems.hotCocoa.getItemStack(), 40);
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
