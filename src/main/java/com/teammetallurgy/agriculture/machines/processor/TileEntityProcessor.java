package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.teammetallurgy.agriculture.machines.FuelMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;

public class TileEntityProcessor extends FuelMachineTileEntity {
    private int coolDown = 0;

    private final IInventory fuelSlot = new InventoryBasic("", false, 1);

    private final InventoryProcessor inventory = new InventoryProcessor("", false, 3);
    private int processingTime = 0;
    private ItemStack result;

    private boolean canProcess(final ItemStack stackInSlot, final ItemStack stackInSlot22)
    {
        if (result == null) { return false; }

        final ItemStack stackInSlot2 = inventory.getStackInSlot(2);

        final ItemStack result2 = ProcessorRecipes.getInstance().findMatchingRecipe(stackInSlot, stackInSlot22);

        if (result2 == null) { return false; }

        if (stackInSlot2 == null) { return true; }

        if (ItemStack.areItemStacksEqual(stackInSlot2, result2))
        {
            final ItemStack copy = stackInSlot2.copy();
            if (copy.stackSize++ >= inventory.getInventoryStackLimit())
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        return result2 != null;
    }

    public int getCoolDown()
    {
        return coolDown;
    }

    @Override
    public IInventory getFuelInventory()
    {
        return fuelSlot;
    }

    @Override
    public int getFuelSlot()
    {
        return 0;
    }

    public IInventory getInventory()
    {
        return inventory;
    }

    private int getProcessTime(final ItemStack stackInSlot)
    {
        return ProcessorRecipes.getProcessTime(stackInSlot);
    }

    public void process()
    {
        if (result != null)
        {
            final ItemStack stackInSlot = inventory.getStackInSlot(2);
            if (stackInSlot != null && ItemStack.areItemStacksEqual(stackInSlot, result))
            {
                stackInSlot.stackSize += result.stackSize;
            }
            else
            {
                inventory.setInventorySlotContents(2, result.copy());
            }

            result = null;
            currentItemBurnTime = -1;
            sendPacket();
        }
    }

    @Override
    public void readCustomNBT(final NBTTagCompound tag)
    {
        super.readCustomNBT(tag);
        coolDown = tag.getInteger("CoolDown");
        processingTime = tag.getInteger("ProcessingTime");

        final NBTTagList tagList = tag.getTagList("Items", 10);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            final NBTTagCompound base = (NBTTagCompound) tagList.getCompoundTagAt(i);
            final int slot = Integer.valueOf(base.getByte("Slot"));
            inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
        }
    }

    public void sendPacket()
    {
//        final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
//        final DataOutputStream dos = new DataOutputStream(bos);
//        try
//        {
//            dos.writeShort(1);
//            dos.writeInt(xCoord);
//            dos.writeInt(yCoord);
//            dos.writeInt(zCoord);
//            dos.writeByte(direction);
//            dos.writeInt(processingTime);
//            dos.writeInt(currentItemBurnTime);
//            dos.writeInt(fuelRemaining);
//            dos.writeInt(coolDown);
//
//            if (result == null)
//            {
//                dos.writeInt(0);
//            }
//            else
//            {
//                dos.writeInt(1);
//                dos.writeInt(result.getItem().itemID);
//                dos.writeInt(result.getItemDamage());
//                dos.writeInt(result.stackSize);
//            }
//
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

    public void setCoolDown(final int coolDown)
    {
        this.coolDown = coolDown;
    }

    public void setProcessingTime(final int processingTime)
    {
        this.processingTime = processingTime;
    }

    public void setResult(final ItemStack result)
    {
        this.result = result;
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (processingTime > 0)
        {
            --processingTime;
            --currentItemBurnTime;
            --fuelRemaining;
        }

        if (worldObj.isRemote) { return; }
        if (processingTime == 0)
        {
            if (currentItemBurnTime == 0)
            {
                process();
            }

            final ItemStack stackInSlot = inventory.getStackInSlot(0);
            final ItemStack stackInSlot2 = inventory.getStackInSlot(1);

            if (coolDown-- <= 0)
            {
                result = ProcessorRecipes.getInstance().findMatchingRecipe(stackInSlot, stackInSlot2);

                coolDown = processingTime = getProcessTime(result);

                if (processingTime > 0 && canProcess(stackInSlot, stackInSlot2) && fuelRemaining >= processingTime)
                {
                    if (stackInSlot2 != null)
                    {
                        currentItemBurnTime = processingTime;
                        --stackInSlot2.stackSize;
                        sendPacket();
                        if (stackInSlot2.stackSize == 0)
                        {
                            inventory.setInventorySlotContents(1, null);
                        }

                        if (stackInSlot != null)
                        {
                            --stackInSlot.stackSize;
                            if (stackInSlot.stackSize == 0)
                            {
                                inventory.setInventorySlotContents(0, null);
                            }
                        }
                    }
                    else
                    {
                        currentItemBurnTime = processingTime;
                        --stackInSlot.stackSize;
                        sendPacket();
                        if (stackInSlot.stackSize == 0)
                        {
                            inventory.setInventorySlotContents(0, null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void writeCustomNBT(final NBTTagCompound tag)
    {
        super.writeCustomNBT(tag);
        tag.setInteger("CoolDown", coolDown);
        tag.setInteger("ProcessingTime", processingTime);

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
}