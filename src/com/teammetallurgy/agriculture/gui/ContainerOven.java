package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

public class ContainerOven extends Container
{
	private TileEntityOven oven;

	public ContainerOven(InventoryPlayer invePlayer, TileEntityOven oven)
	{
		this.oven = oven;
		oven.openChest();
		
		int i;

		//Fuel Slot
		this.addSlotToContainer(new Slot(oven, 0, 8, 34));

		for (int x = 0; x < 4; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				//Input slot ??
				this.addSlotToContainer(new Slot(oven, (x + 1) + (y * 4) , 74 + x * 19, 6 + y * 19));
			}
		}
		System.out.println("test1");
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(invePlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		System.out.println("test2");
		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(invePlayer, i, 8 + i * 18, 142));
		}
		System.out.println("test3");
		
	}
	
    public void onContainerClosed(EntityPlayer entityplayer)
    {
        super.onContainerClosed(entityplayer);
    	oven.closeChest();
    }
	
	public TileEntityOven getOven()
	{
		return oven;
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

}
