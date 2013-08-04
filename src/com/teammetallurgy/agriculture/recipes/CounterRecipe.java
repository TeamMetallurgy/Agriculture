package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.teammetallurgy.agriculture.SubItem;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CounterRecipe implements ICounterRecipe
{

	private final ItemStack recipeOutput;

	public final List<ItemStack> recipeItems;

	private final ItemStack baseItem;

	public CounterRecipe(ItemStack outputStack, ItemStack baseItem, List par2List)
	{
		this.recipeOutput = outputStack;
		this.recipeItems = par2List;
		this.baseItem = baseItem;
	}

	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(IInventory processor)
	{
		ArrayList arraylist = new ArrayList(this.recipeItems);

		for (int k = 0; k < 4; ++k)
		{
			ItemStack stackInSlot = processor.getStackInSlot(k);
			if (stackInSlot != null)
			{

				int itemID = stackInSlot.getItem().itemID;
				int damage = stackInSlot.getItemDamage();
				if (itemID != baseItem.itemID || damage != baseItem.getItemDamage())
				{
					continue;
				}
			}
			else
			{
				continue;
			}

			for (int i = 0; i < 4; ++i)
			{
				for (int j = 0; j < 4; ++j)
				{
					ItemStack itemstack = processor.getStackInSlot((i + j * 4) + 4);

					if (itemstack != null)
					{
						boolean flag = false;
						Iterator iterator = arraylist.iterator();

						while (iterator.hasNext())
						{
							ItemStack itemstack1 = (ItemStack) iterator.next();

							if (itemstack.itemID == itemstack1.itemID && (itemstack1.getItemDamage() == 32767 || itemstack.getItemDamage() == itemstack1.getItemDamage()))
							{
								flag = true;
								arraylist.remove(itemstack1);
								break;
							}
						}
						
						if (!flag)
						{
							return false;
						}
					}
				}
			}

			return arraylist.isEmpty();
		}
		return false;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	public ItemStack getCraftingResult(IInventory procesor)
	{
		return this.recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	public int getRecipeSize()
	{
		return this.recipeItems.size();
	}

	@Override
	public boolean isMat(ItemStack stack)
	{
		for(ItemStack stack2: recipeItems) 
		{
			if(ItemStack.areItemStacksEqual(stack, stack2))
			{
				return true;
			}
		}
		return false;
	}

}
