package com.teammetallurgy.agriculture.machines.oven;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.food.ICookable;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.OvenRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileEntityOven extends BaseMachineTileEntity {
    private static int fuelSlot = 0;

    private static int numOvenSlots = 16;
    private static int ovenInventorySlotStart = 1;

    public float doorAngle;

    private int fuelHotness;
    private int fuelRemaining;
    private final InventoryOven inventoryOven = new InventoryOven("", false, 20, this);
    private boolean isCooking = false;

    private int maxTemp = 1000;

    int numUsingPlayers;

    public float prevDoorAngle;
    int sync = 0;
    private int temp;

    private int[] timeInOvenSlot = new int[TileEntityOven.numOvenSlots];

    private void burnFuel()
    {
        final ItemStack fuelStack = inventoryOven.getStackInSlot(TileEntityOven.fuelSlot);

        final int fuelAmount = TileEntityFurnace.getItemBurnTime(fuelStack);
        if (fuelAmount > 0)
        {
            // TODO: add different "hotness" levels for different fuels
            fuelHotness = 1;

            fuelRemaining += fuelAmount;

            fuelStack.stackSize--;

            if (fuelStack.stackSize == 0)
            {
                inventoryOven.setInventorySlotContents(TileEntityOven.fuelSlot, fuelStack.getItem().getContainerItem(fuelStack));
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

    public InventoryOven getInventoryOven()
    {
        return inventoryOven;
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

        timeInOvenSlot = tag.getIntArray("timeInOvenSlot");

        final NBTTagList tagList = tag.getTagList("Items", 10);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            final NBTTagCompound base = (NBTTagCompound) tagList.getCompoundTagAt(i);
            final int slot = Integer.valueOf(base.getByte("Slot"));
            inventoryOven.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
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

    public void setFuelRemaining(final int fuelRemaining)
    {
        this.fuelRemaining = fuelRemaining;
    }

    public void setMaxTemp(final int maxTemp)
    {
        this.maxTemp = maxTemp;
    }

    public void setTemp(final int temp)
    {
        this.temp = temp;
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
            if (temp < maxTemp)
            {
                temp += fuelHotness;
            }
            else if (temp > maxTemp)
            {
                temp--;
            }

            if (!worldObj.isRemote)
            {
                for (int i = 0; i < TileEntityOven.numOvenSlots; i++)
                {
                    final ItemStack stack = inventoryOven.getStackInSlot(TileEntityOven.ovenInventorySlotStart + i);
                    if (stack != null)
                    {
                        timeInOvenSlot[i]++;
                        if (stack.getItem() instanceof ICookable)
                        {
                            ((ICookable) stack.getItem()).heatUpdate(stack, temp, timeInOvenSlot[i]);
                        }

                        final ItemStack result = OvenRecipes.getInstance().findMatchingRecipe(stack, timeInOvenSlot[i] * temp); // replace
                        // with
                        // actually
                        // heat
                        // records
                        if (result != null)
                        {
                            inventoryOven.setInventorySlotContents(TileEntityOven.ovenInventorySlotStart + i, result.copy());
                            timeInOvenSlot[i] = 0;
                            sendPacket();
                        }
                    }
                    else
                    {
                        if (i < timeInOvenSlot.length)
                        {
                            timeInOvenSlot[i] = 0;
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

        tag.setIntArray("timeInOvenSlot", timeInOvenSlot);

        final NBTTagList itemListTag = new NBTTagList();
        for (int i = 0; i < inventoryOven.getSizeInventory(); ++i)
        {
            if (inventoryOven.getStackInSlot(i) != null)
            {
                final NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("Slot", (byte) i);
                inventoryOven.getStackInSlot(i).writeToNBT(itemTag);
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
