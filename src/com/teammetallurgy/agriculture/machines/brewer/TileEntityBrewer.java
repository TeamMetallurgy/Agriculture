package com.teammetallurgy.agriculture.machines.brewer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.machines.FuelMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.BrewerRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TileEntityBrewer extends FuelMachineTileEntity
{
	private InventoryBrewer cabinet = new InventoryBrewer("", false, 3, this);

	private FluidTank leftTank = new FluidTank(8000);
	private FluidTank rightTank = new FluidTank(8000);

	float liquidAmount;
	float maxLiquidAmount;

	float update = 0;

	int numUsingPlayers;

	// Left door
	float prevLeftDoorAngle;
	float leftDoorAngle;

	// Right door
	double prevRightDoorAngle;
	float rightDoorAngle;

	private int amountLeftInput;
	private int amountRightInput;

	private int processingTime;
	private int maxProcessingTime;

	private boolean processing = false;

	private FluidStack fluidStack;

	public IInventory getBrewer()
	{
		return cabinet;
	}

	@Override
	public void onInventoryChanged()
	{
	}

	public float getLiquidScaled()
	{
		return rightTank.getFluidAmount() / (float) rightTank.getCapacity();
	}

	public float getProcessScaled()
	{
		float i = processingTime / (float) maxProcessingTime;

		if (i <= 0)
		{
			return 0;
		}

		return i;
	}

	public boolean isLiquidContainer(ItemStack stack)
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.readCustomNBT(tag);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
	{
		readCustomNBT(pkt.customParam1);
	}

	public void readCustomNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			cabinet.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
		NBTTagCompound rightTankTag = (NBTTagCompound) tag.getTag("RightTank");
		NBTTagCompound leftTankTag = (NBTTagCompound) tag.getTag("LeftTank");
		NBTTagCompound proceedFluid = (NBTTagCompound) tag.getTag("ProcessedFluid");

		this.rightTank = rightTank.readFromNBT(rightTankTag);
		this.leftTank = leftTank.readFromNBT(leftTankTag);

		if (proceedFluid != null)
			this.fluidStack = FluidStack.loadFluidStackFromNBT(proceedFluid);

		this.amountLeftInput = tag.getInteger("AmountLeftInput");
		this.amountRightInput = tag.getInteger("AmountRightInput");

		this.fuelRemaining = tag.getInteger("FuelRemaining");

		this.processingTime = tag.getInteger("ProcessingTime");
		this.maxProcessingTime = tag.getInteger("MaxProcessingTime");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag)
	{

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

		NBTTagCompound rightTankTag = new NBTTagCompound();
		NBTTagCompound leftTankTag = new NBTTagCompound();

		rightTank.writeToNBT(rightTankTag);
		leftTank.writeToNBT(leftTankTag);

		tag.setTag("RightTank", rightTankTag);
		tag.setTag("LeftTank", leftTankTag);

		tag.setTag("Items", nbtTagList);

		tag.setInteger("AmountLeftInput", amountLeftInput);
		tag.setInteger("AmountRightInput", amountRightInput);

		tag.setInteger("FuelRemaining", fuelRemaining);

		tag.setInteger("ProcessingTime", processingTime);
		tag.setInteger("MaxProcessingTime", maxProcessingTime);

		NBTTagCompound processedFluid = new NBTTagCompound();

		if (fluidStack != null)
		{
			fluidStack.writeToNBT(processedFluid);
		}

		tag.setTag("ProcessedFluid", processedFluid);

	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
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

		if (processingTime-- <= 0)
		{
			process();
			this.processing = false;
		}

		if (this.processingTime > 0)
		{
			--this.currentItemBurnTime;
		}

		if (fuelRemaining <= 0)
		{
			burnFuel();
		}

		if (update-- <= 0)
		{
			update = 10;

			if (amountLeftInput > 0)
			{
				if (leftTank.getFluid() != null)
				{
					amountLeftInput -= 100;

					FluidStack fluidStack = leftTank.getFluid().copy();

					fluidStack.amount = 100;

					leftTank.fill(fluidStack, true);
				} else
				{
					amountLeftInput = 0;
				}
			}

			if (amountLeftInput < 0)
			{

				if (leftTank.getFluid() != null)
				{
					amountLeftInput += 100;
					leftTank.drain(100, true);
				} else
				{
					amountLeftInput = 0;
				}
			}

			if (amountRightInput > 0)
			{
				if (rightTank.getFluid() != null)
				{
					amountRightInput -= 100;

					FluidStack fluidStack = rightTank.getFluid().copy();

					fluidStack.amount = 100;

					rightTank.fill(fluidStack, true);
				} else
				{
					amountRightInput = 0;
				}
			}

			if (amountRightInput < 0)
			{

				if (leftTank.getFluid() != null)
				{
					amountLeftInput += 100;
					rightTank.drain(100, true);
				} else
				{
					amountRightInput = 0;
				}
			}

			if (amountRightInput == 0)
			{
				fluidStack = null;
			}
		}

		ItemStack itemStack2 = cabinet.getStackInSlot(2);
		ItemStack itemStack = cabinet.getStackInSlot(0);

		if (processingTime <= 0 && amountLeftInput == 0 && amountRightInput == 0)
		{
			if (itemStack2 != null)
			{
				FluidStack fluidStack = BrewerRecipes.getInstance().findMatchingFluid(itemStack2, leftTank.getFluid());

				if (fluidStack == null)
				{
					fluidStack = BrewerRecipes.getInstance().findMatchingFluid(itemStack2, null);
				}

				if (fluidStack != null && !processing && amountLeftInput == 0 && amountRightInput == 0)
				{
					this.maxProcessingTime = this.processingTime = BrewerRecipes.getInstance().getProcessTime(itemStack2);
					if (leftTank.fill(fluidStack, false) == fluidStack.amount - amountLeftInput)
					{
						this.processing = true;
						this.amountLeftInput += fluidStack.amount - 100;

						leftTank.fill(new FluidStack(fluidStack, 100), true);

						--itemStack2.stackSize;

						if (itemStack2.stackSize <= 0)
						{
							cabinet.setInventorySlotContents(2, itemStack2.getItem().getContainerItemStack(itemStack2));
						}
						if (!worldObj.isRemote)
							sendPacket();
					}
				}
			}

			if (itemStack != null)
			{
				FluidStack fluidStack = BrewerRecipes.getInstance().findMatchingFluid(itemStack, leftTank.getFluid());

				if (fluidStack == null)
				{
					fluidStack = BrewerRecipes.getInstance().findMatchingFluid(itemStack, null);
				}

				if (fluidStack != null)
				{
					if (!processing && amountLeftInput == 0 && amountRightInput == 0 && fuelRemaining > 0)
					{
						if (leftTank.getFluid() != null && !fluidStack.isFluidEqual(leftTank.getFluid()) && rightTank.fill(fluidStack, false) == fluidStack.amount - amountRightInput && processingTime <= 0)
						{
							this.maxProcessingTime = this.processingTime = BrewerRecipes.getInstance().getProcessTime(itemStack);
							this.processing = true;

							--itemStack.stackSize;

							this.amountRightInput += fluidStack.amount - 100;

							this.amountLeftInput += -amountRightInput;

							FluidStack stack = fluidStack.copy();
							stack.amount = 100;

							rightTank.fill(stack, true);

							leftTank.drain(100, true);

							if (itemStack.stackSize <= 0)
							{
								cabinet.setInventorySlotContents(0, itemStack.getItem().getContainerItemStack(itemStack));
							}
							if (!worldObj.isRemote)
								sendPacket();
						}
					}
				}
				ItemStack itemStackResult = BrewerRecipes.getInstance().findMatchingItem(itemStack, rightTank.getFluid());

				if (itemStackResult != null)
				{
					--this.fuelRemaining;
					if (itemStack.stackSize == 1 && !this.processing)
					{
						this.maxProcessingTime = this.processingTime = BrewerRecipes.getInstance().getProcessTime(itemStackResult);
						this.processing = true;
					}

					if (this.processingTime <= 0 && this.processing)
					{
						cabinet.setInventorySlotContents(0, itemStackResult.copy());
						rightTank.drain(1000, true);
						this.processing = false;
					}
					if (!worldObj.isRemote)
						sendPacket();

				}
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

	private void process()
	{
		if (fluidStack != null)
		{

		}

	}

	public void setProcessing(boolean processing)
	{
		this.processing = processing;
	}

	public void setProcessingTime(int processingTime)
	{
		this.processingTime = processingTime;
	}

	public void setAmountLeftInput(int amountLeftInput)
	{
		this.amountLeftInput = amountLeftInput;
	}

	public void setAmountRightInput(int amountRightInput)
	{
		this.amountRightInput = amountRightInput;
	}

	public void setMaxProcessingTime(int maxProcessingTime)
	{
		this.maxProcessingTime = maxProcessingTime;
	}

	public void setFuelRemaining(int fuelRemaining)
	{
		this.fuelRemaining = fuelRemaining;
	}

	public void sendPacket()
	{

		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try
		{
			dos.writeShort(2);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeByte(direction);

			dos.writeInt(processingTime);
			dos.writeInt(maxProcessingTime);
			dos.writeInt(fuelRemaining);
			dos.writeInt(amountLeftInput);
			dos.writeInt(amountRightInput);

			dos.writeInt(leftTank.getFluidAmount());
			dos.writeInt(rightTank.getFluidAmount());

			dos.writeBoolean(processing);

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

	public int getAmountRightInput()
	{
		return amountRightInput;
	}

	public FluidTank getLeftTank()
	{
		return leftTank;
	}

	public FluidTank getRightTank()
	{
		return rightTank;
	}

	@Override
	public int getFuelSlot()
	{
		return 1;
	}

	@Override
	public IInventory getFuelInventory()
	{
		return cabinet;
	}

	public IInventory getInventory()
	{
		return cabinet;
	}
}
