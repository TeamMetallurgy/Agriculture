//package com.teammetallurgy.agriculture.modstuff;
//
//import java.util.HashMap;
//
//import powercrystals.minefactoryreloaded.api.FactoryRegistry;
//
//import com.teammetallurgy.agriculture.AgricultureBlocks;
//import com.teammetallurgy.agriculture.AgricultureItems;
//
//public class MineFactoryReloaded {
//
//    static void initMFR()
//    {
//
//        final HashMap<Integer, Integer> plants = new HashMap<Integer, Integer>();
//
//        plants.put(AgricultureItems.strawberry.getDamage(), AgricultureBlocks.strawberry.blockID);
//        plants.put(AgricultureItems.peanuts.getDamage(), AgricultureBlocks.peanut.blockID);
//
//        MineFactoryReloaded.registerPlants(AgricultureItems.strawberry.itemID, plants);
//
//    }
//
//    private static void registerPlants(final int id, final HashMap<Integer, Integer> plants)
//    {
//        final MFRPlants plant = new MFRPlants(id, plants);
//
//        FactoryRegistry.registerPlantable(plant);
//
//        for (final Object value : plants.values())
//        {
//            final int plantId = (Integer) value;
//            final MFRHarvest harvest = new MFRHarvest(plantId);
//
//            FactoryRegistry.registerFertilizable(harvest);
//            FactoryRegistry.registerHarvestable(harvest);
//        }
//
//    }
//}
