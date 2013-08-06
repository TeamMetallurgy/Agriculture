package com.teammetallurgy.agriculture.machines.frier;

import net.minecraft.inventory.InventoryBasic;

public class InventoryFrier extends InventoryBasic
{
	TileEntityFrier counter;

	public InventoryFrier(String par1Str, boolean par2, int par3, TileEntityFrier tileEntityCounter)
	{
		super(par1Str, par2, par3);
		counter = tileEntityCounter;
	}

	@Override
	public void closeChest()
	{
		if (counter.getBlockType() != null && counter.getBlockType() instanceof BlockFrier)
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
