package com.teammetallurgy.agriculture.machines.icebox;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TileEntityIcebox extends BaseMachineTileEntity
{
	public static int getItemBurnTime(ItemStack par0ItemStack)
	{
		if (par0ItemStack == null)
		{
			return 0;
		} else
		{
			final int i = par0ItemStack.getItem().itemID;
			par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
			{
				final Block block = Block.blocksList[i];

				if (block == Block.snow)
				{
					return 6400;
				}
				if (block == Block.ice)
				{
					return 1600;
				}
			}

			if (i == Item.snowball.itemID)
			{
				return 1600;
			}
		}

		return 0;
	}

	private final InventoryIcebox inventory = new InventoryIcebox("", false, 13, this);

	private int[] timeInSlot = new int[inventory.getSizeInventory()];
	private int maxTemp = 1000;
	private int temp = maxTemp;
	private int fuelRemaining;

	private int fuelHotness;

	private boolean isCooking = false;

	int sync = 0;
	int numUsingPlayers;
	public float doorAngle;

	public float prevDoorAngle;

	private void burnFuel()
	{
		final ItemStack fuelStack = inventory.getStackInSlot(0);

		final int fuelAmount = getItemBurnTime(fuelStack);
		if (fuelAmount > 0)
		{
			// TODO: add different "hotness" levels for different fuels
			fuelHotness = 1;

			fuelRemaining += fuelAmount;

			fuelStack.stackSize--;

			if (fuelStack.stackSize == 0)
			{
				inventory.setInventorySlotContents(0, fuelStack.getItem().getContainerItemStack(fuelStack));
			}
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
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
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readCustomNBT(pkt.data);
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		temp = tag.getInteger("temp");
		maxTemp = tag.getInteger("maxTemp");
		fuelRemaining = tag.getInteger("fuelRemaining");
		fuelHotness = tag.getInteger("fuelHotness");

		timeInSlot = tag.getIntArray("timeInSlot");

		final NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			final NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			final int slot = Integer.valueOf(base.getByte("Slot"));
			inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}

	@Override
	public boolean receiveClientEvent(int id, int value)
	{
		if (id == 1)
		{
			numUsingPlayers = value;
			return true;
		} else
		{
			return super.receiveClientEvent(id, value);
		}
	}

	public void sendPacket()
	{
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		final DataOutputStream dos = new DataOutputStream(bos);
		try
		{
			dos.writeShort(0);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeByte(direction);
			dos.writeInt(fuelRemaining);
			dos.writeInt(temp);
			dos.writeInt(maxTemp);
		} catch (final IOException e)
		{
			System.out.println(e);
		}

		final Packet250CustomPayload packet = new Packet250CustomPayload();
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
			} else if (temp <= maxTemp)
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

						final ItemStack result = IceboxRecipes.getResult(stack, timeInSlot[i] * temp); // replace
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
					} else
					{
						if (i < timeInSlot.length)
						{
							timeInSlot[i] = 0;
						}
					}
				}

			}
		} else
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
			} else
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
	public void writeCustomNBT(NBTTagCompound tag)
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
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}
}
