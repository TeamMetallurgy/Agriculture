package com.teammetallurgy.agriculture.machines.counter;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.CounterRecipes;

public class TileEntityCounter extends BaseMachineTileEntity
{
	private InventoryCabinet cabinet = new InventoryCabinet("", false, 24, this);
	int numUsingPlayers;

	// Left door
	float prevLeftDoorAngle;
	float leftDoorAngle;

	// Right door
	double prevRightDoorAngle;
	float rightDoorAngle;

	public IInventory getCabinet()
	{
		return cabinet;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			cabinet.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}

		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{

		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.cabinet.getSizeInventory(); ++i)
		{
			if (this.cabinet.getStackInSlot(i) != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.cabinet.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbtTagList.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("Items", nbtTagList);

		super.writeToNBT(tag);
	}

	public boolean receiveClientEvent(int id, int value)
	{
		if (id == 1)
		{
			this.numUsingPlayers = value;
			return true;
		} else
		{
			return super.receiveClientEvent(id, value);
		}
	}

	@Override
	public void updateEntity()
	{
		prevLeftDoorAngle = leftDoorAngle;
		if (this.numUsingPlayers == 0 && leftDoorAngle > 0.0F || this.numUsingPlayers > 0 && leftDoorAngle < 1.0F)
		{
			if (this.numUsingPlayers > 0)
			{
				leftDoorAngle += 0.1;
			} else
			{
				leftDoorAngle -= 0.1;
			}

			if (leftDoorAngle > 1.0F)
			{
				leftDoorAngle = 1.0F;
			}

			if (leftDoorAngle < 0.0F)
			{
				leftDoorAngle = 0.0F;
			}
		}
	}
}
