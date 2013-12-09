package com.teammetallurgy.agriculture.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class FuelMachineTileEntity extends BaseMachineTileEntity implements IFuelSlot
{
	protected int currentItemBurnTime = -10;
	protected int fuelRemaining = 0;

	protected void burnFuel()
	{
		final ItemStack fuelStack = getFuelInventory().getStackInSlot(getFuelSlot());

		final int fuelAmount = TileEntityFurnace.getItemBurnTime(fuelStack);
		if (fuelAmount > 0)
		{
			fuelRemaining += fuelAmount / 5;

			fuelStack.stackSize--;

			if (fuelStack.stackSize == 0)
			{
				getFuelInventory().setInventorySlotContents(getFuelSlot(), fuelStack.getItem().getContainerItemStack(fuelStack));
			}
		}
	}

	public int getCurrentItemBurnTime()
	{
		return currentItemBurnTime;
	}

	@Override
	public int getRemainingFuelLevel()
	{
		return fuelRemaining;
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		super.readCustomNBT(tag);
		currentItemBurnTime = tag.getInteger("BurnTime");

		final NBTTagList tagList2 = tag.getTagList("FuelSlot");

		for (int i = 0; i < tagList2.tagCount(); i++)
		{
			final NBTTagCompound base = (NBTTagCompound) tagList2.tagAt(i);
			Integer.valueOf(base.getByte("Slot"));
			getFuelInventory().setInventorySlotContents(getFuelSlot(), ItemStack.loadItemStackFromNBT(base));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}

	public void setCurrentItemBurnTime(int currentItemBurnTime)
	{
		this.currentItemBurnTime = currentItemBurnTime;
	}

	public void setFuelRemaining(int fuelRemaining)
	{
		this.fuelRemaining = fuelRemaining;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (fuelRemaining <= 0)
		{
			burnFuel();
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{
		super.writeCustomNBT(tag);
		tag.setInteger("BurnTime", currentItemBurnTime);

		final NBTTagList itemListTag2 = new NBTTagList();

		if (getFuelInventory().getStackInSlot(getFuelSlot()) != null)
		{
			final NBTTagCompound itemTag = new NBTTagCompound();
			itemTag.setByte("Slot", (byte) getFuelSlot());
			getFuelInventory().getStackInSlot(getFuelSlot()).writeToNBT(itemTag);
			itemListTag2.appendTag(itemTag);
		}

		tag.setTag("FuelSlot", itemListTag2);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}
}
