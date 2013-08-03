package com.teammetallurgy.agriculture.machines;

import com.teammetallurgy.agriculture.recipes.CounterRecipes;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class BaseMachineTileEntity extends TileEntity
{
	private InventoryCounter inventoryCounter = new InventoryCounter("", false, 20);
	
	protected byte direction;

	public byte getDirection() 
	{
		return direction;
	}

	public void setDirection(byte direction) 
	{
		this.direction = direction;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

    public void readCustomNBT(NBTTagCompound tag)
    {
		NBTTagList tagList = tag.getTagList("ItemsCounter");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			inventoryCounter.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
    	
    	direction = tag.getByte("direction");
    }
	
    public void writeCustomNBT(NBTTagCompound tag)
    {
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.inventoryCounter.getSizeInventory(); ++i)
		{
			if (this.inventoryCounter.getStackInSlot(i) != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.inventoryCounter.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbtTagList.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("ItemsCounter", nbtTagList);
		
    	tag.setByte("direction", direction);
    }
    
    public IInventory getInventoryCounter()
	{
		return inventoryCounter;
	}
}
