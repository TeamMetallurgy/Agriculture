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

public class TileEntityOven extends BaseMachineTileEntity implements IInventory
{
	private ItemStack[] ovenItemStacks = new ItemStack[20];
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

	int sync = 80;

	private int numUsingPlayers;
	public float doorAngle;
	public float prevDoorAngle;

	public int getTemp()
	{
		return temp;
	}

	public void updateEntity()
	{
		if (--sync <= 0)
		{
			sendPacket();
			sync = 80;
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

			for (int i = 0; i < numOvenSlots; i++)
			{
				ItemStack stack = ovenItemStacks[ovenInventorySlotStart + i];
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
						ovenItemStacks[ovenInventorySlotStart + i] = result;
				} else
				{
					if (i < timeInOvenSlot.length)
						timeInOvenSlot[i] = 0;
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

	private void burnFuel()
	{
		ItemStack fuelStack = ovenItemStacks[fuelSlot];

		int fuelAmount = getItemBurnTime(fuelStack);
		if (fuelAmount > 0)
		{
			// TODO: add different "hotness" levels for different fuels
			fuelHotness = 1;

			fuelRemaining += fuelAmount;

			fuelStack.stackSize--;

			if (fuelStack.stackSize == 0)
			{
				ovenItemStacks[fuelSlot] = fuelStack.getItem().getContainerItemStack(fuelStack);
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
		for (int i = 0; i < this.getSizeInventory(); ++i)
		{
			if (this.getStackInSlot(i) != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				itemTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(itemTag);
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
			setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
	}

	@Override
	public int getSizeInventory()
	{
		return ovenItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return ovenItemStacks[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (this.ovenItemStacks[i] != null)
		{
			ItemStack itemstack;

			if (this.ovenItemStacks[i].stackSize <= j)
			{
				itemstack = this.ovenItemStacks[i];
				this.ovenItemStacks[i] = null;
				this.onInventoryChanged();
				return itemstack;
			} else
			{
				itemstack = this.ovenItemStacks[i].splitStack(j);

				if (this.ovenItemStacks[i].stackSize == 0)
				{
					this.ovenItemStacks[i] = null;
				}

				this.onInventoryChanged();
				return itemstack;
			}
		} else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		if (this.ovenItemStacks[i] != null)
		{
			ItemStack itemstack = this.ovenItemStacks[i];
			this.ovenItemStacks[i] = null;
			return itemstack;
		} else
		{
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		this.ovenItemStacks[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();

	}

	@Override
	public String getInvName()
	{
		return "";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return getInvName().isEmpty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest()
	{
		if (this.numUsingPlayers < 0)
		{
			this.numUsingPlayers = 0;
		}

		++this.numUsingPlayers;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
	}

	@Override
	public void closeChest()
	{
		if (this.getBlockType() != null && this.getBlockType() instanceof BlockOven)
		{
			--this.numUsingPlayers;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.numUsingPlayers);
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		if (i >= 17 && i <= 19)
		{
			return itemstack.itemID == AgricultureItems.ovenRack.itemID;
		}

		return true;
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
