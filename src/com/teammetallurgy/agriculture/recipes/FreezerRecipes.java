package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

import net.minecraft.item.ItemStack;

public class FreezerRecipes {
    
    private static final int DEFAULTTEMP = 100000;

    private static FreezerRecipes instance = new FreezerRecipes();
    
    private List<FreezerRecipe> recipes = new ArrayList<FreezerRecipe>();

    public static FreezerRecipes getInstance()
    {
        return instance;
    }
    
    public FreezerRecipes()
    {
        addRecipe(AgricultureItems.vanillaIceCream, AgricultureItems.vanillaIceCreamMix);
        addRecipe(AgricultureItems.strawberryIceCream, AgricultureItems.strawberryIceCreamMix);
        addRecipe(AgricultureItems.chocolateIceCream, AgricultureItems.chocolateIceCreamMix);
    }

    public void addRecipe(SubItem out, SubItem in)
    {
        addRecipe(out.getItemStack(), in.getItemStack(), DEFAULTTEMP);
        
    }

    public void addRecipe(ItemStack out, ItemStack in, int temp)
    {
        recipes.add(new FreezerRecipe(in, out, temp));
    }

    public ArrayList<FreezerRecipe> getUsageFor(ItemStack ingredient)
    {
        ArrayList<FreezerRecipe> retRecipes = new ArrayList<FreezerRecipe>();
        
        for (FreezerRecipe recipe : recipes)
        {
            if(recipe.getInput().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }

    public ArrayList<FreezerRecipe> getRecipesFor(ItemStack ingredient)
    {
        ArrayList<FreezerRecipe> retRecipes = new ArrayList<FreezerRecipe>();
        
        for (FreezerRecipe recipe : recipes)
        {
            if(recipe.getResult().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }

    public ItemStack findMatchingRecipe(ItemStack stack, int currentTemp)
    {
        FreezerRecipe recipe = this.getMatchingRecipe(stack, currentTemp);
        
        if(recipe != null)
        {
            return recipe.getResult();
        }
        
        return null;
    }

    private FreezerRecipe getMatchingRecipe(ItemStack stack, int currentTemp)
    {
        for(FreezerRecipe recipe : recipes)
        {
            if(recipe.matches(stack, currentTemp)) { return recipe; }
        }
        return null;
    }

}
