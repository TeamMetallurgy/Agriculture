package com.teammetallurgy.agriculture;

import java.util.EnumSet;

import org.lwjgl.Sys;

import com.teammetallurgy.agriculture.hunger.HungerSystem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler
{
	int hungerUpdate = 20;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		EntityPlayerMP player = (EntityPlayerMP) tickData[0];
		if(hungerUpdate-- <= 0)
		{
			HungerSystem.tick(player);
			HungerSystem.applyBonuses(player);
			hungerUpdate = 20;
		}
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel()
	{
		return "AgricultureTickHandler";
	}

}
