package com.teammetallurgy.agriculture.gui;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.ExtraIcons;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class SlotOven extends Slot
{

	public SlotOven(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

    public Icon getBackgroundIconIndex()
    {
    	if(isAvaliable())
    		return ExtraIcons.ovenSlotOpen;
    	else
    		return ExtraIcons.ovenSlotClosed;
    }
    
    public boolean isItemValid(ItemStack par1ItemStack)
    {
    	boolean isRack = par1ItemStack.getItem().itemID - 256 == AgricultureItems.ovenRack.itemID;	
        return isAvaliable() && !isRack;
    }

    public int getSlotStackLimit()
    {
    	return 1;
    }
    
    public boolean isAvaliable()
    {
    	int rackSlot = 17;
    	
    	if(getSlotIndex() >= 1 && getSlotIndex() <= 4)
    		return true;
    	if(getSlotIndex() >= 5 && getSlotIndex() <= 8)
    		rackSlot = 17;
    	if(getSlotIndex() >= 9 && getSlotIndex() <= 12)
    		rackSlot = 18;
    	if(getSlotIndex() >= 13 && getSlotIndex() <= 16)
    		rackSlot = 19;
    	
    	return inventory.getStackInSlot(rackSlot) != null;
    }
}
