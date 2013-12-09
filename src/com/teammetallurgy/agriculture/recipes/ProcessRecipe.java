package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ProcessRecipe
{

    private final ItemStack first;
    private final ItemStack baseItem;
    private final ItemStack result;

    public ProcessRecipe(ItemStack first, ItemStack baseItem, ItemStack result)
    {
        this.first = first;
        this.baseItem = baseItem;
        this.result = result;
    }

    public ItemStack getCraftingResult()
    {
        return result.copy();
    }

    public boolean matches(ItemStack first, ItemStack second)
    {
        if(uses(first) && uses(second))
        {
            return true;
        }
        
        if(uses(first))
        {
            return true;
        }
        
        if(uses(second))
        {
            return true;
        }

        return matchesOreDict(first, second);
    }
    

    private boolean matchesOreDict(ItemStack first, ItemStack second)
    {
        if(RecipeUtils.matchesOreDict(first) && RecipeUtils.matchesOreDict(second))
        {
            return true;
        }
        
        if(RecipeUtils.matchesOreDict(first))
        {
            return true;
        }
        
        if(RecipeUtils.matchesOreDict(second))
        {
            return true;
        }
        
        return false;
    }

    public boolean uses(ItemStack ingredient)
    {
        if(ingredient == null)
        {
            return false;
        }
        
        if (first != null && first.isItemEqual(ingredient))
        {
            return true;
        }
        else if (baseItem != null && baseItem.isItemEqual(ingredient))
        {
            return true;
        }

        return false;
    }

    public ItemStack[] getIngredients()
    {
        return new ItemStack[]
        { first, baseItem };
    }
}
