package com.teammetallurgy.agriculture;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import com.teammetallurgy.agriculture.machines.counter.BlockCounter;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.oven.BlockOven;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.BlockProcessor;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class AgricultureBlocks
{
	public static Block oven;
	public static Block counter;
	public static Block processor;
	
	public static void init()
	{
		oven = new BlockOven(3000, Material.wood).setUnlocalizedName("agriculture:oven").setCreativeTab(Agriculture.tab);
		counter = new BlockCounter(3001, Material.wood).setUnlocalizedName("agriculture:counter").setCreativeTab(Agriculture.tab);
		processor = new BlockProcessor(3002, Material.wood).setUnlocalizedName("agriculture:processor").setCreativeTab(Agriculture.tab);
		
		GameRegistry.registerBlock(oven, "AgricultureOvenBlock");
		GameRegistry.registerBlock(counter, "AgricultureCounterBlock");
		GameRegistry.registerBlock(processor, "AgricultureProcessorBlock");
		GameRegistry.registerTileEntity(TileEntityOven.class, "AgricultureOvenTileEntity");
		GameRegistry.registerTileEntity(TileEntityCounter.class, "AgricultureCounterTileEntity");
		GameRegistry.registerTileEntity(TileEntityProcessor.class, "AgricultureProcessorTileEntity");
		
		addNames();
	}
	
	public static void addNames()
	{
		LanguageRegistry.addName(oven, "Oven");
		LanguageRegistry.addName(counter, "Counter");
		LanguageRegistry.addName(processor, "Processor");
	}
}
