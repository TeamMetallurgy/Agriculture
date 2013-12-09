package com.teammetallurgy.agriculture.machines.oven;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;

public class InventoryOven extends InventoryBasic {

    private final TileEntityOven oven;

    public InventoryOven(final String par1Str, final boolean par2, final int par3, final TileEntityOven tileEntityOven)
    {
        super(par1Str, par2, par3);

        oven = tileEntityOven;
    }

    @Override
    public void closeChest()
    {
        if (oven.getBlockType() != null && oven.getBlockType() instanceof BlockOven)
        {
            --oven.numUsingPlayers;
            oven.worldObj.addBlockEvent(oven.xCoord, oven.yCoord, oven.zCoord, oven.getBlockType().blockID, 1, oven.numUsingPlayers);
        }
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack)
    {
        if (i >= 17 && i <= 19) { return itemstack.itemID == AgricultureItems.ovenRack.itemID; }

        return true;
    }

    @Override
    public void openChest()
    {
        if (oven.numUsingPlayers < 0)
        {
            oven.numUsingPlayers = 0;
        }

        ++oven.numUsingPlayers;
        oven.worldObj.addBlockEvent(oven.xCoord, oven.yCoord, oven.zCoord, oven.getBlockType().blockID, 1, oven.numUsingPlayers);
    }
}
