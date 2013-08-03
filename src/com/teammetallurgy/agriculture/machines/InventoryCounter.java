package com.teammetallurgy.agriculture.machines;

import com.teammetallurgy.agriculture.recipes.CounterRecipes;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class InventoryCounter extends InventoryBasic
{

	public InventoryCounter(String par1Str, boolean par2, int par3)
	{
		super(par1Str, par2, par3);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void onInventoryChanged()
	{
		for (int i = 0; i < 4; i++)
		{
			ItemStack stack = getStackInSlot(i);
			if (stack != null)
			{

				ItemStack findMatchingRecipe = CounterRecipes.getInstance().findMatchingRecipe(this);

				if (findMatchingRecipe != null && !ItemStack.areItemStacksEqual(stack, findMatchingRecipe))
				{
					this.clearInventory(findMatchingRecipe);
					setInventorySlotContents(i, findMatchingRecipe);
				}
			}
		}
	}

	private void clearInventory(ItemStack findMatchingRecipe)
	{
		for (int i = 4; i < 20; i++)
		{
			ItemStack stack = getStackInSlot(i);

			if (stack != null)
			{
				boolean mat = CounterRecipes.getInstance().isMat(findMatchingRecipe, stack, this);
				if (mat)
				{
					setInventorySlotContents(i, null);
				}
			}
		}
	}
}
