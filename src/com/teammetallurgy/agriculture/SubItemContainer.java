package com.teammetallurgy.agriculture;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

public class SubItemContainer extends SubItem implements IFluidContainerItem
{

	private final int capacity;

	public SubItemContainer(int id, int damage, int capacity)
	{
		super(id, damage);
		this.capacity = capacity;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
	{
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid"))
		{
			return null;
		}

		final FluidStack stack = FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));
		if (stack == null)
		{
			return null;
		}

		stack.amount = Math.min(stack.amount, maxDrain);
		if (doDrain)
		{
			if (maxDrain >= capacity)
			{
				container.stackTagCompound.removeTag("Fluid");

				if (container.stackTagCompound.hasNoTags())
				{
					container.stackTagCompound = null;
				}
				return stack;
			}

			final NBTTagCompound fluidTag = container.stackTagCompound.getCompoundTag("Fluid");
			fluidTag.setInteger("Amount", fluidTag.getInteger("Amount") - maxDrain);
			container.stackTagCompound.setTag("Fluid", fluidTag);
		}
		return stack;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill)
	{
		if (resource == null)
		{
			return 0;
		}

		if (!doFill)
		{
			if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid"))
			{
				return Math.min(capacity, resource.amount);
			}

			final FluidStack stack = FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));

			if (stack == null)
			{
				return Math.min(capacity, resource.amount);
			}

			if (!stack.isFluidEqual(resource))
			{
				return 0;
			}

			return Math.min(capacity - stack.amount, resource.amount);
		}

		if (container.stackTagCompound == null)
		{
			container.stackTagCompound = new NBTTagCompound();
		}

		if (!container.stackTagCompound.hasKey("Fluid"))
		{
			final NBTTagCompound fluidTag = resource.writeToNBT(new NBTTagCompound());

			if (capacity < resource.amount)
			{
				fluidTag.setInteger("Amount", capacity);
				container.stackTagCompound.setTag("Fluid", fluidTag);
				return capacity;
			}

			container.stackTagCompound.setTag("Fluid", fluidTag);
			return resource.amount;
		}

		final NBTTagCompound fluidTag = container.stackTagCompound.getCompoundTag("Fluid");
		final FluidStack stack = FluidStack.loadFluidStackFromNBT(fluidTag);

		if (!stack.isFluidEqual(resource))
		{
			return 0;
		}

		int filled = capacity - resource.amount;
		if (resource.amount < filled)
		{
			stack.amount += resource.amount;
			filled = resource.amount;
		} else
		{
			stack.amount = capacity;
		}

		container.stackTagCompound.setTag("Fluid", stack.writeToNBT(fluidTag));
		return filled;
	}

	@Override
	public int getCapacity(ItemStack container)
	{
		return capacity;
	}

	@Override
	public FluidStack getFluid(ItemStack container)
	{
		if (container.stackTagCompound == null || !container.stackTagCompound.hasKey("Fluid"))
		{
			return null;
		}
		return FluidStack.loadFluidStackFromNBT(container.stackTagCompound.getCompoundTag("Fluid"));
	}

}
