package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.machines.oven.InventoryOven;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

public class ContainerOven extends Container {
    private final InventoryOven oven;
    private final TileEntityOven teOven;

    public ContainerOven(final InventoryPlayer invePlayer, final TileEntityOven teOven)
    {
        oven = teOven.getInventoryOven();
        oven.openInventory();

        this.teOven = teOven;
        int i;

        // Fuel Slot
        addSlotToContainer(new Slot(oven, 0, 8, 34));

        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                addSlotToContainer(new SlotOven(oven, x + 1 + y * 4, 74 + x * 19, 6 + y * 19));
            }
        }

        // Oven Rack Slots
        addSlotToContainer(new SlotOvenRack(oven, 17, 76 + 4 * 19, 6 + 1 * 19));
        addSlotToContainer(new SlotOvenRack(oven, 18, 76 + 4 * 19, 6 + 2 * 19));
        addSlotToContainer(new SlotOvenRack(oven, 19, 76 + 4 * 19, 6 + 3 * 19));

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                addSlotToContainer(new Slot(invePlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            addSlotToContainer(new Slot(invePlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canDragIntoSlot(final Slot par1Slot)
    {
        return true;
    }

    @Override
    public boolean canInteractWith(final EntityPlayer entityplayer)
    {
        return true;
    }

    public TileEntityOven getOven()
    {
        return teOven;
    }

    protected boolean mergeItemStack2(final ItemStack par1ItemStack, final int par2, final int par3, final boolean par4)
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

                if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1))
                {
                    final int l = itemstack1.stackSize + par1ItemStack.stackSize;

                    if (l <= slot.getSlotStackLimit())
                    {
                        par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                    else if (itemstack1.stackSize < slot.getSlotStackLimit())
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
                }
                else
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
            }
            else
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
                }
                else
                {
                    ++k;
                }
            }
        }

        return flag1;
    }

    @Override
    public void onContainerClosed(final EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
        oven.closeInventory();
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

            final int itemDamage = itemstack.getItemDamage();

            if (par2 <= 19)
            {
                if (!mergeItemStack(itemstack1, 20, inventorySlots.size(), true)) { return null; }
            }
            else
            {
                if (itemstack.getItem() == AgricultureItems.ovenRack.getItemStack().getItem() && itemDamage == AgricultureItems.ovenRack.getDamage())
                {
                    if (!mergeItemStack2(itemstack1, 17, 20, false)) { return null; }

                }
                else if (TileEntityFurnace.getItemBurnTime(itemstack) > 0)
                {
                    if (!mergeItemStack(itemstack1, 0, 1, false)) { return null; }
                }
                else
                {
                    if (!mergeItemStack2(itemstack1, 1, 17, false)) { return null; }
                }
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) { return null; }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return null;

    }

}
