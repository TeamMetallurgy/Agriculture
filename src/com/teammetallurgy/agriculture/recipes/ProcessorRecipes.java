package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class ProcessorRecipes
{
	/** The static instance of this class */
	private static final ProcessorRecipes instance = new ProcessorRecipes();

	/**
	 * Returns the static instance of this class
	 */
	public static final ProcessorRecipes getInstance()
	{
		return instance;
	}

	public static int getProcessTime(ItemStack stackInSlot)
	{
		return stackInSlot != null ? 20 : 0;
	}

	/** A list of all the recipes added */
	private final List recipes = new ArrayList();

	private ProcessorRecipes()
	{
		addRecipe(AgricultureItems.appleMush, Item.sugar, AgricultureItems.appleJelly);
		addRecipe(Item.appleRed, AgricultureItems.appleMush);
		addRecipe(Item.bread, AgricultureItems.breadCrumbs.getItemStack(8));
		addRecipe(AgricultureItems.milk, AgricultureItems.butter);
		addRecipe(AgricultureItems.groundCinnamon, Item.sugar, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.roastedPeanuts, AgricultureItems.water, AgricultureItems.cookingOil);
		addRecipe(AgricultureItems.roastedPeanuts, AgricultureItems.crushedPeanuts);
		addRecipe(Item.wheat, AgricultureItems.flour);
		addRecipe(Item.bone, AgricultureItems.gelatin);
		addRecipe(Item.beefRaw, AgricultureItems.groundBeef.getItemStack(2));
		addRecipe(Item.chickenRaw, AgricultureItems.groundChicken.getItemStack(2));
		addRecipe(AgricultureItems.cinnamon, AgricultureItems.groundCinnamon);
		addRecipe(Item.porkRaw, AgricultureItems.groundPork.getItemStack(2));
		addRecipe(AgricultureItems.crushedPeanuts, Item.sugar, AgricultureItems.peanutButter);
		addRecipe(AgricultureItems.strawberryMush, Item.sugar, AgricultureItems.strawberryJelly);
		addRecipe(AgricultureItems.strawberry, AgricultureItems.strawberryMush);
		addRecipe(AgricultureItems.milk, Item.sugar, AgricultureItems.whippedCream);
	}

	public void addRecipe(Object item, Object result)
	{
		addRecipe(item, null, result);
	}

	public void addRecipe(Object par1ItemStack, Object baseItem, Object result)
	{
		recipes.add(new ProcessRecipe(getItemStack(par1ItemStack), getItemStack(baseItem), getItemStack(result)));
	}

	public ItemStack findMatchingRecipe(ItemStack first, ItemStack second)
	{
		// System.out.println("recipe " +
		// first.getItem().getUnlocalizedName(first) + " " +
		// second.getItem().getUnlocalizedName(second));
		for (int j = 0; j < recipes.size(); ++j)
		{
			final ProcessRecipe irecipe = (ProcessRecipe) recipes.get(j);

			if (irecipe.matches(first, second))
			{
				return irecipe.getCraftingResult();
			}
		}

		return null;
	}

	public ItemStack getItemStack(Object object)
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
}
