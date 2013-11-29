package com.teammetallurgy.agriculture;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		final ByteArrayDataInput byteIn = ByteStreams.newDataInput(packet.data);
		final short packetID = byteIn.readShort();

		if (packetID == 0)
		{
			final int x = byteIn.readInt();
			final int y = byteIn.readInt();
			final int z = byteIn.readInt();
			byteIn.readByte();
			final int fuelRemaining = byteIn.readInt();
			final int temp = byteIn.readInt();
			final int maxTemp = byteIn.readInt();

			final TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

			if (te instanceof TileEntityOven)
			{
				final TileEntityOven oven = (TileEntityOven) te;
				oven.setTemp(temp);
				oven.setMaxTemp(maxTemp);
				oven.setFuelRemaining(fuelRemaining);
			}
		} else if (packetID == 1)
		{
			final int x = byteIn.readInt();
			final int y = byteIn.readInt();
			final int z = byteIn.readInt();
			byteIn.readByte();
			final int processingTime = byteIn.readInt();
			final int currentItemBurnTime = byteIn.readInt();
			final int fuelRemaining = byteIn.readInt();
			final int coolDown = byteIn.readInt();

			final boolean item = byteIn.readInt() == 1;

			ItemStack result = null;
			if (item)
			{
				final int itemId = byteIn.readInt();
				final int itemDamage = byteIn.readInt();
				final int stackSize = byteIn.readInt();
				result = new ItemStack(itemId, stackSize, itemDamage);
			}

			final TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

			if (te instanceof TileEntityProcessor)
			{
				final TileEntityProcessor oven = (TileEntityProcessor) te;
				oven.setProcessingTime(processingTime);
				oven.setCurrentItemBurnTime(currentItemBurnTime);
				oven.setResult(result);
				oven.setFuelRemaining(fuelRemaining);
				oven.setCoolDown(coolDown);
			}
		} else if (packetID == 2)
		{
			final int x = byteIn.readInt();
			final int y = byteIn.readInt();
			final int z = byteIn.readInt();
			byteIn.readByte();

			final int processingTime = byteIn.readInt();
			final int maxProcessingTime = byteIn.readInt();
			final int fuelRemaining = byteIn.readInt();
			final int amountLeftInput = byteIn.readInt();
			final int amountRightInput = byteIn.readInt();

			final int amountLeft = byteIn.readInt();
			final int amountRight = byteIn.readInt();

			final boolean processing = byteIn.readBoolean();

			final TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

			if (te instanceof TileEntityBrewer)
			{
				final TileEntityBrewer oven = (TileEntityBrewer) te;
				oven.setProcessingTime(processingTime);
				oven.setMaxProcessingTime(maxProcessingTime);
				oven.setFuelRemaining(fuelRemaining);
				oven.setAmountLeftInput(amountLeftInput);
				oven.setAmountRightInput(amountRightInput);

				final FluidStack fluid = oven.getRightTank().getFluid();

				if (fluid != null)
				{
					fluid.amount = amountRight;
				}

				final FluidStack fluid2 = oven.getLeftTank().getFluid();

				if (fluid2 != null)
				{
					fluid2.amount = amountLeft;
				}

				oven.setProcessing(processing);
			}
		} else if (packetID == 3)
		{
			final int x = byteIn.readInt();
			final int y = byteIn.readInt();
			final int z = byteIn.readInt();
			byteIn.readByte();

			final int fuelRemaining = byteIn.readInt();
			final int fluidAmount = byteIn.readInt();

			final TileEntity te = ((EntityPlayer) player).worldObj.getBlockTileEntity(x, y, z);

			if (te instanceof TileEntityFrier)
			{
				final TileEntityFrier oven = (TileEntityFrier) te;
				oven.setFuelRemaining(fuelRemaining);

				final FluidStack fluid = oven.getTank().getFluid();

				if (fluid != null)
				{
					fluid.amount = fluidAmount;
				}
			}
		} else if (packetID == 256 && FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			final float hunger = byteIn.readFloat();
			Agriculture.proxy.updateHunger(hunger);
		}
	}
}
