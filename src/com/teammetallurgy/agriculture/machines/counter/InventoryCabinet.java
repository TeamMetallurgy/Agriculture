package com.teammetallurgy.agriculture.machines.counter;

import net.minecraft.inventory.InventoryBasic;

public class InventoryCabinet extends InventoryBasic
{

	TileEntityCounter counter;

	public InventoryCabinet(String par1Str, boolean par2, int par3, TileEntityCounter tileEntityCounter)
	{
		super(par1Str, par2, par3);
		counter = tileEntityCounter;
	}

	@Override
	public void closeChest()
	{
		if (counter.getBlockType() != null && counter.getBlockType() instanceof BlockCounter)
		{
			--counter.numUsingPlayers;
			counter.worldObj.addBlockEvent(counter.xCoord, counter.yCoord, counter.zCoord, counter.getBlockType().blockID, 1, counter.numUsingPlayers);
		}
	}

	@Override
	public void openChest()
	{
		if (counter.numUsingPlayers < 0)
		{
			counter.numUsingPlayers = 0;
		}

		++counter.numUsingPlayers;
		counter.worldObj.addBlockEvent(counter.xCoord, counter.yCoord, counter.zCoord, counter.getBlockType().blockID, 1, counter.numUsingPlayers);
	}

}
