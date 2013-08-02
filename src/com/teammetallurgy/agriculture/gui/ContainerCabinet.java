package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;

public class ContainerCabinet extends Container
{

	private TileEntityCounter cabinet;

	public ContainerCabinet(InventoryPlayer invPlayer, TileEntityCounter cabinet)
	{
		this.cabinet = cabinet;

		int i;

		for (i = 0; i < 4; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(cabinet.getCabinet(), i + j * 4, 8 + 18 * i, 14 + 18 * j));
			}
		}
		
		for (i = 0; i < 4; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(cabinet.getCabinet(), (i + j * 4) + 12, 98 + 18 * i, 14 + 18 * j));
			}
		}

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
