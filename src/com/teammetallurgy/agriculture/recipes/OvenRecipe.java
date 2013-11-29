package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class OvenRecipe {

    private final int heat;
    private final ItemStack in, out;

    public OvenRecipe(final ItemStack in, final ItemStack out, final int heat)
    {
        this.in = in;
        this.out = out;
        this.heat = heat;
    }

    public ItemStack getInput()
    {
        return this.in.copy();
    }

    public ItemStack getResult()
    {
        return this.out.copy();
    }

    public boolean matches(final ItemStack stack, final int currentHeat)
    {
        if (stack.isItemEqual(this.in) && (currentHeat >= this.heat)) { 
            return true; 
        }

        return false;
    }

}
