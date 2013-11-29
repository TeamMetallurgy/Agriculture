package com.teammetallurgy.agriculture.hunger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.WeakHashMap;

import com.teammetallurgy.agriculture.Agriculture;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HungerSystem {
	private static HungerSystem instance;
	private static Map<String, Integer> playerInstances = new WeakHashMap<String, Integer>();
	static final float MAXPOINTS = 200;
	static final int DECAYRATE = 5;

	public static HungerSystem getInstance() {
		if (instance == null) {
			instance = new HungerSystem();
		}
		return instance;
	}

	public void addPlayer(EntityPlayer player) {
		playerInstances.put(player.username, player.getFoodStats().getFoodLevel());
	}

	public void removePlayer(EntityPlayer player) {
		playerInstances.remove(player.username);
	}

	public void addPoints(EntityPlayer player, float points) {
		if(!playerInstances.containsKey(player.username)){
			addPlayer(player);
		}
		int currentPoints = playerInstances.get(player.username);
		currentPoints += points;
		if (currentPoints > MAXPOINTS) {
			currentPoints = (int) MAXPOINTS;
		}
		playerInstances.put(player.username, currentPoints);
		syncClientWithServer(player);
	}
	
	public void removePoints(EntityPlayer player, float points) {
		if(!playerInstances.containsKey(player.username)){
			addPlayer(player);
		}
		int currentPoints = playerInstances.get(player.username);
		currentPoints -= points;
		if (currentPoints < 0) {
			currentPoints = 0;
		}
		playerInstances.put(player.username, currentPoints);
		syncClientWithServer(player);
	}

	public float getPercentage(EntityPlayer player) {
		if(!playerInstances.containsKey(player.username)){
			addPlayer(player);
		}
		return playerInstances.get(player.username) / MAXPOINTS;
	}

	public void tick(EntityPlayer player) {
		removePoints(player, DECAYRATE);
	}

	public void applyInstanceBonuses(EntityPlayer player) {
		if (!player.worldObj.isRemote)
			if (getPercentage(player) >= .20f) {
				// if (!player.isPotionActive(Potion.digSpeed.id))
				{
					player.addPotionEffect(new PotionEffect(Potion.digSpeed.id,
							200, 1, true));
				}

				// if (!player.isPotionActive(Potion.moveSpeed.id))
				{
					player.addPotionEffect(new PotionEffect(
							Potion.moveSpeed.id, 200, 1, true));
				}
			}

	}

	public void syncClientWithServer(EntityPlayer player) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			// PacketDispatcher.sendPacketToPlayer(new
			// PacketSyncHunger(hungerPoints), (Player)player);

			final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
			final DataOutputStream dos = new DataOutputStream(bos);
			try {
				dos.writeShort(256);
				dos.writeFloat(playerInstances.get(player.username));
			} catch (final IOException e) {
				System.out.println(e);
			}

			final Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = Agriculture.MODID;
			packet.data = bos.toByteArray();
			packet.length = bos.size();

			if (packet != null) {
				PacketDispatcher.sendPacketToAllPlayers(packet);
				// PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
			}
		}
	}
}
