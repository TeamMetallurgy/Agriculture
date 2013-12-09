package com.teammetallurgy.agriculture.machines;

import net.minecraft.inventory.IInventory;

public interface IFuelSlot {
    public IInventory getFuelInventory();

    public int getFuelSlot();

    public int getRemainingFuelLevel();
}
