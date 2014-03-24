package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

public class ContainerProcessor extends Container {
    private final TileEntityProcessor processor;

    public ContainerProcessor(final InventoryPlayer invPlayer, final TileEntityProcessor processor)
    {
        this.processor = processor;

        addSlotToContainer(new Slot(processor.getInventory(), 0, 57, 32));
        addSlotToContainer(new Slot(processor.getInventory(), 1, 21, 32));
        addSlotToContainer(new Slot(processor.getInventory(), 2, 103, 32));

        int i;
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

    public TileEntityProcessor getProcessor()
    {
        return processor;
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

            if (par2 < 3)
            {
                if (!mergeItemStack(itemstack1, 3, inventorySlots.size(), true)) { return null; }
            }
            else if (!mergeItemStack(itemstack1, 0, 2, false)) { return null; }

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
