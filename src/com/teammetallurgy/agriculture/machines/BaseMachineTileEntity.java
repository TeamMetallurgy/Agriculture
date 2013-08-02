package com.teammetallurgy.agriculture.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BaseMachineTileEntity extends TileEntity
{
	protected byte direction;

	public byte getDirection() 
	{
		return direction;
	}

	public void setDirection(byte direction) 
	{
		this.direction = direction;
	}

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
    	super.readFromNBT(tag);
    	
    	direction = tag.getByte("direction");
    }
	
    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
    	super.writeToNBT(tag);
    	
    	tag.setByte("direction", direction);
    }
}
