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
            if(time >= this.cookTime)
            {
           
                if (stack.isItemEqual(this.base)) { 
                    return true; 
                }
                
                if(RecipeUtils.matchesOreDict(stack, base))
                {
                    return true;
                }
            }
        }

        return false;
    }
}
