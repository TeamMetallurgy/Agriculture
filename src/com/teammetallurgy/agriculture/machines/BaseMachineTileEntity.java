package com.teammetallurgy.agriculture.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class BaseMachineTileEntity extends TileEntity
{
	private final InventoryCounter inventoryCounter = new InventoryCounter("", false, 20);

	protected byte direction;

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	public byte getDirection()
	{
		return direction;
	}

	public IInventory getInventoryCounter()
	{
		return inventoryCounter;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readCustomNBT(pkt.customParam1);
	}

	public void readCustomNBT(NBTTagCompound tag)
	{
		final NBTTagList tagList = tag.getTagList("ItemsCounter");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			final NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			final int slot = Integer.valueOf(base.getByte("Slot"));
			inventoryCounter.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}

		direction = tag.getByte("direction");
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}

	public void setDirection(byte direction)
	{
		this.direction = direction;
	}

	public void writeCustomNBT(NBTTagCompound tag)
	{
		final NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < inventoryCounter.getSizeInventory(); ++i)
		{
			if (inventoryCounter.getStackInSlot(i) != null)
			{
				final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventoryCounter.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbtTagList.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("ItemsCounter", nbtTagList);

		tag.setByte("direction", direction);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}

}
