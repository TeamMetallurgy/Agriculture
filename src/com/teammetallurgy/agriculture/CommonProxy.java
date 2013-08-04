package com.teammetallurgy.agriculture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.gui.ContainerBrewer;
import com.teammetallurgy.agriculture.gui.ContainerCabinet;
import com.teammetallurgy.agriculture.gui.ContainerCounter;
import com.teammetallurgy.agriculture.gui.ContainerFuel;
import com.teammetallurgy.agriculture.gui.ContainerOven;
import com.teammetallurgy.agriculture.gui.ContainerProcessor;
import com.teammetallurgy.agriculture.gui.GUIBrewer;
import com.teammetallurgy.agriculture.gui.GUICabinet;
import com.teammetallurgy.agriculture.gui.GUICounter;
import com.teammetallurgy.agriculture.gui.GUIFuelSlot;
import com.teammetallurgy.agriculture.gui.GUIOven;
import com.teammetallurgy.agriculture.gui.GUIProcessor;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.machines.IFuelSlot;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch (ID)
		{
		case GUIIds.OVEN:

			if (te != null && te instanceof TileEntityOven)
			{
				return new ContainerOven(player.inventory, (TileEntityOven) te);
			}
		case GUIIds.CABINET:
			if (te != null && te instanceof TileEntityCounter)
			{
				return new ContainerCabinet(player.inventory, (TileEntityCounter) te);
			}
		case GUIIds.PROCESSOR:
			if (te != null && te instanceof TileEntityProcessor)
			{
				return new ContainerProcessor(player.inventory, (TileEntityProcessor) te);
			}
		case GUIIds.COUNTER:
			if (te != null && te instanceof BaseMachineTileEntity)
			{
				return new ContainerCounter(player.inventory, ((BaseMachineTileEntity) te).getInventoryCounter());
			}
		case GUIIds.FUEL:
			if (te != null && te instanceof TileEntityProcessor)
			{
				return new ContainerFuel(player.inventory, ((TileEntityProcessor) te).getFuelSlot(), (IFuelSlot)te);
			}
		case GUIIds.BREWER:
			if (te != null && te instanceof TileEntityBrewer)
			{
				return new ContainerBrewer(player.inventory, (TileEntityBrewer)te);
			}
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch (ID)
		{
		case GUIIds.OVEN:
			if (te != null && te instanceof TileEntityOven)
			{
				return new GUIOven(new ContainerOven(player.inventory, (TileEntityOven) te));
			}
		case GUIIds.CABINET:
			if (te != null && te instanceof TileEntityCounter)
			{
				return new GUICabinet(new ContainerCabinet(player.inventory, (TileEntityCounter) te));
			}
		case GUIIds.PROCESSOR:
			if (te != null && te instanceof TileEntityProcessor)
			{
				return new GUIProcessor(new ContainerProcessor(player.inventory, (TileEntityProcessor) te));
			}
		case GUIIds.COUNTER:

			if (te != null && te instanceof BaseMachineTileEntity)
			{
				return new GUICounter(new ContainerCounter(player.inventory, ((BaseMachineTileEntity) te).getInventoryCounter()));
			}
		case GUIIds.FUEL:
			if (te != null && te instanceof TileEntityProcessor)
			{
				return new GUIFuelSlot(new ContainerFuel(player.inventory, ((TileEntityProcessor) te).getFuelSlot(), (IFuelSlot)te));
			}
		case GUIIds.BREWER:
			if (te != null && te instanceof TileEntityBrewer)
			{
				return new GUIBrewer(new ContainerBrewer(player.inventory, (TileEntityBrewer)te));
			}
		default:
			return null;
		}
	}

	public void registerEventHandlers()
	{
	}

	public void registerRenderers()
	{
	}

}
