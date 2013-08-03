package com.teammetallurgy.agriculture.gui;

import com.teammetallurgy.agriculture.AgricultureItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOvenRack extends Slot
{

	public SlotOvenRack(IInventory par1iInventory, int par2, int par3, int par4)
	{
		super(par1iInventory, par2, par3, par4);
	}

    public boolean isItemValid(ItemStack par1ItemStack)
    {
    	int itemID = AgricultureItems.ovenRack.itemID;
    	
		return par1ItemStack.getItem().itemID - 256 == itemID;
    }

    public int getSlotStackLimit()
    {
    	return 1;
    }

    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
    	int slotStart = 5;
    	if(this.getSlotIndex() == 17)
    		slotStart = 5;
    	if(this.getSlotIndex() == 18)
    		slotStart = 9;
    	if(this.getSlotIndex() == 19)
    		slotStart = 13;
    	
    	for(int i = 0; i < 4; i++)
    	{
    		if(this.inventory.getStackInSlot(slotStart + i) != null)
    			return false;
    	}
        return true;
    }
}
