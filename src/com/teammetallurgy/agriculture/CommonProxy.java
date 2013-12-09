package com.teammetallurgy.agriculture;

import com.teammetallurgy.agriculture.factories.Factories;
import com.teammetallurgy.agriculture.gui.*;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.machines.IFuelSlot;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler
{

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
        return Factories.GUIFactory(ID, player, world, x, y, z, FMLCommonHandler.instance().getEffectiveSide());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
        return Factories.GUIFactory(ID, player, world, x, y, z, FMLCommonHandler.instance().getEffectiveSide());
	}

	public void registerEventHandlers()
	{
	}

	public void registerRenderers()
	{
	}

	public void updateHunger(float hunger)
	{
		// TODO Auto-generated method stub
		
	}

}
