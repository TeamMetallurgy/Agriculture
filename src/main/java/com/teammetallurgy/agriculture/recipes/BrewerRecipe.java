package com.teammetallurgy.agriculture.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class BrewerRecipe {

    private final FluidStack base;
    private final ItemStack item;
    private final int processingTime;
    private final Object result;

    public BrewerRecipe(final ItemStack item, final FluidStack base, final Object result, final int processingTime)
    {
        this.item = item;
        this.base = base;
        this.result = result;
        this.processingTime = processingTime;

    }

    public Object getCraftingResult()
    {
        return result;
    }

    public int getProcessingTime()
    {
        return processingTime;
    }

    public boolean matches(final ItemStack stackInSlot)
    {
        return stackInSlot.isItemEqual(item);
    }

    public boolean matches(final ItemStack first, final FluidStack baseFluid)
    {
        if (first.isItemEqual(item))
        {
            if (base == null)
            {
                if (baseFluid == null) { return true; }
            }
            else
            {
                if (base.isFluidEqual(baseFluid)) { return true; }
            }

        }

        return false;
    }
}
