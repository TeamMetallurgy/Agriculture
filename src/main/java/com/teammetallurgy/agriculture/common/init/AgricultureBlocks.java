package com.teammetallurgy.agriculture.common.init;

import com.teammetallurgy.agriculture.common.blocks.BlockJuicer;

import net.minecraft.block.Block;
import net.minecraft.block.Block.Properties;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = "agriculture", bus = Mod.EventBusSubscriber.Bus.MOD)
public class AgricultureBlocks {

    public static final String JUICER = "agriculture:juicer";
    public static final ResourceLocation JUICER_RL = new ResourceLocation(JUICER);

    @ObjectHolder(AgricultureBlocks.JUICER)
    public static BlockJuicer juicer;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(
                new BlockJuicer(Properties.from(Blocks.FURNACE)).setRegistryName(AgricultureBlocks.JUICER_RL));
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(new BlockItem(juicer, AgricultureItems.getDefaultItemProperties())
                .setRegistryName(AgricultureBlocks.JUICER_RL));

    }

    @SubscribeEvent
    public static void registerTiles(RegistryEvent.Register<TileEntityType<?>> event) {
        final IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
    }
}