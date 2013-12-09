package com.teammetallurgy.agriculture;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayerMP;

import com.teammetallurgy.agriculture.hunger.HungerSystem;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickHandler implements ITickHandler
{
	int hungerUpdate = 20;

	@Override
	public String getLabel()
	{
		return "AgricultureTickHandler";
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
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		final EntityPlayerMP player = (EntityPlayerMP) tickData[0];
		if (hungerUpdate-- <= 0)
		{

			HungerSystem.tick(player);
			HungerSystem.applyBonuses(player);
			hungerUpdate = 20;
		}

	}

}
