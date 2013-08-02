package com.teammetallurgy.agriculture;

import com.teammetallurgy.agriculture.gui.ContainerCabinet;
import com.teammetallurgy.agriculture.gui.ContainerOven;
import com.teammetallurgy.agriculture.gui.ContainerProcessor;
import com.teammetallurgy.agriculture.gui.GUICabinet;
import com.teammetallurgy.agriculture.gui.GUIOven;
import com.teammetallurgy.agriculture.gui.GUIProcessor;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	switch(ID) {
    	case GUIIds.OVEN:
    		TileEntity te = world.getBlockTileEntity(x, y, z);
    		
    		if(te != null && te instanceof TileEntityOven) {
    			return new ContainerOven(player.inventory, (TileEntityOven) te); 
    		}
    	case GUIIds.CABINET:
    		TileEntity te2 = world.getBlockTileEntity(x, y, z);
    		
    		if(te2 != null && te2 instanceof TileEntityCounter) {
    			return new ContainerCabinet(player.inventory, (TileEntityCounter) te2); 
    		}
    	case GUIIds.PROCESSOR:
    		TileEntity te3 = world.getBlockTileEntity(x, y, z);
    		
    		if(te3 != null && te3 instanceof TileEntityProcessor) {
    			return new ContainerProcessor(player.inventory, (TileEntityProcessor) te3); 
    		}
    	default:
    		return null;
    	}
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
    	switch(ID) {
    	case GUIIds.OVEN:
    		TileEntity te = world.getBlockTileEntity(x, y, z);
    		
    		if(te != null && te instanceof TileEntityOven) {
    			return new GUIOven(new ContainerOven(player.inventory, (TileEntityOven) te)); 
    		}
    	case GUIIds.CABINET:
    		TileEntity te2 = world.getBlockTileEntity(x, y, z);
    		
    		if(te2 != null && te2 instanceof TileEntityCounter) {
    			return new GUICabinet(new ContainerCabinet(player.inventory, (TileEntityCounter) te2)); 
    		}
    	case GUIIds.PROCESSOR:
    		TileEntity te3 = world.getBlockTileEntity(x, y, z);
    		
    		if(te3 != null && te3 instanceof TileEntityProcessor) {
    			return new GUIProcessor(new ContainerProcessor(player.inventory, (TileEntityProcessor) te3)); 
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
