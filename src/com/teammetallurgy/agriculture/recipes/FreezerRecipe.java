package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class FreezerRecipe extends TempRecipe {
    
    public FreezerRecipe(ItemStack in, ItemStack out, int temp)
    {
        super(in, out, temp);
    }

    @Override
    public boolean matches(ItemStack stack, int currentTemp)
    {
        if(currentTemp >= this.temp)
        {
            if (stack.isItemEqual(this.in)) { 
                return true; 
            }
            
            if(RecipeUtils.matchesOreDict(stack, in)) {
                return true;
            }
        }
        return false;
    }

}
