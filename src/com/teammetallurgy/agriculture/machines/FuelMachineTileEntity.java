package com.teammetallurgy.agriculture.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class FuelMachineTileEntity extends BaseMachineTileEntity implements IFuelSlot
{
	protected int currentItemBurnTime = -10;
	protected int fuelRemaining = 0;

	public int getRemainingFuelLevel()
	{
		return fuelRemaining;
	}

	public void setFuelRemaining(int fuelRemaining)
	{
		this.fuelRemaining = fuelRemaining;
	}

	public void setCurrentItemBurnTime(int currentItemBurnTime)
	{
		this.currentItemBurnTime = currentItemBurnTime;
	}

	public int getCurrentItemBurnTime()
	{
		return currentItemBurnTime;
	}

	protected void burnFuel()
	{
		ItemStack fuelStack = getFuelInventory().getStackInSlot(getFuelSlot());

		int fuelAmount = TileEntityFurnace.getItemBurnTime(fuelStack);
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

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (this.fuelRemaining <= 0)
		{
			burnFuel();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.readCustomNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		this.writeCustomNBT(tag);
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		super.readCustomNBT(tag);
		this.currentItemBurnTime = tag.getInteger("BurnTime");

		NBTTagList tagList2 = tag.getTagList("FuelSlot");

		for (int i = 0; i < tagList2.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList2.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			getFuelInventory().setInventorySlotContents(getFuelSlot(), ItemStack.loadItemStackFromNBT(base));
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{
		super.writeCustomNBT(tag);
		tag.setInteger("BurnTime", currentItemBurnTime);

		NBTTagList itemListTag2 = new NBTTagList();
		
		if (getFuelInventory().getStackInSlot(getFuelSlot()) != null)
		{
			NBTTagCompound itemTag = new NBTTagCompound();
			itemTag.setByte("Slot", (byte) getFuelSlot());
			getFuelInventory().getStackInSlot(getFuelSlot()).writeToNBT(itemTag);
			itemListTag2.appendTag(itemTag);
		}

		tag.setTag("FuelSlot", itemListTag2);
	}
}
