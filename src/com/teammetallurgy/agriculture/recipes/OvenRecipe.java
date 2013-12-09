package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class OvenRecipe extends TempRecipe {

    public OvenRecipe(final ItemStack in, final ItemStack out, final int temp)
    {
        super(in, out, temp);
    }

    @Override
    public boolean matches(final ItemStack stack, final int currentTemp)
    {

        if (currentTemp >= temp)
        {
            if (stack.isItemEqual(in)) { return true; }

            if (RecipeUtils.matchesOreDict(stack, in)) { return true; }
        }

        return false;
    }

}
