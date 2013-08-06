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
	private final InventoryFrier cabinet = new InventoryFrier("", false, 12, this);
	private final IInventory fuel = new InventoryBasic("", false, 1);

	private FluidTank tank = new FluidTank(8000);

	int numUsingPlayers;

	// Left door
	float prevLeftDoorAngle;
	float leftDoorAngle;

	// Right door
	double prevRightDoorAngle;
	float rightDoorAngle;
	private int processingTime;
	private final int[] timeInOvenSlot = new int[11];
	private int sync;

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public IInventory getFuelInventory()
	{
		return fuel;
	}

	@Override
	public int getFuelSlot()
	{
		return 0;
	}

	public IInventory getInventory()
	{
		return cabinet;
	}

	public FluidTank getTank()
	{
		return tank;
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readCustomNBT(pkt.customParam1);
	}

	@Override
	public void onInventoryChanged()
	{
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag)
	{
		super.readCustomNBT(tag);
		final NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			final NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			final int slot = Integer.valueOf(base.getByte("Slot"));
			cabinet.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}

		final NBTTagCompound tankTag = (NBTTagCompound) tag.getTag("Tank");
		if (tankTag != null)
		{
			tank = tank.readFromNBT(tankTag);
		}
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
			dos.writeShort(3);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeByte(direction);

			dos.writeInt(fuelRemaining);

			dos.writeInt(tank.getFluidAmount());

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
		if (sync-- == 0 && !worldObj.isRemote)
		{
			sendPacket();
		}
		if (processingTime > 0)
		{
			--processingTime;
			--currentItemBurnTime;
			--fuelRemaining;
		}

		if (fuelRemaining <= 0)
		{
			burnFuel();
		}
		final ItemStack bucketStack = getInventory().getStackInSlot(0);
		if (bucketStack != null && bucketStack.isItemEqual(AgricultureItems.cookingOil.getItemStack()))
		{
			final FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid("cookingoil"), 1000);

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

			final ItemStack stack = getInventory().getStackInSlot(i);
			if (stack != null && fuelRemaining >= 0)
			{
				timeInOvenSlot[i - 1]++;
				if (stack.getItem() instanceof IDeepFry)
				{
					((IDeepFry) stack.getItem()).heatUpdate(stack, timeInOvenSlot[i]);
				}

				final ItemStack result = FrierRecipes.getInstance().findMatchingRecipe(stack, timeInOvenSlot[i - 1]);

				fuelRemaining--;

				final FluidStack stack2 = tank.drain(1000, false);
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
		if (numUsingPlayers == 0 && leftDoorAngle > 0.0F || numUsingPlayers > 0 && leftDoorAngle < 1.0F)
		{
			if (numUsingPlayers > 0)
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

	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{

		super.writeCustomNBT(tag);
		final NBTTagList nbtTagList = new NBTTagList();
		for (int i = 0; i < cabinet.getSizeInventory(); ++i)
		{
			if (cabinet.getStackInSlot(i) != null)
			{
				final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				cabinet.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbtTagList.appendTag(nbttagcompound1);
			}
		}

		tag.setTag("Items", nbtTagList);

		final NBTTagCompound tankTag = new NBTTagCompound();

		tank.writeToNBT(tankTag);

		tag.setTag("Tank", tankTag);
	}
}
