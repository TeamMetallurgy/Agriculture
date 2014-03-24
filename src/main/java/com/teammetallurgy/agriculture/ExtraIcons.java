package com.teammetallurgy.agriculture;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fluids.FluidRegistry;

import com.teammetallurgy.agriculture.crops.BlockCrop;

public class ExtraIcons {
    public static Icon cookingOil;
    public static Icon liquidBeer;

    public static Icon liquidCider;
    public static Icon liquidHotcocoa;
    public static Icon liquidMilk;
    public static Icon liquidVodka;
    public static Icon ovenSlotClosed;
    public static Icon ovenSlotOpen;
    public static Icon vineger;

    @ForgeSubscribe
    public void onBoneMeal(final BonemealEvent event)
    {
        final Block block = Block.blocksList[event.ID];
        if (block instanceof BlockCrop)
        {
            if (((BlockCrop) block).fertilize(event.world, event.X, event.Y, event.Z))
            {
                event.setResult(Result.ALLOW);
            }
            else
            {
                event.setCanceled(true);
            }
        }
    }

    @ForgeSubscribe
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

    @ForgeSubscribe
    public void onItemIconRegister(final TextureStitchEvent.Pre evt)
    {
        System.out.println("textureType = " + evt.map.textureType);
        if (evt.map.textureType == 1)
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
