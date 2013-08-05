package com.teammetallurgy.agriculture.machines;

import net.minecraft.inventory.IInventory;

public interface IFuelSlot
{
	public int getRemainingFuelLevel();
	public int getFuelSlot();
	public IInventory getFuelInventory();
}
