package com.teammetallurgy.agriculture.hunger;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HungerSystem {
    static final int DECAYRATE = 5;

    static final float MAXPOINTS = 200;

    static Map<String, HungerSystem> playerInstances = new WeakHashMap<String, HungerSystem>();

    public static void addPoints(final EntityPlayer player, final float points)
    {
        HungerSystem.getInstance(player).addPoints(points);
    }

    public static void applyBonuses(final EntityPlayerMP player)
    {
        if (!player.worldObj.isRemote)
        {
            HungerSystem.getInstance(player).applyInstanceBonuses(player);
        }
    }

    public static HungerSystem getInstance(final EntityPlayer player)
    {
        HungerSystem instance = null;

//        if (HungerSystem.playerInstances.containsKey(player.username))
//        {
//            instance = HungerSystem.playerInstances.get(player.username);
//        }
//        else
//        {
//            instance = new HungerSystem(player);
//            HungerSystem.playerInstances.put(player.username, instance);
//        }

        return instance;
    }

    public static float getPercentage(final EntityPlayer player)
    {
        return HungerSystem.getInstance(player).getPercentage();
    }

    public static void removePoints(final EntityPlayer player, final float points)
    {
        HungerSystem.getInstance(player).removePoints(points);
    }

    public static void tick(final EntityPlayer player)
    {
        HungerSystem.getInstance(player).tick();
    }

    float hungerPoints = 0;

    final EntityPlayer player;

    public HungerSystem(final EntityPlayer player)
    {
        this(player, 0f);
    }

    public HungerSystem(final EntityPlayer player, final float points)
    {
        this.player = player;
        hungerPoints = points;

//        HungerSystem.playerInstances.put(player.username, this);
    }

    public void addPoints(final float points)
    {
        if (points + hungerPoints <= HungerSystem.MAXPOINTS)
        {
            hungerPoints += points;
        }
        else
        {
            hungerPoints = HungerSystem.MAXPOINTS;
        }
        // System.out.println("points " + hungerPoints);
        syncClientWithServer(player);
    }

    public void applyInstanceBonuses(final EntityPlayer playerMP)
    {
        if (this.getPercentage() >= .20f)
        {
            // if (!player.isPotionActive(Potion.digSpeed.id))
            {
                playerMP.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 200, 1, true));
            }

            // if (!player.isPotionActive(Potion.moveSpeed.id))
            {
                playerMP.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200, 1, true));
            }
        }
    }

    public float getPercentage()
    {
        return hungerPoints / HungerSystem.MAXPOINTS;
    }

    public void removePoints(final float points)
    {
        if (hungerPoints - points >= 0)
        {
            hungerPoints -= points;
        }
        else
        {
            hungerPoints = 0;
        }
        // System.out.println(hungerPoints);
        syncClientWithServer(player);
    }

    public void syncClientWithServer(final EntityPlayer player)
    {
//        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
//        {
//            // PacketDispatcher.sendPacketToPlayer(new
//            // PacketSyncHunger(hungerPoints), (Player)player);
//
//            final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
//            final DataOutputStream dos = new DataOutputStream(bos);
//            try
//            {
//                dos.writeShort(256);
//                dos.writeFloat(hungerPoints);
//            }
//            catch (final IOException e)
//            {
//                System.out.println(e);
//            }
//
//            final Packet250CustomPayload packet = new Packet250CustomPayload();
//            packet.channel = Agriculture.MODID;
//            packet.data = bos.toByteArray();
//            packet.length = bos.size();
//
//            if (packet != null)
//            {
//                PacketDispatcher.sendPacketToAllPlayers(packet);
//                // PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
//            }
//        }
    }

    public void tick()
    {
        this.removePoints(HungerSystem.DECAYRATE);
    }
}
