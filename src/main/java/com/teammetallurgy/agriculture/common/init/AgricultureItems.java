package com.teammetallurgy.agriculture.common.init;

import com.teammetallurgy.agriculture.common.AgricultureItemGroup;
import com.teammetallurgy.agriculture.common.items.ItemCarrotJuice;

import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = "agriculture", bus = Mod.EventBusSubscriber.Bus.MOD)
public class AgricultureItems {

    public static final String CARROT_JUICE = "agriculture:carrot_juice";
    public static final ResourceLocation CARROT_JUICE_RL = new ResourceLocation(CARROT_JUICE);

    @ObjectHolder(AgricultureItems.CARROT_JUICE)
    public static ItemCarrotJuice carrotJuice;

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(
                new ItemCarrotJuice(getDefaultItemProperties().food(Foods.CARROT)).setRegistryName(CARROT_JUICE_RL));

    }

    public static Item.Properties getDefaultItemProperties() {
        return new Item.Properties().group(AgricultureItemGroup.GetInstance());
    }
}