package com.teammetallurgy.agriculture.machines.frier;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.food.IDeepFry;
import com.teammetallurgy.agriculture.machines.FuelMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.FrierRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TileEntityFrier extends FuelMachineTileEntity
{
	private InventoryFrier cabinet = new InventoryFrier("", false, 12, this);
	private IInventory fuel = new InventoryBasic("", false, 1);

	private FluidTank tank = new FluidTank(8000);

	int numUsingPlayers;

	// Left door
	float prevLeftDoorAngle;
	float leftDoorAngle;

	// Right door
	double prevRightDoorAngle;
	float rightDoorAngle;
	private int processingTime;
	private int[] timeInOvenSlot = new int[11];
	private int sync;

	public IInventory getInventory()
	{
		return cabinet;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		super.readCustomNBT(tag);
		NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			cabinet.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}

		NBTTagCompound tankTag = (NBTTagCompound) tag.getTag("Tank");
		if (tankTag != null)
		{
			this.tank = this.tank.readFromNBT(tankTag);
		}
	}
	
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readCustomNBT(pkt.customParam1);
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

		super.writeCustomNBT(tag);
		NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < this.cabinet.getSizeInventory(); ++i)
		{
			if (this.cabinet.getStackInSlot(i) != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.cabinet.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbtTagList.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("Items", nbtTagList);

		NBTTagCompound tankTag = new NBTTagCompound();

		tank.writeToNBT(tankTag);

		tag.setTag("Tank", tankTag);
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

	@Override
	public void updateEntity()
	{
		if (sync-- == 0 && !worldObj.isRemote)
		{
			sendPacket();
		}
		if (this.processingTime > 0)
		{
			--this.processingTime;
			--this.currentItemBurnTime;
			--this.fuelRemaining;
		}

		if (this.fuelRemaining <= 0)
		{
			burnFuel();
		}
		ItemStack bucketStack = getInventory().getStackInSlot(0);
		if (bucketStack != null && bucketStack.isItemEqual(AgricultureItems.cookingOil.getItemStack()))
		{
			FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid("cookingoil"), 1000);

			if (fluidStack != null)
			{
				if (tank.fill(fluidStack, false) == fluidStack.amount)
				{
					tank.fill(fluidStack, true);

					--bucketStack.stackSize;

					if (bucketStack.stackSize <= 0)
					{
						getInventory().setInventorySlotContents(0, bucketStack.getItem().getContainerItemStack(bucketStack));
					}
				}
			}
		}

		for (int i = 1; i < getInventory().getSizeInventory(); i++)
		{

			ItemStack stack = getInventory().getStackInSlot(i);
			if (stack != null && fuelRemaining >= 0)
			{
				timeInOvenSlot[i - 1]++;
				if (stack.getItem() instanceof IDeepFry)
					((IDeepFry) stack.getItem()).heatUpdate(stack, timeInOvenSlot[i]);

				ItemStack result = FrierRecipes.getInstance().findMatchingRecipe(stack, timeInOvenSlot[i - 1]);
				
				fuelRemaining--;

				FluidStack stack2 = tank.drain(1000, false);
				if (result != null && stack2 != null && stack2.amount == 1000)
				{
					tank.drain(stack2.amount, true);
					getInventory().setInventorySlotContents(i, result.copy());
				}
			} else
			{
				timeInOvenSlot[i - 1] = 0;
			}

		}
		prevLeftDoorAngle = leftDoorAngle;
		if (this.numUsingPlayers == 0 && leftDoorAngle > 0.0F || this.numUsingPlayers > 0 && leftDoorAngle < 1.0F)
		{
			if (this.numUsingPlayers > 0)
			{
				leftDoorAngle += 0.1;
			} else
			{
				leftDoorAngle -= 0.1;
			}

			if (leftDoorAngle > 1.0F)
			{
				leftDoorAngle = 1.0F;
			}

			if (leftDoorAngle < 0.0F)
			{
				leftDoorAngle = 0.0F;
			}
		}
	}

	public void sendPacket()
	{

		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try
		{
			dos.writeShort(3);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeByte(direction);

			dos.writeInt(fuelRemaining);

			dos.writeInt(tank.getFluidAmount());

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
	public int getFuelSlot()
	{
		return 0;
	}

	@Override
	public IInventory getFuelInventory()
	{
		return fuel;
	}
}
