package com.teammetallurgy.agriculture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Icon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ExtraIcons
{
	public static Icon ovenSlotOpen;
	public static Icon ovenSlotClosed;
	
	@ForgeSubscribe
	public void onItemIconRegister(TextureStitchEvent.Pre evt) 
	{
		System.out.println("textureType = " + evt.map.textureType);
		if(evt.map.textureType == 1)
		{
			ovenSlotOpen = evt.map.registerIcon("agriculture:gui/OvenSlotOpen");
			ovenSlotClosed = evt.map.registerIcon("agriculture:gui/OvenSlotClosed");
		}
	}
}
