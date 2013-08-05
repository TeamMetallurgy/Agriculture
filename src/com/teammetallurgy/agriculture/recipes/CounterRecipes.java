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
		addRecipe(AgricultureItems.mashedPotatos, AgricultureItems.ceramicBowl, Item.bakedPotato, AgricultureItems.butter);
		addRecipe(AgricultureItems.carrotCakeBatter, AgricultureItems.ceramicBowl, AgricultureItems.batter, Item.carrot);
		addRecipe(AgricultureItems.rawFrenchToast, AgricultureItems.ceramicPlate, Item.bread, Item.egg, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.batter, AgricultureItems.ceramicBowl, Item.egg, AgricultureItems.butter, AgricultureItems.flour);
		addRecipe(AgricultureItems.macaroniAndCheese, AgricultureItems.ceramicBowl, AgricultureItems.pasta, AgricultureItems.cheese);
		addRecipe(AgricultureItems.rawApplePie, AgricultureItems.ceramicPlate, AgricultureItems.dough, AgricultureItems.appleMush, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.rawStrawberryPie, AgricultureItems.ceramicPlate, AgricultureItems.dough, AgricultureItems.strawberryMush, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.strawberryShortcake, AgricultureItems.shortcake, AgricultureItems.strawberryJelly, AgricultureItems.strawberry, AgricultureItems.whippedCream);
		addRecipe(AgricultureItems.hamburger, AgricultureItems.ceramicPlate, AgricultureItems.sliceOfBread, AgricultureItems.hamburgerPatty, AgricultureItems.sliceOfBread);
		addRecipe(AgricultureItems.pbjSandwichApple, AgricultureItems.pbSandwich, AgricultureItems.appleJelly);
		addRecipe(AgricultureItems.pbjSandwichStrawberry, AgricultureItems.pbSandwich, AgricultureItems.strawberryJelly);
		addRecipe(AgricultureItems.cheeseSandwich, AgricultureItems.ceramicPlate, AgricultureItems.sliceOfBread, AgricultureItems.sliceOfCheese, AgricultureItems.sliceOfBread);
		addRecipe(AgricultureItems.butteredToast, AgricultureItems.toast, AgricultureItems.butter);
		addRecipe(AgricultureItems.baconCheeseFries, AgricultureItems.cheeseFries, AgricultureItems.bacon);
		addRecipe(AgricultureItems.baconCheeseburger, AgricultureItems.cheeseburger, AgricultureItems.bacon);
		addRecipe(AgricultureItems.chocolate, AgricultureItems.milk, new ItemStack(Item.dyePowder.itemID, 1, 3), Item.sugar);
		addRecipe(AgricultureItems.cheese, AgricultureItems.milk, AgricultureItems.vinegar);
		addRecipe(AgricultureItems.dough, AgricultureItems.water, AgricultureItems.flour);
		addRecipe(AgricultureItems.appleGelatin, AgricultureItems.ceramicPlate, AgricultureItems.water, AgricultureItems.gelatin, AgricultureItems.appleMush);
		addRecipe(AgricultureItems.strawberryGelatin, AgricultureItems.ceramicPlate, AgricultureItems.water, AgricultureItems.gelatin, AgricultureItems.strawberryMush);
		addRecipe(AgricultureItems.marshmellows, AgricultureItems.water, AgricultureItems.gelatin, Item.sugar);
		addRecipe(AgricultureItems.pastaDough, AgricultureItems.dough, Item.egg, AgricultureItems.salt);
		addRecipe(AgricultureItems.cheeseFries, AgricultureItems.frenchFries, AgricultureItems.cheese);
		addRecipe(AgricultureItems.rawChickenNuggets, AgricultureItems.groundChicken, AgricultureItems.breadCrumbs);
		addRecipe(AgricultureItems.cheeseburger, AgricultureItems.hamburger, AgricultureItems.sliceOfCheese);
		addRecipe(AgricultureItems.deluxeHotCocoa, AgricultureItems.hotCocoa, AgricultureItems.whippedCream, AgricultureItems.chocolate, AgricultureItems.marshmellows);
		addRecipe(AgricultureItems.saltedBeef.getItemStack(), new ItemStack(Item.beefRaw), AgricultureItems.salt);
		addRecipe(AgricultureItems.breadedChicken.getItemStack(), new ItemStack(Item.chickenRaw), AgricultureItems.breadCrumbs);
		addRecipe(AgricultureItems.saltedPork.getItemStack(), new ItemStack(Item.porkRaw), AgricultureItems.salt);
		addRecipe(AgricultureItems.caramelApple.getItemStack(), new ItemStack(Item.stick), Item.appleRed, AgricultureItems.caramel);
		addRecipe(AgricultureItems.chocolateCoveredStrawberries, AgricultureItems.strawberry, AgricultureItems.chocolate);
	
		addRecipe(AgricultureItems.appleCinnamonCookie, AgricultureItems.dough, AgricultureItems.appleMush, AgricultureItems.groundCinnamon);
		addRecipe(AgricultureItems.butterCookieDough, AgricultureItems.dough, AgricultureItems.butter);
		addRecipe(AgricultureItems.cheesyBaconPotatoes, AgricultureItems.cheesyPotatoes, AgricultureItems.bacon);
		addRecipe(AgricultureItems.cheesyPotatoes, AgricultureItems.mashedPotatos, AgricultureItems.cheese);
		addRecipe(AgricultureItems.chocolateChipCookieDough, AgricultureItems.dough, AgricultureItems.chocolate);
		addRecipe(AgricultureItems.chocolateIceCreamChocolateSauce, AgricultureItems.chocolateIceCream, AgricultureItems.chocolateSauce);
		addRecipe(AgricultureItems.chocolateIceCreamMix, AgricultureItems.iceCreamMix, AgricultureItems.chocolate);
		addRecipe(AgricultureItems.dicedPotatoes, AgricultureItems.ceramicPlate, Item.potato);
		addRecipe(AgricultureItems.doubleBaconCheeseburger, AgricultureItems.baconCheeseburger, AgricultureItems.baconCheeseburger);
		addRecipe(AgricultureItems.iceCreamMix, AgricultureItems.ceramicBowl, AgricultureItems.milk, Item.sugar);
		addRecipe(AgricultureItems.pbSandwich, AgricultureItems.ceramicPlate, AgricultureItems.sliceOfBread, AgricultureItems.sliceOfBread, AgricultureItems.peanutButter);
		addRecipe(AgricultureItems.peanutButterCookieDough, AgricultureItems.dough, AgricultureItems.peanutButter);
		addRecipe(AgricultureItems.pumpkinCookieDough, AgricultureItems.dough, new ItemStack(Block.pumpkin));
		addRecipe(AgricultureItems.snickerDoodleDough, AgricultureItems.dough, AgricultureItems.cinnamonAndSugar);
		addRecipe(AgricultureItems.strawberryIceCreamChocolateSauce, AgricultureItems.strawberryIceCream, AgricultureItems.chocolate);
		addRecipe(AgricultureItems.strawberryIceCreamMix, AgricultureItems.iceCreamMix, AgricultureItems.strawberry);
		addRecipe(AgricultureItems.strawberryJellyCookieDough, AgricultureItems.dough, AgricultureItems.strawberryJelly);
		addRecipe(AgricultureItems.sugarCookieDough, AgricultureItems.dough, Item.sugar);
		addRecipe(AgricultureItems.vanillaIceCreamChocolateSauce, AgricultureItems.vanillaIceCream, AgricultureItems.chocolateSauce);
		addRecipe(AgricultureItems.vanillaIceCreamMix, AgricultureItems.iceCreamMix, AgricultureItems.vanilla);
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
		for (int j = 0; j < this.recipes.size(); ++j)
		{
			ICounterRecipe irecipe = (ICounterRecipe) this.recipes.get(j);

			if (irecipe.matches(processor))
			{
				return irecipe.getCraftingResult(processor);
			}
		}

		return null;
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