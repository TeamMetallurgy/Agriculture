package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;

public class ContainerCabinet extends Container {

    private final TileEntityCounter cabinet;

    public ContainerCabinet(final InventoryPlayer invPlayer, final TileEntityCounter cabinet)
    {
        this.cabinet = cabinet;
        cabinet.getCabinet().openChest();

        int i;

        for (i = 0; i < 4; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                addSlotToContainer(new Slot(cabinet.getCabinet(), i + j * 4, 8 + 18 * i, 14 + 18 * j));
            }
        }

        for (i = 0; i < 4; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                addSlotToContainer(new Slot(cabinet.getCabinet(), i + j * 4 + 12, 98 + 18 * i, 14 + 18 * j));
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
    public boolean canInteractWith(final EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void onContainerClosed(final EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
        cabinet.getCabinet().closeChest();
    }

    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2)
    {
        ItemStack itemstack = null;
        final Slot slot = (Slot) inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < 24)
            {
                if (!mergeItemStack(itemstack1, 24, inventorySlots.size(), true)) { return null; }
            }
            else if (!mergeItemStack(itemstack1, 0, 24, false)) { return null; }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

}
