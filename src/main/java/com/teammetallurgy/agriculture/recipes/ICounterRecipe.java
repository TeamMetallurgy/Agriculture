package com.teammetallurgy.agriculture.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ICounterRecipe {
    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(IInventory inventory);

    ItemStack getRecipeOutput();

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    boolean isMat(ItemStack stack);

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(IInventory inventory);

}
