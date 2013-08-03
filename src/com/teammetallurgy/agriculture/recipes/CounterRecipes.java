package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

public class CounterRecipes
{
	/** The static instance of this class */
	private static final CounterRecipes instance = new CounterRecipes();

	/** A list of all the recipes added */
	private List recipes = new ArrayList();

	/**
	 * Returns the static instance of this class
	 */
	public static final CounterRecipes getInstance()
	{
		return instance;
	}

	private CounterRecipes()
	{
		addRecipe(AgricultureItems.dough.getItemStack(), new ItemStack(Item.bucketWater), AgricultureItems.flour);
		addRecipe(AgricultureItems.rawHamburgerPatty, AgricultureItems.groundBeef, AgricultureItems.groundBeef);
		addRecipe(AgricultureItems.appleJellyToast, AgricultureItems.butteredToast, AgricultureItems.appleJelly);
		addRecipe(AgricultureItems.cinnamonToast, AgricultureItems.butteredToast, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.strawberryJellyToast, AgricultureItems.butteredToast, AgricultureItems.strawberryJelly);
		addRecipe(AgricultureItems.caramelAppleWithNuts, AgricultureItems.caramelApple, AgricultureItems.crushedPeanuts);
		addRecipe(AgricultureItems.appleJelly, AgricultureItems.ceramicBowl, AgricultureItems.appleMush, Item.sugar);
		addRecipe(AgricultureItems.mashedPotatos, AgricultureItems.ceramicBowl, Item.bakedPotato, AgricultureItems.butter);
		addRecipe(AgricultureItems.carrotCakeBatter, AgricultureItems.ceramicBowl, AgricultureItems.batter, Item.carrot);
		addRecipe(AgricultureItems.rawFrenchToast, AgricultureItems.ceramicBowl, Item.bread, Item.egg, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.batter, AgricultureItems.ceramicBowl, AgricultureItems.butter, Item.egg, AgricultureItems.flour, Item.sugar);
	}
	
	public void addRecipe(SubItem item, SubItem baseItem, Object...par2ArrayOfObj)
	{
		addRecipe(item.getItemStack(), baseItem.getItemStack(), par2ArrayOfObj);
	}

	public void addRecipe(ItemStack par1ItemStack, ItemStack baseItem, Object... par2ArrayOfObj)
	{
		ArrayList arraylist = new ArrayList();
		Object[] aobject = par2ArrayOfObj;
		int i = par2ArrayOfObj.length;

		for (int j = 0; j < i; ++j)
		{
			Object object1 = aobject[j];

			if (object1 instanceof ItemStack)
			{
				arraylist.add(((ItemStack) object1).copy());
			}
			else if(object1 instanceof SubItem)
			{
				arraylist.add(((SubItem) object1).getItemStack());
			}
			else if (object1 instanceof Item)
			{
				arraylist.add(new ItemStack((Item) object1));
			} else
			{
				if (!(object1 instanceof Block))
				{
					throw new RuntimeException("Invalid shapeless recipy!");
				}

				arraylist.add(new ItemStack((Block) object1));
			}
		}

		this.recipes.add(new CounterRecipe(par1ItemStack, baseItem, arraylist));
	}

	public ItemStack findMatchingRecipe(IInventory processor)
	{
		int i = 0;
		ItemStack itemstack = null;
		ItemStack itemstack1 = null;
		int j;

		for (j = 0; j < processor.getSizeInventory(); ++j)
		{
			ItemStack itemstack2 = processor.getStackInSlot(j);

			if (itemstack2 != null)
			{
				if (i == 0)
				{
					itemstack = itemstack2;
				}

				if (i == 1)
				{
					itemstack1 = itemstack2;
				}

				++i;
			}
		}

		if (i == 2 && itemstack.itemID == itemstack1.itemID && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && Item.itemsList[itemstack.itemID].isRepairable())
		{
			Item item = Item.itemsList[itemstack.itemID];
			int k = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
			int l = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
			int i1 = k + l + item.getMaxDamage() * 5 / 100;
			int j1 = item.getMaxDamage() - i1;

			if (j1 < 0)
			{
				j1 = 0;
			}

			return new ItemStack(itemstack.itemID, 1, j1);
		} else
		{
			for (j = 0; j < this.recipes.size(); ++j)
			{
				ICounterRecipe irecipe = (ICounterRecipe) this.recipes.get(j);

				if (irecipe.matches(processor))
				{
					return irecipe.getCraftingResult(processor);
				}
			}

			return null;
		}
	}

	/**
	 * returns the List<> of all recipes
	 */
	public List getRecipeList()
	{
		return this.recipes;
	}

	public boolean isMat(ItemStack findMatchingRecipe, ItemStack stack, IInventory processor)
	{
		for (int j = 0; j < this.recipes.size(); ++j)
		{
			ICounterRecipe irecipe = (ICounterRecipe) this.recipes.get(j);

			if (ItemStack.areItemStacksEqual(irecipe.getRecipeOutput(), findMatchingRecipe))
			{
				return irecipe.isMat(stack);
			}
		}
		return false;

	}
}