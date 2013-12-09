package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class FrierRecipe {

    ItemStack base;
    int cookTime;
    ItemStack result;

    public FrierRecipe(final ItemStack item, final ItemStack result, final int cookTime)
    {
        base = item;
        this.result = result;
        this.cookTime = cookTime;
    }

    public ItemStack getCraftingResult()
    {
        return result.copy();
    }

    public ItemStack getInput()
    {
        return base.copy();
    }

    public ItemStack getResult()
    {
        return result.copy();
    }

    public boolean matches(final ItemStack stack, final int time)
    {
        if (stack != null)
        {
            if (time >= cookTime)
            {

                if (stack.isItemEqual(base)) { return true; }

                if (RecipeUtils.matchesOreDict(stack, base)) { return true; }
            }
        }

        return false;
    }
}
