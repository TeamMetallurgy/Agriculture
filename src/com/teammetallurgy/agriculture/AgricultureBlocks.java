package com.teammetallurgy.agriculture;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.teammetallurgy.agriculture.crops.BlockSpice;
import com.teammetallurgy.agriculture.machines.brewer.BlockBrewer;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.counter.BlockCounter;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.frier.BlockFrier;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.icebox.BlockIcebox;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
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
	public static Block salt;
	public static Block cinnamon;
	public static Block brewer;
	public static Block icebox;
	public static Block frier;
			
	public static void init()
	{
		oven = new BlockOven(3000, Material.wood).setUnlocalizedName("agriculture:oven").setCreativeTab(Agriculture.tab);
		counter = new BlockCounter(3001, Material.wood).setUnlocalizedName("agriculture:counter").setCreativeTab(Agriculture.tab);
		processor = new BlockProcessor(3002, Material.wood).setUnlocalizedName("agriculture:processor").setCreativeTab(Agriculture.tab);
		salt = new BlockSalt(3003, Material.rock).setUnlocalizedName("agriculture:salt").func_111022_d("agriculture:Salt").setCreativeTab(Agriculture.tab);
		cinnamon = new BlockSpice(3004).setHardness(1.0f).setUnlocalizedName("agriculture:cinnamon").func_111022_d("agriculture:cinnamon").setCreativeTab(Agriculture.tab);
		
		brewer = new BlockBrewer(3005, Material.wood).setUnlocalizedName("agriculture:brewer").setCreativeTab(Agriculture.tab);
		icebox = new BlockIcebox(3006, Material.wood).setUnlocalizedName("agriculture:icebox").setCreativeTab(Agriculture.tab);
		frier = new BlockFrier(3007, Material.wood).setUnlocalizedName("agriculture:frier").setCreativeTab(Agriculture.tab);
		
		FluidRegistry.registerFluid(new Fluid("milk"));
		FluidRegistry.registerFluid(new Fluid("beer"));
		FluidRegistry.registerFluid(new Fluid("hotcocoa"));
		FluidRegistry.registerFluid(new Fluid("vinegar"));
		FluidRegistry.registerFluid(new Fluid("vodka"));
		FluidRegistry.registerFluid(new Fluid("cider"));
		
		GameRegistry.registerBlock(oven, "AgricultureOvenBlock");
		GameRegistry.registerBlock(counter, "AgricultureCounterBlock");
		GameRegistry.registerBlock(processor, "AgricultureProcessorBlock");
		GameRegistry.registerBlock(salt, "AgricultureSalt");
		GameRegistry.registerBlock(cinnamon, "AgricultureCinnamon");
		GameRegistry.registerBlock(brewer, "AgricultureBrewerBlock");
		GameRegistry.registerBlock(icebox, "AgricultureIcebox");
		GameRegistry.registerBlock(frier, "AgricultureFrier");
		GameRegistry.registerTileEntity(TileEntityOven.class, "AgricultureOvenTileEntity");
		GameRegistry.registerTileEntity(TileEntityCounter.class, "AgricultureCounterTileEntity");
		GameRegistry.registerTileEntity(TileEntityProcessor.class, "AgricultureProcessorTileEntity");
		GameRegistry.registerTileEntity(TileEntityBrewer.class, "AgricultureBrewerTileEntity");
		GameRegistry.registerTileEntity(TileEntityIcebox.class, "AgricultureIceboxTileEntity");
		GameRegistry.registerTileEntity(TileEntityFrier.class, "AgricultureFrierTileEntity");
		
		addNames();
	}
	
	public static void addNames()
	{
		LanguageRegistry.addName(oven, "Oven");
		LanguageRegistry.addName(counter, "Counter");
		LanguageRegistry.addName(processor, "Processor");
		LanguageRegistry.addName(brewer, "Brewer");
	}
}
