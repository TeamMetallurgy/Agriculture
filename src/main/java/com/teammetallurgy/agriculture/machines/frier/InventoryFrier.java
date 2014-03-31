package com.teammetallurgy.agriculture.machines.frier;

import net.minecraft.inventory.InventoryBasic;

public class InventoryFrier extends InventoryBasic {
    TileEntityFrier counter;

    public InventoryFrier(final String par1Str, final boolean par2, final int par3, final TileEntityFrier tileEntityCounter)
    {
        super(par1Str, par2, par3);
        counter = tileEntityCounter;
    }

    @Override
    public void closeInventory()
    {
        if (counter.getBlockType() != null && counter.getBlockType() instanceof BlockFrier)
        {
            --counter.numUsingPlayers;
            counter.getWorldObj().addBlockEvent(counter.xCoord, counter.yCoord, counter.zCoord, counter.getBlockType(), 1, counter.numUsingPlayers);
        }
    }

    @Override
    public void openInventory()
    {
        if (counter.numUsingPlayers < 0)
        {
            counter.numUsingPlayers = 0;
        }

        ++counter.numUsingPlayers;
        counter.getWorldObj().addBlockEvent(counter.xCoord, counter.yCoord, counter.zCoord, counter.getBlockType(), 1, counter.numUsingPlayers);
    }

}
