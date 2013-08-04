package com.teammetallurgy.agriculture.machines.brewer;

import com.teammetallurgy.agriculture.machines.oven.BlockOven;

import net.minecraft.inventory.InventoryBasic;

public class InventoryBrewer extends InventoryBasic
{

	TileEntityBrewer counter;
	public InventoryBrewer(String par1Str, boolean par2, int par3, TileEntityBrewer tileEntityCounter)
	{
		super(par1Str, par2, par3);
		this.counter = tileEntityCounter;
	}
	
	@Override
	public void openChest()
	{
		if (this.counter.numUsingPlayers < 0)
		{
			this.counter.numUsingPlayers = 0;
		}

		++this.counter.numUsingPlayers;
		this.counter.worldObj.addBlockEvent(this.counter.xCoord, this.counter.yCoord, this.counter.zCoord, this.counter.getBlockType().blockID, 1, this.counter.numUsingPlayers);
	}

	@Override
	public void closeChest()
	{
		if (this.counter.getBlockType() != null && this.counter.getBlockType() instanceof BlockBrewer)
		{
			--this.counter.numUsingPlayers;
			this.counter.worldObj.addBlockEvent(this.counter.xCoord, this.counter.yCoord, this.counter.zCoord, this.counter.getBlockType().blockID, 1, this.counter.numUsingPlayers);
		}
	}

}
