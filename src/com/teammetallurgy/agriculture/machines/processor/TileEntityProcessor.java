package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;

import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;

public class TileEntityProcessor extends BaseMachineTileEntity
{
	private IInventory inventory = new InventoryBasic("", false, 2);

	public IInventory getInventory()
	{
		return inventory;
	}

}
