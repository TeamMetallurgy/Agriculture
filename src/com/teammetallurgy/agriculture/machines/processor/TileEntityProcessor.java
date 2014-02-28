package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;
import com.teammetallurgy.metallurgycore.machines.TileEntityMetallurgySided;

public class TileEntityProcessor extends TileEntityMetallurgySided
{
    public TileEntityProcessor()
    {
        super(4, new int[] { 0, 1, 2 }, new int[] { 1, 2 }, new int[] { 3 });
    }

    @Override
    protected boolean hasInput()
    {
        for (int slot : getInputSlots())
        {
            if (getStackInSlot(slot) != null) { return true; }
        }
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public String getInvName()
    {
        return "container.processor";
    }

    @Override
    protected ItemStack getSmeltingResult(ItemStack... itemStack)
    {
        return ProcessorRecipes.getInstance().findMatchingRecipe(itemStack[0], itemStack[1]);
    }

    @Override
    public int getFuelSlot()
    {
        return 0;
    }

    @Override
    protected int[] getInputSlots()
    {
        return new int[] { 1, 2 };
    }

    @Override
    protected int[] getOutputSlots()
    {
        return new int[] { 3 };
    }

}