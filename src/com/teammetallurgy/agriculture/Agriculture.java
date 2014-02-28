package com.teammetallurgy.agriculture;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.modstuff.ModIntegration;
import com.teammetallurgy.agriculture.worldgen.WorldGenSalt;
import com.teammetallurgy.agriculture.worldgen.WorldGenSpice;
import com.teammetallurgy.metallurgycore.handlers.ConfigHandler;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(name = Agriculture.MODNAME, modid = Agriculture.MODID, version = Agriculture.VERSION)
@NetworkMod(channels = { Agriculture.MODID }, packetHandler = PacketHandler.class)
public class Agriculture {
    public static final String MODID = "Agriculture";
    public static final String MODNAME = "Agriculture";
    public static final String VERSION = "1.0.0";

    public static boolean debug;

    @Instance(Agriculture.MODID)
    public static Agriculture instance;

    @SidedProxy(serverSide = "com.teammetallurgy.agriculture.CommonProxy", clientSide = "com.teammetallurgy.agriculture.ClientProxy")
    public static CommonProxy proxy;

    public static AgricultureTab tab;

    private Logger logger;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event)
    {
        ConfigHandler.setFile(event.getSuggestedConfigurationFile());

        logger = event.getModLog();
    }

    @EventHandler
    public void init(final FMLInitializationEvent event)
    {
        Agriculture.tab = new AgricultureTab("Agriculture");
        LanguageRegistry.instance().addStringLocalization("itemGroup.Agriculture", "en_US", "Agriculture");
        AgricultureItems.init();
        AgricultureBlocks.init();
//        Agriculture.tab.setItemID(AgricultureBlocks.oven.blockID);

        AgricultureItems.setupItems();

        GameRegistry.registerWorldGenerator(new WorldGenSalt(4));
        GameRegistry.registerWorldGenerator(new WorldGenSpice(AgricultureBlocks.cinnamon.blockID));
        GameRegistry.registerWorldGenerator(new WorldGenSpice(AgricultureBlocks.vanilla.blockID));

        TickRegistry.registerTickHandler(new TickHandler(), Side.SERVER);
        NetworkRegistry.instance().registerGuiHandler(Agriculture.instance, Agriculture.proxy);
        Agriculture.proxy.registerEventHandlers();
        Agriculture.proxy.registerRenderers();

        registerTCCrop(AgricultureBlocks.peanut, 6);
        registerTCCrop(AgricultureBlocks.strawberry, 6);

        registerForestryCrop(AgricultureItems.strawberry.itemID, AgricultureItems.strawberry.getDamage(), AgricultureBlocks.strawberry.blockID, 6);
        registerForestryCrop(AgricultureItems.peanuts.itemID, AgricultureItems.peanuts.getDamage(), AgricultureBlocks.peanut.blockID, 6);
    }

    private void registerTCCrop(final Block crop, final int matureMetadata)
    {
        FMLInterModComms.sendMessage("Thaumcraft", "harvestStandardCrop", new ItemStack(crop, 1, matureMetadata));
    }

    private void registerForestryCrop(final int seedID, final int seedMetadata, final int cropBlockID, final int cropMatureMetedata)
    {
        FMLInterModComms.sendMessage("Forestry", "add-farmable-crop", "farmVegetables@" + seedID + "." + seedMetadata + "." + cropBlockID + "." + cropMatureMetedata);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event)
    {
        if (Loader.isModLoaded("IguanaTweaksTConstruct"))
        {
            logger.fine("Found Iquana Tweaks. Tweaking Recipes");
            AgricultureItems.tweakRecipeIguana();
        }

        if (Loader.isModLoaded("MineFactoryReloaded"))
        {
            ModIntegration.init("MFR");
        }
    }

    public Logger getLogger()
    {
        return logger;
    }

}
