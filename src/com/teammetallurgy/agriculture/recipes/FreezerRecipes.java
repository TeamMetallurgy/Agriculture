package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class FreezerRecipes {

    private static final int DEFAULTTEMP = 100000;

    private static FreezerRecipes instance = new FreezerRecipes();

    public static FreezerRecipes getInstance()
    {
        return FreezerRecipes.instance;
    }

    private final ArrayList<FreezerRecipe> recipes = new ArrayList<FreezerRecipe>();

    public FreezerRecipes()
    {
        this.addRecipe(AgricultureItems.vanillaIceCream, AgricultureItems.vanillaIceCreamMix);
        this.addRecipe(AgricultureItems.strawberryIceCream, AgricultureItems.strawberryIceCreamMix);
        this.addRecipe(AgricultureItems.chocolateIceCream, AgricultureItems.chocolateIceCreamMix);
    }

    public void addRecipe(final ItemStack out, final ItemStack in, final int temp)
    {
        recipes.add(new FreezerRecipe(in, out, temp));
    }

    public void addRecipe(final SubItem out, final SubItem in)
    {
        this.addRecipe(out.getItemStack(), in.getItemStack(), FreezerRecipes.DEFAULTTEMP);

    }

    public ItemStack findMatchingRecipe(final ItemStack stack, final int currentTemp)
    {
        final TempRecipe recipe = getMatchingRecipe(stack, currentTemp);

        if (recipe != null) { return recipe.getResult(); }

        return null;
    }

    private TempRecipe getMatchingRecipe(final ItemStack stack, final int currentTemp)
    {
        for (final TempRecipe recipe : recipes)
        {
            if (recipe.matches(stack, currentTemp)) { return recipe; }
        }
        return null;
    }

    public ArrayList<FreezerRecipe> getRecipes()
    {
        return recipes;
    }

    public ArrayList<FreezerRecipe> getRecipesFor(final ItemStack ingredient)
    {
        final ArrayList<FreezerRecipe> retRecipes = new ArrayList<FreezerRecipe>();

        for (final FreezerRecipe recipe : recipes)
        {
            if (recipe.getResult().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }

    public ArrayList<FreezerRecipe> getUsageFor(final ItemStack ingredient)
    {
        final ArrayList<FreezerRecipe> retRecipes = new ArrayList<FreezerRecipe>();

        for (final FreezerRecipe recipe : recipes)
        {
            if (recipe.getInput().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }

}
