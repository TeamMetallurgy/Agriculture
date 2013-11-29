package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public class FreezerRecipe {
    
    private ItemStack in, out;
    private int temp;

    public FreezerRecipe(ItemStack in, ItemStack out, int temp)
    {
        this.in = in;
        this.out = out;
        this.temp = temp;
    }

    public ItemStack getInput()
    {
        return in.copy();
    }

    public ItemStack getResult()
    {
        return out.copy();
    }

    public boolean matches(ItemStack stack, int currentTemp)
    {
        if (stack.isItemEqual(this.in) && currentTemp >= temp) { return true; }
        return false;
    }

}
