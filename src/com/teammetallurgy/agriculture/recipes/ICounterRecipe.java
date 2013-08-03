package com.teammetallurgy.agriculture.recipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ICounterRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     */
    boolean matches(IInventory inventory);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getCraftingResult(IInventory inventory);

    /**
     * Returns the size of the recipe area
     */
    int getRecipeSize();

    ItemStack getRecipeOutput();

	boolean isMat(ItemStack stack);
	
}
