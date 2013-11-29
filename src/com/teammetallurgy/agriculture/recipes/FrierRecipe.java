package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class FrierRecipe {

    ItemStack base;
    int       cookTime;
    ItemStack result;

    public FrierRecipe(final ItemStack item, final ItemStack result, final int cookTime)
    {
        this.base = item;
        this.result = result;
        this.cookTime = cookTime;
    }

    public ItemStack getCraftingResult()
    {
        return this.result.copy();
    }

    public ItemStack getInput()
    {
        return this.base.copy();
    }

    public ItemStack getResult()
    {
        return this.result.copy();
    }

    public boolean matches(final ItemStack stack, final int time)
    {
        if (stack != null)
        {
            if (stack.isItemEqual(this.base) && time >= this.cookTime) { return true; }
        }

        return false;
    }
}
