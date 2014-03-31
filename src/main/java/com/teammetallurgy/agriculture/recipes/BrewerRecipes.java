package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.teammetallurgy.agriculture.AgricultureItems;

public class BrewerRecipes {
    /** The static instance of this class */
    private static final BrewerRecipes instance = new BrewerRecipes();

    /**
     * Returns the static instance of this class
     */
    public static final BrewerRecipes getInstance()
    {
        return BrewerRecipes.instance;
    }

    /** A list of all the recipes added */
    private final List<BrewerRecipe> recipes = new ArrayList<BrewerRecipe>();

    private BrewerRecipes()
    {
        this.addRecipe(new ItemStack(Items.water_bucket), new FluidStack(FluidRegistry.WATER, 1000));
        this.addRecipe(new ItemStack(Items.milk_bucket), new FluidStack(FluidRegistry.getFluid("milk"), 1000));

        this.addRecipe(new ItemStack(Items.wheat), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("beer"), 1000));
        this.addRecipe(AgricultureItems.chocolate.getItemStack(), new FluidStack(FluidRegistry.getFluid("milk"), 1000), new FluidStack(FluidRegistry.getFluid("hotcocoa"), 1000));
        this.addRecipe(new ItemStack(Items.reeds), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("vinegar"), 1000));
        this.addRecipe(new ItemStack(Items.potato), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("vodka"), 1000));
        this.addRecipe(new ItemStack(Items.apple), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("cider"), 1000));

        this.addRecipe(AgricultureItems.ceramicCup.getItemStack(), new FluidStack(FluidRegistry.getFluid("beer"), 1000), AgricultureItems.beer.getItemStack(), 40);
        this.addRecipe(AgricultureItems.ceramicCup.getItemStack(), new FluidStack(FluidRegistry.getFluid("vinegar"), 1000), AgricultureItems.vinegar.getItemStack(), 40);
        this.addRecipe(AgricultureItems.ceramicCup.getItemStack(), new FluidStack(FluidRegistry.getFluid("hotcocoa"), 1000), AgricultureItems.hotCocoa.getItemStack(), 40);
    }

    public void addRecipe(final ItemStack item, final FluidStack result)
    {
        this.addRecipe(item, null, result);
    }

    public void addRecipe(final ItemStack item, final FluidStack base, final FluidStack result)
    {
        this.addRecipe(item, base, result, 40);
    }

    public void addRecipe(final ItemStack item, final FluidStack base, final FluidStack result, final int processingTime)
    {
        recipes.add(new BrewerRecipe(item, base, result, 40));
    }

    private void addRecipe(final ItemStack itemStack, final FluidStack base, final ItemStack result, final int processingTime)
    {
        recipes.add(new BrewerRecipe(itemStack, base, result, 40));

    }

    public FluidStack findMatchingFluid(final ItemStack first, final FluidStack base)
    {
        final BrewerRecipe recipe = getMatchingRecipe(first, base);

        if (recipe != null)
        {
            final Object result = recipe.getCraftingResult();

            if (result instanceof FluidStack) { return (FluidStack) result; }

            return null;
        }
        return null;
    }

    public ItemStack findMatchingItem(final ItemStack first, final FluidStack base)
    {
        final BrewerRecipe recipe = getMatchingRecipe(first, base);

        if (recipe != null)
        {
            final Object result = recipe.getCraftingResult();

            if (result instanceof ItemStack) { return (ItemStack) result; }

            return null;
        }
        return null;
    }

    public BrewerRecipe getMatchingRecipe(final ItemStack first, final FluidStack base)
    {
        if (first == null) { return null; }

        for (int j = 0; j < recipes.size(); ++j)
        {
            final BrewerRecipe irecipe = recipes.get(j);

            if (irecipe.matches(first, base)) { return irecipe; }
        }

        return null;
    }

    private BrewerRecipe getMatchingRecipeIgnoreBase(final ItemStack stackInSlot)
    {

        for (int j = 0; j < recipes.size(); ++j)
        {
            final BrewerRecipe irecipe = recipes.get(j);

            if (irecipe.matches(stackInSlot)) { return irecipe; }
        }

        return null;
    }

    public int getProcessTime(final ItemStack stackInSlot)
    {
        if (stackInSlot == null) { return 0; }

        final BrewerRecipe recipe = getMatchingRecipeIgnoreBase(stackInSlot);

        if (recipe != null) { return recipe.getProcessingTime(); }
        return 0;
    }
}
