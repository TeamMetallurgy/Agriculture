package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;

public class ContainerBrewer extends Container {

    private final TileEntityBrewer cabinet;

    public ContainerBrewer(final InventoryPlayer invPlayer, final TileEntityBrewer brewer)
    {
        cabinet = brewer;
        cabinet.getBrewer().openInventory();

        int i;
        addSlotToContainer(new Slot(brewer.getBrewer(), 0, 80, 13));
        addSlotToContainer(new Slot(brewer.getBrewer(), 1, 80, 58));
        addSlotToContainer(new Slot(brewer.getBrewer(), 2, 26, 36));

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

    public TileEntityBrewer getTe()
    {
        return cabinet;
    }

    @Override
    public void onContainerClosed(final EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
        cabinet.getBrewer().closeInventory();
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
                if (!mergeItemStack(itemstack1, 4, inventorySlots.size(), true)) { return null; }
            }
            else if (!mergeItemStack(itemstack1, 0, 4, false)) { return null; }

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
