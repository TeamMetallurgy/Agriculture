package com.teammetallurgy.agriculture;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

import com.teammetallurgy.agriculture.hunger.HungerOverlay;
import com.teammetallurgy.agriculture.hunger.HungerSystem;
import com.teammetallurgy.agriculture.machines.RenderHelper;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewerRenderer;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounterRenderer;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrierRenderer;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIceboxRenderer;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOvenRenderer;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessorRenderer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerEventHandlers()
    {
        MinecraftForge.EVENT_BUS.register(new HungerOverlay());
        MinecraftForge.EVENT_BUS.register(new ExtraIcons());
    }

    @Override
    public void registerRenderers()
    {
        registerTERender(AgricultureBlocks.oven, TileEntityOven.class, new TileEntityOvenRenderer());
        registerTERender(AgricultureBlocks.counter, TileEntityCounter.class, new TileEntityCounterRenderer());
        registerTERender(AgricultureBlocks.processor, TileEntityProcessor.class, new TileEntityProcessorRenderer());
        registerTERender(AgricultureBlocks.brewer, TileEntityBrewer.class, new TileEntityBrewerRenderer());
        registerTERender(AgricultureBlocks.icebox, TileEntityIcebox.class, new TileEntityIceboxRenderer());
        registerTERender(AgricultureBlocks.frier, TileEntityFrier.class, new TileEntityFrierRenderer());
    }

    private void registerTERender(final Block block, final Class<? extends TileEntity> tileEntity, final TileEntitySpecialRenderer ovenRenderer)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(tileEntity, ovenRenderer);
        try
        {
            RenderingRegistry.registerBlockHandler(new RenderHelper(block, tileEntity.newInstance()));
        }
        catch (final Exception ignored)
        {
        }
    }

    @Override
    public void updateHunger(final float hunger)
    {
        new HungerSystem(Minecraft.getMinecraft().thePlayer, hunger);
    }
}
