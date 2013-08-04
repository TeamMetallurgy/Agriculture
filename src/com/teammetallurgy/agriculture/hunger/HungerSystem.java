package com.teammetallurgy.agriculture.hunger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.packets.PacketSyncHunger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class HungerSystem
{
    static Map<String, HungerSystem> playerInstances = new WeakHashMap<String, HungerSystem>();

    static final float MAXPOINTS = 200;
    float hungerPoints = 0;
    static final int DECAYRATE = 5;

    final EntityPlayer player;

    public HungerSystem(EntityPlayer player)
    {
        this(player, 0f);
    }
    
    public HungerSystem(EntityPlayer player, float points) 
    {
        this.player = player;
        this.hungerPoints = points;
        
        playerInstances.put(player.username, this);
    }

    public void tick()
    {
        removePoints(DECAYRATE);
    }

    public static void tick(EntityPlayer player)
    {
        getInstance(player).tick();
    }

    public static void addPoints(EntityPlayer player, float points)
    {
        getInstance(player).addPoints(points);
    }

    public void addPoints(float points)
    {
        if (points + this.hungerPoints <= MAXPOINTS)
        {
            this.hungerPoints += points;
        }
        else
        {
            this.hungerPoints = MAXPOINTS;
        }
//        System.out.println("points " + hungerPoints);
        syncClientWithServer(this.player);
    }

    public static void removePoints(EntityPlayer player, float points)
    {
        getInstance(player).removePoints(points);
    }

    public void removePoints(float points)
    {
        if (this.hungerPoints - points >= 0)
        {
            this.hungerPoints -= points;
        }
        else
        {
            this.hungerPoints = 0;
        }
//        System.out.println(hungerPoints);
        syncClientWithServer(this.player);
    }

    public static HungerSystem getInstance(EntityPlayer player)
    {
        HungerSystem instance = null;

        if (playerInstances.containsKey(player.username))
        {
            instance = playerInstances.get(player.username);
        }
        else
        {
            instance = new HungerSystem(player);
            playerInstances.put(player.username, instance);
        }

        return instance;
    }

    public static float getPercentage(EntityPlayer player)
    {
        return getInstance(player).getPercentage();
    }

    public float getPercentage()
    {
        return hungerPoints / MAXPOINTS;
    }

    public static void applyBonuses(EntityPlayerMP player)
    {
    	if(!player.worldObj.isRemote)
    		getInstance(player).applyInstanceBonuses(player);
    }

    public void applyInstanceBonuses(EntityPlayerMP playerMP)
    {
        if (getPercentage() >= .20f && playerMP instanceof EntityPlayerMP)
        {
            //if (!player.isPotionActive(Potion.digSpeed.id))
            {
            	playerMP.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 200, 1, true));
            }

            //if (!player.isPotionActive(Potion.moveSpeed.id))
            {
            	playerMP.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, 1, true));
            }
        }
    }

    public void syncClientWithServer(EntityPlayer player)
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            //PacketDispatcher.sendPacketToPlayer(new PacketSyncHunger(hungerPoints), (Player)player);
        
	        ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
			DataOutputStream dos = new DataOutputStream(bos);
			try
			{
				dos.writeShort(256);
				dos.writeFloat(hungerPoints);
			} catch (IOException e)
			{
				System.out.println(e);
			}
	
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = Agriculture.MODID;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
	
			if (packet != null)
			{
				PacketDispatcher.sendPacketToAllPlayers(packet);
				//PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
			}
        }
    }
}
