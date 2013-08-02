package com.teammetallurgy.agriculture;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.agriculture.hunger.HungerOverlay;
import com.teammetallurgy.agriculture.machines.counter.CounterRenderHelper;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounterRenderer;
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
    }
}
