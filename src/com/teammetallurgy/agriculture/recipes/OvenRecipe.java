package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OvenRecipe extends TempRecipe 
{

    public OvenRecipe(ItemStack in, ItemStack out, int temp)
    {
        super(in, out, temp);
    }

    @Override
    public boolean matches(ItemStack stack, int currentTemp)
    {
        if (stack.isItemEqual(this.in) && (currentTemp >= this.temp)) { 
            return true; 
        }
        
        int oreID = OreDictionary.getOreID(this.in);
        
        if(oreID != -1)
        {
            ArrayList<ItemStack> ores = OreDictionary.getOres(oreID);
            
            if(ores.contains(stack) && (currentTemp >= this.temp))
            {
                return true;
            }
        }

        return false;
    }

}
