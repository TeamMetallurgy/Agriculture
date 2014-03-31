package com.teammetallurgy.agriculture.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class BaseMachineTileEntity extends TileEntity {
    protected byte direction;

    private final InventoryCounter inventoryCounter = new InventoryCounter("", false, 20);

    @Override
    public Packet getDescriptionPacket()
    {
        final NBTTagCompound tag = new NBTTagCompound();
        writeCustomNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    public byte getDirection()
    {
        return direction;
    }

    public IInventory getInventoryCounter()
    {
        return inventoryCounter;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
    	readCustomNBT(pkt.func_148857_g());
    }

    public void readCustomNBT(final NBTTagCompound tag)
    {
        final NBTTagList tagList = tag.getTagList("ItemsCounter", 10);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            final NBTTagCompound base = (NBTTagCompound) tagList.getCompoundTagAt(i);
            final int slot = Integer.valueOf(base.getByte("Slot"));
            inventoryCounter.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
        }

        direction = tag.getByte("direction");
    }

    @Override
    public void readFromNBT(final NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        readCustomNBT(tag);
    }

    public void setDirection(final byte direction)
    {
        this.direction = direction;
    }

    public void writeCustomNBT(final NBTTagCompound tag)
    {
        final NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < inventoryCounter.getSizeInventory(); ++i)
        {
            if (inventoryCounter.getStackInSlot(i) != null)
            {
                final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                inventoryCounter.getStackInSlot(i).writeToNBT(nbttagcompound1);
                nbtTagList.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("ItemsCounter", nbtTagList);

        tag.setByte("direction", direction);
    }

    @Override
    public void writeToNBT(final NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        writeCustomNBT(tag);
    }

}
