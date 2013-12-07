package com.teammetallurgy.agriculture.modstuff;

import java.util.HashMap;

import powercrystals.minefactoryreloaded.api.FactoryRegistry;

import com.teammetallurgy.agriculture.AgricultureBlocks;
import com.teammetallurgy.agriculture.AgricultureItems;

public class MineFactoryReloaded {
    
    
    static void initMFR()
    {
        
        HashMap<Integer, Integer> plants = new HashMap<Integer, Integer>();
        
        
        plants.put(AgricultureItems.strawberry.getDamage(), AgricultureBlocks.strawberry.blockID);
        plants.put(AgricultureItems.peanuts.getDamage(), AgricultureBlocks.peanut.blockID);
        
        registerPlants(AgricultureItems.strawberry.itemID, plants);
              
    }

    private static void registerPlants(int id, HashMap<Integer, Integer> plants)
    {
        MFRPlants plant = new MFRPlants(id, plants);

        FactoryRegistry.registerPlantable(plant);
        
        for(Object value : plants.values())
        {
            int plantId = (Integer) value;
            MFRHarvest harvest = new MFRHarvest(plantId);
            
            FactoryRegistry.registerFertilizable(harvest);
            FactoryRegistry.registerHarvestable(harvest);
        }

    }
}
