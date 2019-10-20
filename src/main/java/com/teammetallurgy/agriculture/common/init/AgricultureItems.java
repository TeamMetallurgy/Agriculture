package com.teammetallurgy.agriculture.common.init;

import com.teammetallurgy.agriculture.common.AgricultureItemGroup;
import com.teammetallurgy.agriculture.common.items.ItemCarrotJuice;

import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

public class AgricultureItems {
    @Mod.EventBusSubscriber(modid = "agriculture", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            final Item[] items = { new ItemCarrotJuice(getDefaultItemProperties().food(Foods.CARROT))
                    .setRegistryName("carrot_juice") };
            final IForgeRegistry<Item> registry = event.getRegistry();
            for (final Item item : items) {
                registry.register(item);
            }
        }

        private static Item.Properties getDefaultItemProperties() {
            return new Item.Properties().group(AgricultureItemGroup.GetInstance());
        }
    }
}