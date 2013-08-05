package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ProcessorRecipes
{
	/** The static instance of this class */
	private static final ProcessorRecipes instance = new ProcessorRecipes();

	/** A list of all the recipes added */
	private List recipes = new ArrayList();

	/**
	 * Returns the static instance of this class
	 */
	public static final ProcessorRecipes getInstance()
	{
		return instance;
	}

	public void addRecipe(Object item, Object result)
	{
		addRecipe(item, null, result);
	}
	
	public void addRecipe(Object par1ItemStack, Object baseItem, Object result)
	{
		this.recipes.add(new ProcessRecipe(getItemStack(par1ItemStack), getItemStack(baseItem), getItemStack(result)));
	}
	
	public ItemStack getItemStack(Object object)
	{
		ItemStack stack = null;
		if(object instanceof ItemStack)
		{
			stack = (ItemStack) object;
		}
		else if(object instanceof SubItem)
		{
			stack = ((SubItem) object).getItemStack();
		}
		else if(object instanceof Item)
		{
			stack = new ItemStack((Item)object);
		}
		return stack;
	}

	public ItemStack findMatchingRecipe(ItemStack first, ItemStack second)
	{
		//System.out.println("recipe " + first.getItem().getUnlocalizedName(first) + " " + second.getItem().getUnlocalizedName(second));
		for (int j = 0; j < this.recipes.size(); ++j)
		{
			ProcessRecipe irecipe = (ProcessRecipe) this.recipes.get(j);

			if (irecipe.matches(first, second))
			{
				return irecipe.getCraftingResult();
			}
		}

		return null;
	}

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

	public static int getProcessTime(ItemStack stackInSlot)
	{
		return stackInSlot != null ? 20 : 0;
	}
}
