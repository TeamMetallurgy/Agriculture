package com.teammetallurgy.agriculture;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.teammetallurgy.agriculture.hunger.HungerSystem;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		ByteArrayDataInput byteIn = ByteStreams.newDataInput(packet.data);
		short packetID = byteIn.readShort();

		if (packetID == 0)
		{
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			byte direction = byteIn.readByte();
			int fuelRemaining = byteIn.readInt();
			int temp = byteIn.readInt();
			int maxTemp = byteIn.readInt();

			TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

			if (te instanceof TileEntityOven)
			{
				TileEntityOven oven = (TileEntityOven) te;
				oven.setTemp(temp);
				oven.setMaxTemp(maxTemp);
				oven.setFuelRemaining(fuelRemaining);
			}
		} 
		else if (packetID == 1)
		{
			int x = byteIn.readInt();
			int y = byteIn.readInt();
			int z = byteIn.readInt();
			byte direction = byteIn.readByte();
			int processingTime = byteIn.readInt();
			int currentItemBurnTime = byteIn.readInt();
			int fuelRemaining = byteIn.readInt();
			int coolDown = byteIn.readInt();

			boolean item = byteIn.readInt() == 1;

			ItemStack result = null;
			if (item)
			{
				int itemId = byteIn.readInt();
				int itemDamage = byteIn.readInt();
				int stackSize = byteIn.readInt();
				result = new ItemStack(itemId, stackSize, itemDamage);
			}

			TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

			if (te instanceof TileEntityProcessor)
			{
				TileEntityProcessor oven = (TileEntityProcessor) te;
				oven.setProcessingTime(processingTime);
				oven.setCurrentItemBurnTime(currentItemBurnTime);
				oven.setResult(result);
				oven.setFuelRemaining(fuelRemaining);
				oven.setCoolDown(coolDown);
			}
		}
		else if(packetID == 256)
		{
			float hunger = byteIn.readFloat();
			
            Minecraft client = FMLClientHandler.instance().getClient();
            new HungerSystem(client.thePlayer, hunger);
		}
	}
}
