package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.recipes.CounterRecipes;

public class ContainerCounter extends Container
{
	IInventory inventory;

	public ContainerCounter(InventoryPlayer invPlayer, IInventory inventory)
	{
		this.inventory = inventory;
		int i;

		for (int j = 0; j < 4; j++)
		{
			this.addSlotToContainer(new Slot(inventory, j, 44, 6 + 19 * j));
		}

		for (i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				this.addSlotToContainer(new Slot(inventory, (i + j * 4) + 4, 65 + 18 * i, 6 + 19 * j));
			}
		}

		// Player Inventory
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
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		final int inventorySize = 20;

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			ItemStack itemstack2 = itemstack1.copy();

			itemstack2.stackSize = inventory.getInventoryStackLimit();

			if (par2 < inventorySize)
			{
				if (!this.mergeItemStack(itemstack1, inventorySize, this.inventorySlots.size(), true))
				{
					return null;
				}
			} else
			{
				if (this.mergeItemStack2(itemstack2, 0, inventorySize, false))
				{
					itemstack1.stackSize--;
					itemstack = itemstack1.copy();
				} else
				{
					return null;
				}
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			} else
			{
				slot.putStack(itemstack1);
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	protected boolean mergeItemStack2(ItemStack par1ItemStack, int par2, int par3, boolean par4)
	{
		boolean flag1 = false;
		int k = par2;

		if (par4)
		{
			k = par3 - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if (par1ItemStack.isStackable())
		{
			if (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2))
			{
				slot = (Slot) this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null && slot.isItemValid(par1ItemStack) && itemstack1.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
				{
					int l = itemstack1.stackSize + par1ItemStack.stackSize;

					if (l <= inventory.getInventoryStackLimit())
					{
						par1ItemStack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.stackSize < inventory.getInventoryStackLimit())
					{
						par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = inventory.getInventoryStackLimit();
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (par4)
				{
					--k;
				} else
				{
					++k;
				}
			}
		}

		if (par1ItemStack.stackSize > 0)
		{
			if (par4)
			{
				k = par3 - 1;
			} else
			{
				k = par2;
			}

			while (!par4 && k < par3 || par4 && k >= par2)
			{
				slot = (Slot) this.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null)
				{
					slot.putStack(par1ItemStack.copy());
					slot.onSlotChanged();
					par1ItemStack.stackSize = 0;
					flag1 = true;
					break;
				}

				if (par4)
				{
					--k;
				} else
				{
					++k;
				}
			}
		}

		return flag1;
	}

}
