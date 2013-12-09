package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeUtils {
    
    public static boolean matchesOreDict(ItemStack input, ItemStack... stacks)
    {
        if(input == null)
        {
            return false;
        }
        
        int oreID = OreDictionary.getOreID(input);
        
        if(oreID == -1)
        {
            return false;
        }
        
        ArrayList<ItemStack> ores = OreDictionary.getOres(oreID);
        
        for(ItemStack ore : ores)
        {
            for(ItemStack stack : stacks)
            {
                if(stack != null && OreDictionary.itemMatches(stack, ore, true))
                {
                    return true;
                }
            }

        }       
        
        return false;
    }
}
