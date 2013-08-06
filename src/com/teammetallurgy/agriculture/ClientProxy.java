package com.teammetallurgy.agriculture;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.agriculture.hunger.HungerOverlay;
import com.teammetallurgy.agriculture.hunger.HungerSystem;
import com.teammetallurgy.agriculture.machines.brewer.BrewerRenderHelper;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewerRenderer;
import com.teammetallurgy.agriculture.machines.counter.CounterRenderHelper;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounterRenderer;
import com.teammetallurgy.agriculture.machines.frier.FrierRenderHelper;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrierRenderer;
import com.teammetallurgy.agriculture.machines.icebox.IceboxRenderHelper;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIceboxRenderer;
import com.teammetallurgy.agriculture.machines.oven.OvenRenderHelper;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOvenRenderer;
import com.teammetallurgy.agriculture.machines.processor.ProcessorRenderHelper;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessorRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new HungerOverlay());
		MinecraftForge.EVENT_BUS.register(new ExtraIcons());
	}

	@Override
	public void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOven.class, new TileEntityOvenRenderer());
		RenderingRegistry.registerBlockHandler(new OvenRenderHelper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCounter.class, new TileEntityCounterRenderer());
		RenderingRegistry.registerBlockHandler(new CounterRenderHelper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityProcessor.class, new TileEntityProcessorRenderer());
		RenderingRegistry.registerBlockHandler(new ProcessorRenderHelper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrewer.class, new TileEntityBrewerRenderer());
		RenderingRegistry.registerBlockHandler(new BrewerRenderHelper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIcebox.class, new TileEntityIceboxRenderer());
		RenderingRegistry.registerBlockHandler(new IceboxRenderHelper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFrier.class, new TileEntityFrierRenderer());
		RenderingRegistry.registerBlockHandler(new FrierRenderHelper());
	}
	
	@Override
	public void updateHunger(float hunger)
	{
		new HungerSystem(Minecraft.getMinecraft().thePlayer, hunger);
	}
}
