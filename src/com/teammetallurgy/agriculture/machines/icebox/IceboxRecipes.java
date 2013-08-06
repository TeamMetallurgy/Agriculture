package com.teammetallurgy.agriculture.machines.icebox;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class IceboxRecipes
{
	private static Map<Integer, IceboxRecipes> recipes;

	public static void addRecipe(Object result, Object source, int heat)
	{
		final ItemStack resultStack = getItemStack(result);
		final ItemStack sourceStack = getItemStack(source);
		final IceboxRecipes recipe = new IceboxRecipes(sourceStack, resultStack, heat);

		final int hash = (sourceStack.itemID << 8) + sourceStack.getItemDamage();

		if (recipes == null)
		{
			recipes = new HashMap<Integer, IceboxRecipes>();
		}

		recipes.put(hash, recipe);
	}

	public static ItemStack getItemStack(Object object)
	{
		ItemStack stack = null;
		if (object instanceof ItemStack)
		{
			stack = (ItemStack) object;
		} else if (object instanceof SubItem)
		{
			stack = ((SubItem) object).getItemStack();
		} else if (object instanceof Item)
		{
			stack = new ItemStack((Item) object);
		}
		return stack;
	}

	public static ItemStack getResult(ItemStack input, int heat)
	{
		final int hash = (input.itemID << 8) + input.getItemDamage();
		final IceboxRecipes result = recipes.get(hash);

		if (result != null)
		{
			if (result.getRequiredHeat() <= heat)
			{
				final ItemStack itemStack = result.getResult();
				itemStack.stackSize = 1;
				return itemStack;
			}
		}

		return null;
	}

	private final int requiredHeat;

	private final ItemStack result;

	static
	{
		addRecipe(AgricultureItems.vanillaIceCream, AgricultureItems.vanillaIceCreamMix, 100000);
		addRecipe(AgricultureItems.strawberryIceCream, AgricultureItems.strawberryIceCreamMix, 100000);
		addRecipe(AgricultureItems.chocolateIceCream, AgricultureItems.chocolateIceCreamMix, 100000);
	}

	private IceboxRecipes(ItemStack source, ItemStack result, int heat)
	{
		this.result = result;
		requiredHeat = heat;
	}

	private int getRequiredHeat()
	{
		return requiredHeat;
	}

	private ItemStack getResult()
	{
		return result;
	}
}
