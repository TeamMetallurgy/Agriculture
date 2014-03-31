package com.teammetallurgy.agriculture.machines.icebox;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;

public class InventoryIcebox extends InventoryBasic {
    private TileEntityIcebox oven;

    public InventoryIcebox(final String par1Str, final boolean par2, final int par3)
    {
        super(par1Str, par2, par3);
    }

    public InventoryIcebox(final String par1Str, final boolean par2, final int par3, final TileEntityIcebox tileEntityOven)
    {
        super(par1Str, par2, par3);

        oven = tileEntityOven;
    }

    @Override
    public void closeInventory()
    {
        if (oven.getBlockType() != null && oven.getBlockType() instanceof BlockIcebox)
        {
            --oven.numUsingPlayers;
            oven.getWorldObj().addBlockEvent(oven.xCoord, oven.yCoord, oven.zCoord, oven.getBlockType(), 1, oven.numUsingPlayers);
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
        if (i >= 17 && i <= 19) { return itemstack.getItem() == AgricultureItems.ovenRack.getItem(); }

        return true;
    }

    @Override
    public void openInventory()
    {
        if (oven.numUsingPlayers < 0)
        {
            oven.numUsingPlayers = 0;
        }

        ++oven.numUsingPlayers;
        oven.getWorldObj().addBlockEvent(oven.xCoord, oven.yCoord, oven.zCoord, oven.getBlockType(), 1, oven.numUsingPlayers);
    }
}
