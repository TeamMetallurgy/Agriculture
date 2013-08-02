package com.teammetallurgy.agriculture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        ByteArrayDataInput byteIn = ByteStreams.newDataInput(packet.data);
        short packetID = byteIn.readShort();

        if(packetID == 0)
        {
        	int x = byteIn.readInt();
        	int y = byteIn.readInt();
        	int z = byteIn.readInt();
        	byte direction = byteIn.readByte();
        	int fuelRemaining = byteIn.readInt();
        	int temp = byteIn.readInt();
        	int maxTemp = byteIn.readInt();
        	
        	TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity(x, y, z);
        	
        	if(te instanceof TileEntityOven)
        	{
        		TileEntityOven oven = (TileEntityOven)te;
        		oven.setTemp(temp);
        		oven.setMaxTemp(maxTemp);
        		oven.setFuelRemaining(fuelRemaining);
        	}
        }
    }
}
