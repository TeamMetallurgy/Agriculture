package com.teammetallurgy.agriculture.machines.processor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.machines.FuelMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TileEntityProcessor extends FuelMachineTileEntity
{
	private InventoryProcessor inventory = new InventoryProcessor("", false, 3);

	private IInventory fuelSlot = new InventoryBasic("", false, 1);

	private int sync = 0;
	private int processingTime = 0;
	private ItemStack result;
	private int coolDown = 0;

	public IInventory getInventory()
	{
		return inventory;
	}

	public int getFuelSlot()
	{
		return 0;
	}
	
	

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (this.processingTime > 0)
		{
			--this.processingTime;
			--this.currentItemBurnTime;
			--this.fuelRemaining;
		}

		if (this.worldObj.isRemote)
			return;
		if (this.processingTime == 0)
		{
			if (currentItemBurnTime == 0)
			{
				process();
			}

			ItemStack stackInSlot = inventory.getStackInSlot(0);
			ItemStack stackInSlot2 = inventory.getStackInSlot(1);

			if (coolDown-- <= 0)
			{
				this.result = ProcessorRecipes.getInstance().findMatchingRecipe(stackInSlot, stackInSlot2);

				this.coolDown = this.processingTime = getProcessTime(result);

				if (this.processingTime > 0 && canProcess(stackInSlot, stackInSlot2) && fuelRemaining >= processingTime)
				{
					if (stackInSlot2 != null)
					{
						this.currentItemBurnTime = processingTime;
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
					} else
					{
						this.currentItemBurnTime = processingTime;
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


	public void process()
	{
		if (result != null)
		{
			ItemStack stackInSlot = inventory.getStackInSlot(2);
			if (stackInSlot != null && stackInSlot.itemID == result.itemID && stackInSlot.getItemDamage() == result.getItemDamage())
			{
				stackInSlot.stackSize += result.stackSize;
			} else
			{
				inventory.setInventorySlotContents(2, result.copy());
			}

			result = null;
			this.currentItemBurnTime = -1;
			sendPacket();
		}
	}

	public int getCoolDown()
	{
		return coolDown;
	}

	public void setCoolDown(int coolDown)
	{
		this.coolDown = coolDown;
	}

	private boolean canProcess(ItemStack stackInSlot, ItemStack stackInSlot22)
	{
		if (result == null)
			return false;

		ItemStack stackInSlot2 = inventory.getStackInSlot(2);

		ItemStack result2 = ProcessorRecipes.getInstance().findMatchingRecipe(stackInSlot, stackInSlot22);

		if (result2 == null)
		{
			return false;
		}

		if (stackInSlot2 == null)
		{
			return true;
		}

		if (result2.itemID == stackInSlot2.itemID && result2.getItemDamage() == stackInSlot2.getItemDamage())
		{
			ItemStack copy = stackInSlot2.copy();
			if (copy.stackSize++ >= inventory.getInventoryStackLimit())
			{
				return false;
			} else
			{
				return true;
			}
		}

		return result2 != null;
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
			dos.writeInt(fuelRemaining);
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
	public void readCustomNBT(NBTTagCompound tag)
	{
		super.readCustomNBT(tag);
		this.coolDown = tag.getInteger("CoolDown");
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
		super.writeCustomNBT(tag);
		tag.setInteger("CoolDown", coolDown);
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

	@Override
	public IInventory getFuelInventory()
	{
		return fuelSlot;
	}
}