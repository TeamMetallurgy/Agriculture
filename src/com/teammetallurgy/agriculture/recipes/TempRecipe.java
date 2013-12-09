package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;

public abstract class TempRecipe {

    protected ItemStack in;
    protected ItemStack out;
    protected int temp;

    public TempRecipe(ItemStack in, ItemStack out, int temp)
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

    public abstract boolean matches(ItemStack stack, int currentTemp);

}