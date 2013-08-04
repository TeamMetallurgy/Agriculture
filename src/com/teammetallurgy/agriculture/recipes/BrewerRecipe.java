package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewerRecipe
{

	private FluidStack result;
	private ItemStack item;
	private FluidStack base;
	private int processingTime;

	public BrewerRecipe(ItemStack item, FluidStack base, FluidStack result, int processingTime)
	{
		this.item = item;
		this.base = base;
		this.result = result;
		this.processingTime = processingTime;
	}

	public boolean matches(ItemStack first, FluidStack baseFluid)
	{
		if (first.isItemEqual(item))
		{
			if (base == null)
			{
				if (baseFluid == null)
				{
					return true;
				}
			} else
			{
				if (base.isFluidEqual(baseFluid))
				{
					return true;
				}
			}

		}

		return false;
	}

	public FluidStack getCraftingResult()
	{
		return result;
	}
	
	public int getProcessingTime()
	{
		return processingTime;
	}

	public boolean matches(ItemStack stackInSlot)
	{
		return stackInSlot.isItemEqual(item);
	}
}
