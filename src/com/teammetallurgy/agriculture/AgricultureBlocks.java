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

public class AgricultureBlocks
{
	public static Block oven;
	public static Block counter;
	public static Block processor;
	
	public static void init()
	{
		oven = new BlockOven(3000, Material.wood).setCreativeTab(CreativeTabs.tabDecorations);
		counter = new BlockCounter(3001, Material.wood).setCreativeTab(CreativeTabs.tabDecorations);
		processor = new BlockProcessor(3002, Material.wood).setCreativeTab(CreativeTabs.tabDecorations);
		
		GameRegistry.registerBlock(oven, "AgricultureOvenBlock");
		GameRegistry.registerBlock(counter, "AgricultureCounterBlock");
		GameRegistry.registerBlock(processor, "AgricultureProcessorBlock");
		GameRegistry.registerTileEntity(TileEntityOven.class, "AgricultureOvenTileEntity");
		GameRegistry.registerTileEntity(TileEntityCounter.class, "AgricultureCounterTileEntity");
		GameRegistry.registerTileEntity(TileEntityProcessor.class, "AgricultureProcessorTileEntity");
	}
}
