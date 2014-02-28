package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgy;
import com.teammetallurgy.metallurgycore.machines.ContainerMetallurgyMachine;

public class ContainerProcessor extends ContainerMetallurgyMachine
{
    private final TileEntityProcessor processor;

    public ContainerProcessor(final InventoryPlayer invPlayer, final TileEntityProcessor processor)
    {
        super(0, 3, processor);
        this.processor = processor;

        addSlotToContainer(new Slot(processor, 0, 0, 0));
        addSlotToContainer(new Slot(processor, 1, 57, 32));
        addSlotToContainer(new Slot(processor, 2, 21, 32));
        addSlotToContainer(new Slot(processor, 3, 103, 32));

        addPlayersInventoryToContainer(invPlayer, 8, 84);
    }

    public TileEntityProcessor getProcessor()
    {
        return processor;
    }
}
