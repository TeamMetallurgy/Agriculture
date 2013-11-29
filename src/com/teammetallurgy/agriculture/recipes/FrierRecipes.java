package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;

public class FrierRecipes {
    /** The static instance of this class */
    private static final FrierRecipes instance = new FrierRecipes();

    /**
     * Returns the static instance of this class
     */
    public static final FrierRecipes getInstance()
    {
        return FrierRecipes.instance;
    }

    /** A list of all the recipes added */
    private final List<FrierRecipe> recipes = new ArrayList<FrierRecipe>();

    private FrierRecipes()
    {
        this.addRecipe(AgricultureItems.rawChickenNuggets.getItemStack(), AgricultureItems.chickenNuggets.getItemStack(), 50);
        this.addRecipe(AgricultureItems.dicedPotatoes.getItemStack(), AgricultureItems.frenchFries.getItemStack(), 50);
        this.addRecipe(AgricultureItems.breadedChicken.getItemStack(), AgricultureItems.friedChicken.getItemStack(), 50);
    }

    public void addRecipe(final ItemStack item, final ItemStack result, final int cookTime)
    {
        this.recipes.add(new FrierRecipe(item, result, cookTime));
    }

    public ItemStack findMatchingRecipe(final ItemStack stack, final int time)
    {
        for (int j = 0; j < this.recipes.size(); ++j)
        {
            final FrierRecipe irecipe = this.recipes.get(j);

            if (irecipe.matches(stack, time)) { return irecipe.getCraftingResult(); }
        }

        return null;
    }

    public ArrayList<FrierRecipe> getRecipesFor(final ItemStack ingredient)
    {
        final ArrayList<FrierRecipe> retRecipes = new ArrayList<FrierRecipe>();

        for (final FrierRecipe recipe : this.recipes)
        {
            if (recipe.getResult().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }

    public ArrayList<FrierRecipe> getUsageFor(final ItemStack ingredient)
    {
        final ArrayList<FrierRecipe> retRecipes = new ArrayList<FrierRecipe>();

        for (final FrierRecipe recipe : this.recipes)
        {
            if (recipe.getInput().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }
}
