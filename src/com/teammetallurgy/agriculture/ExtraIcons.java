package com.teammetallurgy.agriculture;

import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.FluidRegistry;

public class ExtraIcons
{
	public static Icon ovenSlotOpen;
	public static Icon ovenSlotClosed;
	
	public static Icon liquidMilk;
	public static Icon liquidBeer;
	public static Icon liquidHotcocoa;
	public static Icon cookingOil;
	public static Icon vineger;
	public static Icon liquidVodka;
	public static Icon liquidCider;
	
	@ForgeSubscribe
	public void onItemIconRegister(TextureStitchEvent.Pre evt) 
	{
		System.out.println("textureType = " + evt.map.textureType);
		if(evt.map.textureType == 1)
		{
			ovenSlotOpen = evt.map.registerIcon("agriculture:gui/OvenSlotOpen");
			ovenSlotClosed = evt.map.registerIcon("agriculture:gui/OvenSlotClosed");
		} else {
			liquidMilk = evt.map.registerIcon("agriculture:milk");
			liquidBeer = evt.map.registerIcon("agriculture:beer");
			liquidHotcocoa = evt.map.registerIcon("agriculture:hotcocoa");
			vineger = evt.map.registerIcon("agriculture:vinegar");
			liquidVodka = evt.map.registerIcon("agriculture:vodka");
			liquidCider = evt.map.registerIcon("agriculture:cider");
		}
	}
	
	@ForgeSubscribe
	public void onItemIconRegister(TextureStitchEvent.Post event) 
	{
		FluidRegistry.getFluid("milk").setStillIcon(liquidMilk);
		FluidRegistry.getFluid("beer").setStillIcon(liquidBeer);
		FluidRegistry.getFluid("hotcocoa").setStillIcon(liquidHotcocoa);
		FluidRegistry.getFluid("cookingoil").setStillIcon(vineger);
		FluidRegistry.getFluid("vodka").setStillIcon(liquidVodka);
		FluidRegistry.getFluid("cider").setStillIcon(liquidCider);
		FluidRegistry.getFluid("vinegar").setStillIcon(vineger);
	}
}
