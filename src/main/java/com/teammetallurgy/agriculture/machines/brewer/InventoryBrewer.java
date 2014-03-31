package com.teammetallurgy.agriculture.machines.brewer;

import net.minecraft.inventory.InventoryBasic;

public class InventoryBrewer extends InventoryBasic {

    TileEntityBrewer counter;

    public InventoryBrewer(final String par1Str, final boolean par2, final int par3, final TileEntityBrewer tileEntityCounter)
    {
        super(par1Str, par2, par3);
        counter = tileEntityCounter;
    }

    @Override
    public void closeInventory()
    {
        if (counter.getBlockType() != null && counter.getBlockType() instanceof BlockBrewer)
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
