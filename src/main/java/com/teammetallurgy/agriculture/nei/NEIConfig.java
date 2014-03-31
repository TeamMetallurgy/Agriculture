//package com.teammetallurgy.agriculture.nei;
//
//import codechicken.nei.api.API;
//import codechicken.nei.api.IConfigureNEI;
//
//public class NEIConfig implements IConfigureNEI {
//
//    @Override
//    public String getName()
//    {
//        return "agriculture_nei_plugin";
//    }
//
//    @Override
//    public String getVersion()
//    {
//        return "v1.0";
//    }
//
//    @Override
//    public void loadConfig()
//    {
//        API.registerUsageHandler(new ProcessorHandler());
//        API.registerRecipeHandler(new ProcessorHandler());
//
//        API.registerUsageHandler(new CounterHandler());
//        API.registerRecipeHandler(new CounterHandler());
//
//        API.registerUsageHandler(new OvenHandler());
//        API.registerRecipeHandler(new OvenHandler());
//
//        API.registerUsageHandler(new FrierHandler());
//        API.registerRecipeHandler(new FrierHandler());
//
//        API.registerUsageHandler(new IceBoxHandler());
//        API.registerRecipeHandler(new IceBoxHandler());
//
//        API.registerUsageHandler(new FrierHandler());
//        API.registerRecipeHandler(new FrierHandler());
//    }
//
//}
