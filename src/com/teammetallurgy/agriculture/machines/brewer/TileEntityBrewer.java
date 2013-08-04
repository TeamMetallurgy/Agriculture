package com.teammetallurgy.agriculture.machines.brewer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.recipes.BrewerRecipes;
import com.teammetallurgy.agriculture.recipes.CounterRecipes;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class TileEntityBrewer extends BaseMachineTileEntity
{
	private InventoryBasic cabinet = new InventoryBasic("", false, 2);

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

	private int fuelRemaining;

	private int amountLeftInput;

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
		return leftTank.getFluidAmount() / (float) leftTank.getCapacity();
	}

	public boolean isLiquidContainer(ItemStack stack)
	{
		return false;
	}

	private void burnFuel()
	{
		ItemStack fuelStack = cabinet.getStackInSlot(1);

		int fuelAmount = TileEntityFurnace.getItemBurnTime(fuelStack);
		if (fuelAmount > 0)
		{
			fuelRemaining += fuelAmount / 5;

			fuelStack.stackSize--;

			if (fuelStack.stackSize == 0)
			{
				cabinet.setInventorySlotContents(1, fuelStack.getItem().getContainerItemStack(fuelStack));
			}
			sendPacket();
		}
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

	public void readCustomNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList("Items");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound base = (NBTTagCompound) tagList.tagAt(i);
			int slot = Integer.valueOf(base.getByte("Slot"));
			cabinet.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(base));
		}
		
		
		rightTank.readFromNBT((NBTTagCompound) tag.getTag("RightTank"));
		leftTank.readFromNBT((NBTTagCompound) tag.getTag("LefttTank"));
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

		if (update-- <= 0)
		{
			update = 20;

			if (amountLeftInput > 0)
			{
				amountLeftInput -= 100;

				FluidStack fluidStack = leftTank.getFluid().copy();

				fluidStack.amount = 100;

				leftTank.fill(fluidStack, true);
			}
		}

		ItemStack itemStack = cabinet.getStackInSlot(0);

		if (itemStack != null)
		{
			int processTime = BrewerRecipes.getInstance().getProcessTime(itemStack);

			if (processTime > 0)
			{
				FluidStack fluidStack = BrewerRecipes.getInstance().findMatchingFluid(itemStack, leftTank.getFluid());

				if (fluidStack == null)
				{
					fluidStack = BrewerRecipes.getInstance().findMatchingFluid(itemStack, null);
				}

				if (fluidStack != null)
				{
					if (leftTank.fill(fluidStack, false) == fluidStack.amount)
					{
						this.amountLeftInput = fluidStack.amount - 100;

						FluidStack stack = fluidStack.copy();
						stack.amount = 100;

						leftTank.fill(stack, true);

						--itemStack.stackSize;

						if (itemStack.stackSize == 0)
						{
							cabinet.setInventorySlotContents(0, itemStack.getItem().getContainerItemStack(itemStack));
						}
					} else if (leftTank.getFluid() != null && !fluidStack.isFluidEqual(leftTank.getFluid()) && rightTank.fill(fluidStack, false) == fluidStack.amount)
					{
//						this.amountLeftInput = fluidStack.amount - 100;

//						FluidStack stack = fluidStack.copy();
//						stack.amount = 100;

						rightTank.fill(fluidStack, true);
						
						leftTank.drain(1000, true);
					
						--itemStack.stackSize;

						if (itemStack.stackSize == 0)
						{
							cabinet.setInventorySlotContents(0, itemStack.getItem().getContainerItemStack(itemStack));
						}
					}
				}

			}
		}

	}

	public void sendPacket()
	{
		/*
		 * ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		 * DataOutputStream dos = new DataOutputStream(bos); try {
		 * dos.writeShort(1); dos.writeInt(xCoord); dos.writeInt(yCoord);
		 * dos.writeInt(zCoord); dos.writeByte(direction);
		 * dos.writeInt(processingTime); dos.writeInt(currentItemBurnTime);
		 * dos.writeInt(fuelRemaining); dos.writeInt(coolDown);
		 * 
		 * if (result == null) { dos.writeInt(0); } else { dos.writeInt(1);
		 * dos.writeInt(result.getItem().itemID);
		 * dos.writeInt(result.getItemDamage()); dos.writeInt(result.stackSize);
		 * }
		 * 
		 * } catch (IOException e) { System.out.println(e); }
		 * 
		 * Packet250CustomPayload packet = new Packet250CustomPayload();
		 * packet.channel = Agriculture.MODID; packet.data = bos.toByteArray();
		 * packet.length = bos.size(); packet.isChunkDataPacket = true;
		 * 
		 * if (packet != null) { if
		 * (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
		 * PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 16,
		 * worldObj.provider.dimensionId, packet); } else {
		 * PacketDispatcher.sendPacketToServer(packet); } }
		 */
	}

	public FluidTank getLeftTank()
	{
		return leftTank;
	}

	public FluidTank getRightTank()
	{
		return rightTank;
	}
}
