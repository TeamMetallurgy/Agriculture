package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;

public class ContainerBrewer extends Container
{

	private TileEntityBrewer cabinet;

	public ContainerBrewer(InventoryPlayer invPlayer, TileEntityBrewer brewer)
	{
		this.cabinet = brewer;
		cabinet.getBrewer().openChest();

		int i;
		this.addSlotToContainer(new Slot(brewer.getBrewer(), 0, 80, 13));
		this.addSlotToContainer(new Slot(brewer.getBrewer(), 1, 80, 58));
		this.addSlotToContainer(new Slot(brewer.getBrewer(), 2, 26, 36));

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}

	}

	@Override
	public void onContainerClosed(EntityPlayer entityplayer)
	{
		super.onContainerClosed(entityplayer);
		cabinet.getBrewer().closeChest();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < 3)
			{
				if (!this.mergeItemStack(itemstack1, 4, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 4, false))
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			} else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public TileEntityBrewer getTe()
	{
		return cabinet;
	}

}
