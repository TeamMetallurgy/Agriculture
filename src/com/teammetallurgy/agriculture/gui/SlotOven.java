package com.teammetallurgy.agriculture.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.ExtraIcons;

public class SlotOven extends Slot {

    public SlotOven(final IInventory par1iInventory, final int par2, final int par3, final int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public Icon getBackgroundIconIndex()
    {
        if (isAvaliable())
        {
            return ExtraIcons.ovenSlotOpen;
        }
        else
        {
            return ExtraIcons.ovenSlotClosed;
        }
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }

    public boolean isAvaliable()
    {
        int rackSlot = 17;

        if (getSlotIndex() >= 1 && getSlotIndex() <= 4) { return true; }
        if (getSlotIndex() >= 5 && getSlotIndex() <= 8)
        {
            rackSlot = 17;
        }
        if (getSlotIndex() >= 9 && getSlotIndex() <= 12)
        {
            rackSlot = 18;
        }
        if (getSlotIndex() >= 13 && getSlotIndex() <= 16)
        {
            rackSlot = 19;
        }

        return inventory.getStackInSlot(rackSlot) != null;
    }

    @Override
    public boolean isItemValid(final ItemStack par1ItemStack)
    {
        final boolean isRack = par1ItemStack.isItemEqual(AgricultureItems.ovenRack.getItemStack());
        return isAvaliable() && !isRack;
    }
}
