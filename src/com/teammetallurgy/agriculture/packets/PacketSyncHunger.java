package com.teammetallurgy.agriculture.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.teammetallurgy.agriculture.hunger.HungerSystem;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;

public class PacketSyncHunger extends Packet
{
    private float hungerPoints;
    
    
    public PacketSyncHunger(float hungerPoints)
    {
        this.hungerPoints = hungerPoints;
    }

    @Override
    public void readPacketData(DataInput datainput) throws IOException
    {
        this.hungerPoints = datainput.readFloat();
    }

    @Override
    public void writePacketData(DataOutput dataoutput) throws IOException
    {
        dataoutput.writeFloat(this.hungerPoints);
    }

    @Override
    public void processPacket(NetHandler nethandler)
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            Minecraft client = FMLClientHandler.instance().getClient();
            
            new HungerSystem(client.thePlayer, this.hungerPoints);
        }
    }

    @Override
    public int getPacketSize()
    {
        return Float.SIZE;
    }

}
