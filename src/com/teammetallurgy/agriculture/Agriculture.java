package com.teammetallurgy.agriculture;

import java.util.logging.Logger;

import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

import com.teammetallurgy.agriculture.modstuff.ModIntegration;
import com.teammetallurgy.agriculture.worldgen.WorldGenSalt;
import com.teammetallurgy.agriculture.worldgen.WorldGenSpice;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(name = Agriculture.MODNAME, modid = Agriculture.MODID, version = Agriculture.VERSION)
@NetworkMod(channels =
{ Agriculture.MODID }, packetHandler = PacketHandler.class)
public class Agriculture
{
    public static final String MODID = "Agriculture";
    public static final String MODNAME = "Agriculture";
    public static final String VERSION = "1.0.0";

    public static boolean debug;
    
    @Instance(MODID)
    public static Agriculture instance;

    @SidedProxy(serverSide = "com.teammetallurgy.agriculture.CommonProxy", clientSide = "com.teammetallurgy.agriculture.ClientProxy")
    public static CommonProxy proxy;

    public static AgricultureTab tab;
	
    private Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.init(event.getSuggestedConfigurationFile());

        logger = event.getModLog();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        tab = new AgricultureTab("Agriculture");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Agriculture", "en_US", "Agriculture");
        AgricultureItems.init();
        AgricultureBlocks.init();
        tab.setItemID(AgricultureBlocks.oven.blockID);

        AgricultureItems.setupItems();

        GameRegistry.registerWorldGenerator(new WorldGenSalt(4));
        GameRegistry.registerWorldGenerator(new WorldGenSpice(AgricultureBlocks.cinnamon.blockID));
        GameRegistry.registerWorldGenerator(new WorldGenSpice(AgricultureBlocks.vanilla.blockID));

        TickRegistry.registerTickHandler(new TickHandler(), Side.SERVER);
        NetworkRegistry.instance().registerGuiHandler(instance, proxy);
        proxy.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(this);
        proxy.registerRenderers();
        
        FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(AgricultureBlocks.peanut, 1, 6));
        FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(AgricultureBlocks.strawberry, 1, 6));
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
       if(Loader.isModLoaded("IguanaTweaksTConstruct"))
       {
           logger.fine("Found Iquana Tweaks. Tweaking Recipes");
           AgricultureItems.tweakRecipeIguana();
       }
       
       if(Loader.isModLoaded("MineFactoryReloaded"))
       {
           ModIntegration.init("MFR");
       }
    }

    public Logger getLogger()
    {
        return logger;
    }
    
    @ForgeSubscribe
    public void oreReg(OreRegisterEvent event)
    {
        System.out.println(event.Name);
    }
}
