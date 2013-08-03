package com.teammetallurgy.agriculture.machines.processor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TileEntityProcessor extends BaseMachineTileEntity
{
	private InventoryProcessor inventory = new InventoryProcessor("", false, 2);
	private int sync = 0;
	private int processingTime;
	private int currentItemBurnTime = 0;
	private ItemStack result;
	private int coolDown = 0;

	public IInventory getInventory()
	{
		return inventory;
	}

	@Override
	public void updateEntity()
	{
		if (--sync <= 0)
		{
			sendPacket();
			sync = 80;
		}

		if (this.processingTime > 0)
		{
			--this.processingTime;
			this.currentItemBurnTime++;
		}

		if (this.processingTime == 0)
		{
			if (currentItemBurnTime >= 20)
			{
				process();
			}

			ItemStack stackInSlot = inventory.getStackInSlot(0);
			if (stackInSlot == null)
			{
				return;
			}

			if (canProcess(stackInSlot))
			{
				if (coolDown-- <= 0)
				{
					this.result = ProcessorRecipes.getResult(stackInSlot);

					this.coolDown = this.processingTime = getProcessTime(stackInSlot);

					if (this.processingTime > 0)
					{
						if (stackInSlot != null)
						{
							--stackInSlot.stackSize;

							if (stackInSlot.stackSize == 0)
							{
								inventory.setInventorySlotContents(0, null);
							}
						}
					}
				}
			}
		}
	}

	public void process()
	{
		if (result != null)
		{

			ItemStack stackInSlot = inventory.getStackInSlot(1);
			if (stackInSlot != null && stackInSlot.itemID == result.itemID && stackInSlot.getItemDamage() == result.getItemDamage())
			{
				stackInSlot.stackSize++;
			} else
			{
				inventory.setInventorySlotContents(1, result.copy());
			}

			result = null;
			this.currentItemBurnTime = 0;
		}
	}

	public int getCurrentItemBurnTime()
	{
		return currentItemBurnTime;
	}

	public int getCoolDown()
	{
		return coolDown;
	}

	public void setCoolDown(int coolDown)
	{
		this.coolDown = coolDown;
	}

	private boolean canProcess(ItemStack stackInSlot)
	{
		if (stackInSlot == null)
			return false;

		ItemStack stackInSlot2 = inventory.getStackInSlot(1);

		ItemStack result2 = ProcessorRecipes.getResult(stackInSlot);

		if (result2 == null)
		{
			return false;
		}

		if (result == null)
		{
			return true;
		}

		if (result2.itemID == stackInSlot2.itemID && result2.getItemDamage() == stackInSlot2.getItemDamage())
		{
			ItemStack copy = stackInSlot2.copy();
			if (copy.stackSize++ >= copy.getMaxStackSize())
			{
				return false;
			} else
			{
				return true;
			}
		}

		return result2 != null;
	}

	public void setCurrentItemBurnTime(int currentItemBurnTime)
	{
		this.currentItemBurnTime = currentItemBurnTime;
	}

	public void setProcessingTime(int processingTime)
	{
		this.processingTime = processingTime;
	}

	public void setResult(ItemStack result)
	{
		this.result = result;
	}

	private int getProcessTime(ItemStack stackInSlot)
	{
		return ProcessorRecipes.getProcessTime(stackInSlot);
	}

	public void sendPacket()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try
		{
			dos.writeShort(1);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeByte(direction);
			dos.writeInt(processingTime);
			dos.writeInt(currentItemBurnTime);
			dos.writeInt(coolDown);

			if (result == null)
			{
				dos.writeInt(0);
			} else
			{
				dos.writeInt(1);
				dos.writeInt(result.getItem().itemID);
				dos.writeInt(result.getItemDamage());
				dos.writeInt(result.stackSize);
			}

		} catch (IOException e)
		{
			System.out.println(e);
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = Agriculture.MODID;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;

		if (packet != null)
		{
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			{
				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16, worldObj.provider.dimensionId, packet);
			} else
			{
				PacketDispatcher.sendPacketToServer(packet);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.readCustomNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		this.writeCustomNBT(tag);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		this.writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		this.coolDown = tag.getInteger("CoolDown");
		this.currentItemBurnTime = tag.getInteger("BurnTime");
		this.processingTime = tag.getInteger("ProcessingTime");

		NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("CoolDown", coolDown);
		tag.setInteger("BurnTime", currentItemBurnTime);
		tag.setInteger("ProcessingTime", processingTime);

		NBTTagList itemListTag = new NBTTagList();
		for (int i = 0; i < this.inventory.getSizeInventory(); ++i)
		{
			if (this.inventory.getStackInSlot(i) != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("Slot", (byte) i);
				this.inventory.getStackInSlot(i).writeToNBT(itemTag);
				itemListTag.appendTag(itemTag);
			}
		}

		tag.setTag("Items", itemListTag);

	}
}
