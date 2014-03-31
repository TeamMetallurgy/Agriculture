package com.teammetallurgy.agriculture.machines;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.recipes.CounterRecipes;

public class InventoryCounter extends InventoryBasic {

    public InventoryCounter(final String par1Str, final boolean par2, final int par3)
    {
        super(par1Str, par2, par3);
    }

    private void clearInventory(final ItemStack findMatchingRecipe)
    {
        for (int i = 4; i < 20; i++)
        {
            final ItemStack stack = getStackInSlot(i);

            if (stack != null)
            {
                final boolean mat = CounterRecipes.getInstance().isMat(findMatchingRecipe, stack, this);
                if (mat)
                {
                    setInventorySlotContents(i, null);
                }
            }
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public void markDirty()
    {
        for (int i = 0; i < 4; i++)
        {
            final ItemStack stack = getStackInSlot(i);
            if (stack != null)
            {

                final ItemStack findMatchingRecipe = CounterRecipes.getInstance().findMatchingRecipe(this);

                if (findMatchingRecipe != null && !ItemStack.areItemStacksEqual(stack, findMatchingRecipe))
                {
                    clearInventory(findMatchingRecipe);
                    setInventorySlotContents(i, findMatchingRecipe);
                }
            }
        }
    }
}
