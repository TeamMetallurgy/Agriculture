package com.teammetallurgy.agriculture.machines.oven;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.food.ICookable;
import com.teammetallurgy.agriculture.gui.OvenRecipes;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class TileEntityOven extends BaseMachineTileEntity
{
	private InventoryOven inventoryOven = new InventoryOven("", false, 20, this);

	private static int fuelSlot = 0;
	private static int numOvenSlots = 16;
	private static int ovenInventorySlotStart = 1;
	private static int ovenInventorySlotEnd = ovenInventorySlotStart + numOvenSlots - 1;
	private static int ovenRacksStart = 17;

	private int[] timeInOvenSlot = new int[numOvenSlots];

	private int temp;
	private int maxTemp = 1000;
	private int fuelRemaining;
	private int fuelHotness;

	private boolean isCooking = false;

	int sync = 0;

	int numUsingPlayers;
	public float doorAngle;
	public float prevDoorAngle;

	public int getTemp()
	{
		return temp;
	}

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
			} else if (temp > maxTemp)
			{
				temp--;
			}

			if (!worldObj.isRemote)
			{
				for (int i = 0; i < numOvenSlots; i++)
				{
					ItemStack stack = inventoryOven.getStackInSlot(ovenInventorySlotStart + i);
					if (stack != null)
					{
						timeInOvenSlot[i]++;
						if (stack.getItem() instanceof ICookable)
							((ICookable) stack.getItem()).heatUpdate(stack, temp, timeInOvenSlot[i]);

						ItemStack result = OvenRecipes.getResult(stack, timeInOvenSlot[i] * temp); // replace
																									// with
																									// actually
																									// heat
																									// records
						if (result != null)
						{
							inventoryOven.setInventorySlotContents(ovenInventorySlotStart + i, result.copy());
							sendPacket();
						}
					} else
					{
						if (i < timeInOvenSlot.length)
							timeInOvenSlot[i] = 0;
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
				temp--;
		}

		prevDoorAngle = doorAngle;
		if (this.numUsingPlayers == 0 && doorAngle > 0.0F || this.numUsingPlayers > 0 && doorAngle < 1.0F)
		{
			if (this.numUsingPlayers > 0)
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

	public int getFuelRemaining()
	{
		return fuelRemaining;
	}

	public int getMaxTemp()
	{
		return maxTemp;
	}

	public boolean isCooking()
	{
		return isCooking;
	}

	public InventoryOven getInventoryOven()
	{
		return inventoryOven;
	}

	private void burnFuel()
	{
		ItemStack fuelStack = inventoryOven.getStackInSlot(fuelSlot);

		int fuelAmount = getItemBurnTime(fuelStack);
		if (fuelAmount > 0)
		{
			// TODO: add different "hotness" levels for different fuels
			fuelHotness = 1;

			fuelRemaining += fuelAmount;

			fuelStack.stackSize--;

			if (fuelStack.stackSize == 0)
			{
				inventoryOven.setInventorySlotContents(fuelSlot, fuelStack.getItem().getContainerItemStack(fuelStack));
			}
		}

	}

	public static int getItemBurnTime(ItemStack par0ItemStack)
	{
		if (par0ItemStack == null)
		{
			return 0;
		} else
		{
			int i = par0ItemStack.getItem().itemID;
			Item item = par0ItemStack.getItem();

			if (par0ItemStack.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
			{
				Block block = Block.blocksList[i];

				if (block == Block.woodSingleSlab)
				{
					return 150;
				}

				if (block.blockMaterial == Material.wood)
				{
					return 300;
				}

				if (block == Block.field_111034_cE)
				{
					return 16000;
				}
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD"))
				return 200;
			if (i == Item.stick.itemID)
				return 100;
			if (i == Item.coal.itemID)
				return 1600;
			if (i == Item.bucketLava.itemID)
				return 20000;
			if (i == Block.sapling.blockID)
				return 100;
			if (i == Item.blazeRod.itemID)
				return 2400;
			return GameRegistry.getFuelValue(par0ItemStack);
		}
	}

	private int getTempIncreaseForFuel()
	{
		return 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{
		tag.setInteger("temp", temp);
		tag.setInteger("maxTemp", maxTemp);
		tag.setInteger("fuelRemaining", fuelRemaining);
		tag.setInteger("fuelHotness", fuelHotness);

		tag.setIntArray("timeInOvenSlot", timeInOvenSlot);

		NBTTagList itemListTag = new NBTTagList();
		for (int i = 0; i < this.inventoryOven.getSizeInventory(); ++i)
		{
			if (this.inventoryOven.getStackInSlot(i) != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("Slot", (byte) i);
				this.inventoryOven.getStackInSlot(i).writeToNBT(itemTag);
				itemListTag.appendTag(itemTag);
			}
		}

		tag.setTag("Items", itemListTag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readCustomNBT(pkt.customParam1);
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		temp = tag.getInteger("temp");
		maxTemp = tag.getInteger("maxTemp");
		fuelRemaining = tag.getInteger("fuelRemaining");
		fuelHotness = tag.getInteger("fuelHotness");

		timeInOvenSlot = tag.getIntArray("timeInOvenSlot");

		NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			this.inventoryOven.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
	}

	public boolean receiveClientEvent(int id, int value)
	{
		if (id == 1)
		{
			this.numUsingPlayers = value;
			return true;
		} else
		{
			return super.receiveClientEvent(id, value);
		}
	}

	public void sendPacket()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
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

	public void setTemp(int temp)
	{
		this.temp = temp;
	}

	public void setMaxTemp(int maxTemp)
	{
		this.maxTemp = maxTemp;
	}

	public void setFuelRemaining(int fuelRemaining)
	{
		this.fuelRemaining = fuelRemaining;
	}
}
