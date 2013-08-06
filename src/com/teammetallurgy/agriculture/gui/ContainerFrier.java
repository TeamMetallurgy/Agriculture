package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;

public class ContainerFrier extends Container
{
	private TileEntityFrier cabinet;

	public ContainerFrier(InventoryPlayer invPlayer, TileEntityFrier cabinet)
	{
		this.cabinet = cabinet;
		cabinet.getInventory().openChest();

		int i;

		addSlotToContainer(new Slot(cabinet.getInventory(), 0, 22, 35));

		for (i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				addSlotToContainer(new Slot(cabinet.getInventory(), 1 + i + j * 4, 71 + 18 * i, 17 + 18 * j)
				{
					@Override
					public int getSlotStackLimit()
					{
						return 1;
					}
				});
			}
		}

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	public TileEntityFrier getTE()
	{
		return cabinet;
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
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null && slot.isItemValid(par1ItemStack) && itemstack1.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
				{
					final int l = itemstack1.stackSize + par1ItemStack.stackSize;

					if (l <= slot.getSlotStackLimit())
					{
						par1ItemStack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.stackSize < slot.getSlotStackLimit())
					{
						par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
						itemstack1.stackSize = slot.getSlotStackLimit();
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
				slot = (Slot) inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null && slot.isItemValid(par1ItemStack))
				{
					final ItemStack itemStack = par1ItemStack.copy();
					itemStack.stackSize = 1;
					slot.putStack(itemStack);
					slot.onSlotChanged();
					par1ItemStack.stackSize--;
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

	@Override
	public void onContainerClosed(EntityPlayer entityplayer)
	{
		super.onContainerClosed(entityplayer);
		cabinet.getInventory().closeChest();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		final Slot slot = (Slot) inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			final ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 < 10)
			{
				if (!mergeItemStack(itemstack1, 10, inventorySlots.size(), true))
				{
					return null;
				}
			} else if (!mergeItemStack2(itemstack1, 0, 10, false))
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

}
