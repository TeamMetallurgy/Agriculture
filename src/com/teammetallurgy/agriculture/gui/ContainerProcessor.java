package com.teammetallurgy.agriculture.gui;

import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerProcessor extends Container
{
	public ContainerProcessor(InventoryPlayer invPlayer, TileEntityProcessor processor)
	{
		this.addSlotToContainer(new Slot(processor.getInventory(), 0, 57, 32));
		this.addSlotToContainer(new Slot(processor.getInventory(), 1, 103, 32));
		
		int i;
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}
	
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

}
