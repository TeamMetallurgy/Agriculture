package com.teammetallurgy.agriculture.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

import com.teammetallurgy.agriculture.hunger.HungerSystem;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class PacketSyncHunger extends Packet
{
	private float hungerPoints;

	public PacketSyncHunger(float hungerPoints)
	{
		this.hungerPoints = hungerPoints;
	}

	@Override
	public int getPacketSize()
	{
		return Float.SIZE;
	}

	@Override
	public void processPacket(NetHandler nethandler)
	{
		System.out.println("recive packet");
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			final Minecraft client = FMLClientHandler.instance().getClient();

			HungerSystem.getInstance().addPoints(client.thePlayer, hungerPoints);
		}
	}

	@Override
	public void readPacketData(DataInput datainput) throws IOException
	{
		hungerPoints = datainput.readFloat();
	}

	@Override
	public void writePacketData(DataOutput dataoutput) throws IOException
	{
		dataoutput.writeFloat(hungerPoints);
	}

}
