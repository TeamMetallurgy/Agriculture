package com.teammetallurgy.agriculture;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(final INetworkManager manager, final Packet250CustomPayload packet, final Player player)
    {
        final ByteArrayDataInput byteIn = ByteStreams.newDataInput(packet.data);
        final short packetID = byteIn.readShort();

        
        if (packetID == 256 && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            final float hunger = byteIn.readFloat();
            Agriculture.proxy.updateHunger(hunger);
        }
    }
}
