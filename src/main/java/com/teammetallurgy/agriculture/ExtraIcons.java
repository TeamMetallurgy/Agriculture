package com.teammetallurgy.agriculture;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fluids.FluidRegistry;

import com.teammetallurgy.agriculture.crops.BlockCrop;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ExtraIcons {
    public static IIcon cookingOil;
    public static IIcon liquidBeer;

    public static IIcon liquidCider;
    public static IIcon liquidHotcocoa;
    public static IIcon liquidMilk;
    public static IIcon liquidVodka;
    public static IIcon ovenSlotClosed;
    public static IIcon ovenSlotOpen;
    public static IIcon vineger;

    @SubscribeEvent
    public void onBoneMeal(final BonemealEvent event)
    {
        if (event.block instanceof BlockCrop)
        {
            if (((BlockCrop) event.block).fertilize(event.world, event.x, event.y, event.z))
            {
                event.setResult(Result.ALLOW);
            }
            else
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onItemIconRegister(final TextureStitchEvent.Post event)
    {
        FluidRegistry.getFluid("milk").setStillIcon(ExtraIcons.liquidMilk);
        FluidRegistry.getFluid("beer").setStillIcon(ExtraIcons.liquidBeer);
        FluidRegistry.getFluid("hotcocoa").setStillIcon(ExtraIcons.liquidHotcocoa);
        FluidRegistry.getFluid("cookingoil").setStillIcon(ExtraIcons.vineger);
        FluidRegistry.getFluid("vodka").setStillIcon(ExtraIcons.liquidVodka);
        FluidRegistry.getFluid("cider").setStillIcon(ExtraIcons.liquidCider);
        FluidRegistry.getFluid("vinegar").setStillIcon(ExtraIcons.vineger);
    }

    @SubscribeEvent
    public void onItemIconRegister(final TextureStitchEvent.Pre evt)
    {
        System.out.println("textureType = " + evt.map.getTextureType());
        if (evt.map.getTextureType() == 1)
        {
            ExtraIcons.ovenSlotOpen = evt.map.registerIcon("agriculture:gui/OvenSlotOpen");
            ExtraIcons.ovenSlotClosed = evt.map.registerIcon("agriculture:gui/OvenSlotClosed");
        }
        else
        {
            ExtraIcons.liquidMilk = evt.map.registerIcon("agriculture:milk");
            ExtraIcons.liquidBeer = evt.map.registerIcon("agriculture:beer");
            ExtraIcons.liquidHotcocoa = evt.map.registerIcon("agriculture:hotcocoa");
            ExtraIcons.vineger = evt.map.registerIcon("agriculture:vinegar");
            ExtraIcons.liquidVodka = evt.map.registerIcon("agriculture:vodka");
            ExtraIcons.liquidCider = evt.map.registerIcon("agriculture:cider");
        }
    }
}
