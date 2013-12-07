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
        if(matchesOreDict(first) && matchesOreDict(second))
        {
            return true;
        }
        
        if(matchesOreDict(first))
        {
            return true;
        }
        
        if(matchesOreDict(second))
        {
            return true;
        }
        
        return false;
    }

    private boolean matchesOreDict(ItemStack itemStack)
    {
        if(itemStack == null)
        {
            return false;
        }
        
        int oreID = OreDictionary.getOreID(itemStack);
        
        if(oreID == -1)
        {
            return false;
        }
        
        ArrayList<ItemStack> ores = OreDictionary.getOres(oreID);
        
        for(ItemStack ore : ores)
        {
            if(first != null && OreDictionary.itemMatches(first, ore, true))
            {
                return true;
            }
            
            if(baseItem != null && OreDictionary.itemMatches(baseItem, ore, true))
            {
                return true;
            }
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
