package com.teammetallurgy.agriculture;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(name = Agriculture.MODNAME, modid = Agriculture.MODID, version = Agriculture.VERSION)
@NetworkMod(channels = { Agriculture.MODID }, packetHandler = PacketHandler.class)
public class Agriculture
{
    public static final String MODID = "Agriculture";
    public static final String MODNAME = "Agriculture";
    public static final String VERSION = "1.0.0";
    
    @Instance(MODID) 
    public static Agriculture instance;
    
    @SidedProxy(serverSide = "com.teammetallurgy.agriculture.CommonProxy", clientSide = "com.teammetallurgy.agriculture.ClientProxy")
    public static CommonProxy proxy;
    
    public static AgricultureTab tab;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	tab = new AgricultureTab("Agriculture");
    	AgricultureBlocks.init();
    	AgricultureItems.init();
    	tab.setItemID(AgricultureBlocks.oven.blockID);
    	
    	
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        proxy.registerEventHandlers();
        proxy.registerRenderers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @EventHandler
    public void commands(FMLServerStartingEvent event)
    {

    }
}
