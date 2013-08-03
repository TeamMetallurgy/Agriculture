package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

public class ContainerOven extends Container
{
	private TileEntityOven oven;

	public ContainerOven(InventoryPlayer invePlayer, TileEntityOven oven)
	{
		this.oven = oven;
		oven.openChest();

		int i;

		// Fuel Slot
		this.addSlotToContainer(new Slot(oven, 0, 8, 34));

		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				this.addSlotToContainer(new SlotOven(oven, (x + 1) + (y * 4), 74 + x * 19, 6 + y * 19));
			}
		}

		// Oven Rack Slots
		this.addSlotToContainer(new SlotOvenRack(oven, 17, 76 + 4 * 19, 6 + 1 * 19));
		this.addSlotToContainer(new SlotOvenRack(oven, 18, 76 + 4 * 19, 6 + 2 * 19));
		this.addSlotToContainer(new SlotOvenRack(oven, 19, 76 + 4 * 19, 6 + 3 * 19));

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(invePlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(invePlayer, i, 8 + i * 18, 142));
		}
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			int itemDamage = itemstack.getItemDamage();

			if (par2 >= 0 && par2 <= 19)
			{
				if (!mergeItemStack(itemstack1, 20, this.inventorySlots.size(), true))
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else
			{
				if (itemstack.itemID - 256 == AgricultureItems.ovenRack.itemID && itemDamage == AgricultureItems.ovenRack.getDamage())
				{
					for (int i = 17; i <= 19; i++)
					{
						Slot slot1 = (Slot) this.inventorySlots.get(i);
						if (!slot1.getHasStack())
						{
							slot1.putStack(AgricultureItems.ovenRack.getItemStack());
							itemstack1.stackSize--;
							slot.onSlotChange(itemstack1, itemstack);
							return null;
						}
					}

					slot.onSlotChange(itemstack1, itemstack);
				} else if (oven.getItemBurnTime(itemstack) > 0)
				{
					if (!mergeItemStack(itemstack1, 0, 1, false))
					{
						return null;
					}

					slot.onSlotChange(itemstack1, itemstack);
				} else
				{
					for (int i = 1; i <= 16; i++)
					{
						SlotOven slot1 = (SlotOven) this.inventorySlots.get(i);
						if (!slot1.getHasStack() && slot1.isItemValid(itemstack1))
						{
							ItemStack singleStack = itemstack.copy();
							singleStack.stackSize = 1;
							slot1.putStack(singleStack);
							itemstack1.stackSize--;
							slot.onSlotChange(itemstack1, itemstack);
							return null;
						}
					}

					slot.onSlotChange(itemstack1, itemstack);
				}

			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			} else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;

	}

	public void onContainerClosed(EntityPlayer entityplayer)
	{
		super.onContainerClosed(entityplayer);
		oven.closeChest();
	}

	public TileEntityOven getOven()
	{
		return oven;
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
