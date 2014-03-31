package com.teammetallurgy.agriculture.machines.icebox;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.FreezerRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityIcebox extends BaseMachineTileEntity {
    public static int getItemBurnTime(final ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            Block block = Block.getBlockFromItem(par0ItemStack.getItem());
            if (block == Blocks.snow) { return 6400; }
            if (block == Blocks.ice) { return 1600; }

            if (par0ItemStack.getItem() == Items.snowball) { return 1600; }
        }

        return 0;
    }

    public float doorAngle;

    private int fuelHotness;
    private int fuelRemaining;
    private final InventoryIcebox inventory = new InventoryIcebox("", false, 13, this);
    private boolean isCooking = false;

    private int maxTemp = 1000;

    int numUsingPlayers;

    public float prevDoorAngle;
    int sync = 0;
    private int temp = maxTemp;

    private int[] timeInSlot = new int[inventory.getSizeInventory()];

    private void burnFuel()
    {
        final ItemStack fuelStack = inventory.getStackInSlot(0);

        final int fuelAmount = TileEntityIcebox.getItemBurnTime(fuelStack);
        if (fuelAmount > 0)
        {
            // TODO: add different "hotness" levels for different fuels
            fuelHotness = 1;

            fuelRemaining += fuelAmount;

            fuelStack.stackSize--;

            if (fuelStack.stackSize == 0)
            {
                inventory.setInventorySlotContents(0, fuelStack.getItem().getContainerItem(fuelStack));
            }
        }
    }

    @Override
    public Packet getDescriptionPacket()
    {
        final NBTTagCompound tag = new NBTTagCompound();
        writeCustomNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    public int getFuelRemaining()
    {
        return fuelRemaining;
    }

    public InventoryIcebox getInventory()
    {
        return inventory;
    }

    public int getMaxTemp()
    {
        return maxTemp;
    }

    public int getTemp()
    {
        return temp;
    }

    public boolean isCooking()
    {
        return isCooking;
    }

    @Override
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt)
    {
        readCustomNBT(pkt.func_148857_g());
    }

    @Override
    public void readCustomNBT(final NBTTagCompound tag)
    {
        temp = tag.getInteger("temp");
        maxTemp = tag.getInteger("maxTemp");
        fuelRemaining = tag.getInteger("fuelRemaining");
        fuelHotness = tag.getInteger("fuelHotness");

        timeInSlot = tag.getIntArray("timeInSlot");

        final NBTTagList tagList = tag.getTagList("Items", 10);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            final NBTTagCompound base = (NBTTagCompound) tagList.getCompoundTagAt(i);
            final int slot = Integer.valueOf(base.getByte("Slot"));
            inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
        }
    }

    @Override
    public void readFromNBT(final NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        readCustomNBT(tag);
    }

    @Override
    public boolean receiveClientEvent(final int id, final int value)
    {
        if (id == 1)
        {
            numUsingPlayers = value;
            return true;
        }
        else
        {
            return super.receiveClientEvent(id, value);
        }
    }

    public void sendPacket()
    {
//        final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
//        final DataOutputStream dos = new DataOutputStream(bos);
//        try
//        {
//            dos.writeShort(0);
//            dos.writeInt(xCoord);
//            dos.writeInt(yCoord);
//            dos.writeInt(zCoord);
//            dos.writeByte(direction);
//            dos.writeInt(fuelRemaining);
//            dos.writeInt(temp);
//            dos.writeInt(maxTemp);
//        }
//        catch (final IOException e)
//        {
//            System.out.println(e);
//        }
//
//        final Packet250CustomPayload packet = new Packet250CustomPayload();
//        packet.channel = Agriculture.MODID;
//        packet.data = bos.toByteArray();
//        packet.length = bos.size();
//        packet.isChunkDataPacket = true;
//
//        if (packet != null)
//        {
//            if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
//            {
//                PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
//            }
//            else
//            {
//                PacketDispatcher.sendPacketToServer(packet);
//            }
//        }
    }

    @Override
    public void updateEntity()
    {

        if (sync-- == 0)
        {
            sendPacket();
        }

        if (fuelRemaining > 0)
        {
            isCooking = true;
            fuelRemaining--;
            if (temp > 0)
            {
                temp += fuelHotness;
            }
            else if (temp <= maxTemp)
            {
                temp++;
            }

            if (!worldObj.isRemote)
            {
                for (int i = 0; i < inventory.getSizeInventory() - 1; i++)
                {
                    final ItemStack stack = inventory.getStackInSlot(i + 1);
                    if (stack != null)
                    {
                        timeInSlot[i]++;

                        final ItemStack result = FreezerRecipes.getInstance().findMatchingRecipe(stack, timeInSlot[i] * temp); // replace
                        // with
                        // actually
                        // heat
                        // records
                        if (result != null)
                        {
                            inventory.setInventorySlotContents(i + 1, result.copy());
                            timeInSlot[i] = 0;
                            sendPacket();
                        }
                    }
                    else
                    {
                        if (i < timeInSlot.length)
                        {
                            timeInSlot[i] = 0;
                        }
                    }
                }

            }
        }
        else
        {
            isCooking = false;
            burnFuel();
        }

        if (fuelRemaining <= 0)
        {
            if (temp > 0)
            {
                temp--;
            }
        }

        prevDoorAngle = doorAngle;
        if (numUsingPlayers == 0 && doorAngle > 0.0F || numUsingPlayers > 0 && doorAngle < 1.0F)
        {
            if (numUsingPlayers > 0)
            {
                doorAngle += 0.1;
            }
            else
            {
                doorAngle -= 0.1;
            }

            if (doorAngle > 1.0F)
            {
                doorAngle = 1.0F;
            }

            if (doorAngle < 0.0F)
            {
                doorAngle = 0.0F;
            }
        }
    }

    @Override
    public void writeCustomNBT(final NBTTagCompound tag)
    {
        tag.setInteger("temp", temp);
        tag.setInteger("maxTemp", maxTemp);
        tag.setInteger("fuelRemaining", fuelRemaining);
        tag.setInteger("fuelHotness", fuelHotness);

        tag.setIntArray("timeInSlot", timeInSlot);

        final NBTTagList itemListTag = new NBTTagList();
        for (int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            if (inventory.getStackInSlot(i) != null)
            {
                final NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                inventory.getStackInSlot(i).writeToNBT(itemTag);
                itemListTag.appendTag(itemTag);
            }
        }

        tag.setTag("Items", itemListTag);
    }

    @Override
    public void writeToNBT(final NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        writeCustomNBT(tag);
    }
}
