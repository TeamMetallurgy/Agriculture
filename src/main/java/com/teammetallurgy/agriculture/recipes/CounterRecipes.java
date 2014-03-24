package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class CounterRecipes {
    /** The static instance of this class */
    private static final CounterRecipes instance = new CounterRecipes();

    /**
     * Returns the static instance of this class
     */
    public static final CounterRecipes getInstance()
    {
        return CounterRecipes.instance;
    }

    /** A list of all the recipes added */
    private final List recipes = new ArrayList();

    private CounterRecipes()
    {
        this.addRecipe(AgricultureItems.dough.getItemStack(), new ItemStack(Item.bucketWater), AgricultureItems.flour);
        this.addRecipe(AgricultureItems.rawHamburgerPatty, AgricultureItems.groundBeef, AgricultureItems.groundBeef);
        this.addRecipe(AgricultureItems.appleJellyToast, AgricultureItems.butteredToast, AgricultureItems.appleJelly);
        this.addRecipe(AgricultureItems.cinnamonToast, AgricultureItems.butteredToast, AgricultureItems.cinnamonAndSugar);
        this.addRecipe(AgricultureItems.strawberryJellyToast, AgricultureItems.butteredToast, AgricultureItems.strawberryJelly);
        this.addRecipe(AgricultureItems.caramelAppleWithNuts, AgricultureItems.caramelApple, AgricultureItems.crushedPeanuts);
        this.addRecipe(AgricultureItems.mashedPotatos, AgricultureItems.ceramicBowl, Item.bakedPotato, AgricultureItems.butter);
        this.addRecipe(AgricultureItems.carrotCakeBatter, AgricultureItems.ceramicBowl, AgricultureItems.batter, Item.carrot);
        this.addRecipe(AgricultureItems.rawFrenchToast, AgricultureItems.ceramicPlate, Item.bread, Item.egg, AgricultureItems.cinnamonAndSugar);
        this.addRecipe(AgricultureItems.batter, AgricultureItems.ceramicBowl, Item.egg, AgricultureItems.butter, AgricultureItems.flour);
        this.addRecipe(AgricultureItems.macaroniAndCheese, AgricultureItems.ceramicBowl, AgricultureItems.pasta, AgricultureItems.cheese);
        this.addRecipe(AgricultureItems.rawApplePie, AgricultureItems.ceramicPlate, AgricultureItems.dough, AgricultureItems.appleMush, AgricultureItems.cinnamonAndSugar);
        this.addRecipe(AgricultureItems.rawStrawberryPie, AgricultureItems.ceramicPlate, AgricultureItems.dough, AgricultureItems.strawberryMush, AgricultureItems.cinnamonAndSugar);
        this.addRecipe(AgricultureItems.strawberryShortcake, AgricultureItems.shortcake, AgricultureItems.strawberryJelly, AgricultureItems.strawberry, AgricultureItems.whippedCream);
        this.addRecipe(AgricultureItems.hamburger, AgricultureItems.ceramicPlate, AgricultureItems.sliceOfBread, AgricultureItems.hamburgerPatty, AgricultureItems.sliceOfBread);
        this.addRecipe(AgricultureItems.pbjSandwichApple, AgricultureItems.pbSandwich, AgricultureItems.appleJelly);
        this.addRecipe(AgricultureItems.pbjSandwichStrawberry, AgricultureItems.pbSandwich, AgricultureItems.strawberryJelly);
        this.addRecipe(AgricultureItems.cheeseSandwich, AgricultureItems.ceramicPlate, AgricultureItems.sliceOfBread, AgricultureItems.sliceOfCheese, AgricultureItems.sliceOfBread);
        this.addRecipe(AgricultureItems.butteredToast, AgricultureItems.toast, AgricultureItems.butter);
        this.addRecipe(AgricultureItems.baconCheeseFries, AgricultureItems.cheeseFries, AgricultureItems.bacon);
        this.addRecipe(AgricultureItems.baconCheeseburger, AgricultureItems.cheeseburger, AgricultureItems.bacon);
        this.addRecipe(AgricultureItems.chocolate, AgricultureItems.milk, new ItemStack(Item.dyePowder.itemID, 1, 3), Item.sugar);
        this.addRecipe(AgricultureItems.cheese, AgricultureItems.milk, AgricultureItems.vinegar);
        this.addRecipe(AgricultureItems.dough, AgricultureItems.water, AgricultureItems.flour);
        this.addRecipe(AgricultureItems.appleGelatin, AgricultureItems.ceramicPlate, AgricultureItems.water, AgricultureItems.gelatin, AgricultureItems.appleMush);
        this.addRecipe(AgricultureItems.strawberryGelatin, AgricultureItems.ceramicPlate, AgricultureItems.water, AgricultureItems.gelatin, AgricultureItems.strawberryMush);
        this.addRecipe(AgricultureItems.marshmellows, AgricultureItems.water, AgricultureItems.gelatin, Item.sugar);
        this.addRecipe(AgricultureItems.pastaDough, AgricultureItems.dough, Item.egg, AgricultureItems.salt);
        this.addRecipe(AgricultureItems.cheeseFries, AgricultureItems.frenchFries, AgricultureItems.cheese);
        this.addRecipe(AgricultureItems.rawChickenNuggets, AgricultureItems.groundChicken, AgricultureItems.breadCrumbs);
        this.addRecipe(AgricultureItems.cheeseburger, AgricultureItems.hamburger, AgricultureItems.sliceOfCheese);
        this.addRecipe(AgricultureItems.deluxeHotCocoa, AgricultureItems.hotCocoa, AgricultureItems.whippedCream, AgricultureItems.chocolate, AgricultureItems.marshmellows);
        this.addRecipe(AgricultureItems.saltedBeef.getItemStack(), new ItemStack(Item.beefRaw), AgricultureItems.salt);
        this.addRecipe(AgricultureItems.breadedChicken.getItemStack(), new ItemStack(Item.chickenRaw), AgricultureItems.breadCrumbs);
        this.addRecipe(AgricultureItems.saltedPork.getItemStack(), new ItemStack(Item.porkRaw), AgricultureItems.salt);
        this.addRecipe(AgricultureItems.caramelApple.getItemStack(), new ItemStack(Item.stick), Item.appleRed, AgricultureItems.caramel);
        this.addRecipe(AgricultureItems.chocolateCoveredStrawberries, AgricultureItems.strawberry, AgricultureItems.chocolate);

        this.addRecipe(AgricultureItems.appleCinnamonCookie, AgricultureItems.dough, AgricultureItems.appleMush, AgricultureItems.groundCinnamon);
        this.addRecipe(AgricultureItems.butterCookieDough, AgricultureItems.dough, AgricultureItems.butter);
        this.addRecipe(AgricultureItems.cheesyBaconPotatoes, AgricultureItems.cheesyPotatoes, AgricultureItems.bacon);
        this.addRecipe(AgricultureItems.cheesyPotatoes, AgricultureItems.mashedPotatos, AgricultureItems.cheese);
        this.addRecipe(AgricultureItems.chocolateChipCookieDough, AgricultureItems.dough, AgricultureItems.chocolate);
        this.addRecipe(AgricultureItems.chocolateIceCreamChocolateSauce, AgricultureItems.chocolateIceCream, AgricultureItems.chocolateSauce);
        this.addRecipe(AgricultureItems.chocolateIceCreamMix, AgricultureItems.iceCreamMix, AgricultureItems.chocolate);
        this.addRecipe(AgricultureItems.dicedPotatoes, AgricultureItems.ceramicPlate, Item.potato);
        this.addRecipe(AgricultureItems.doubleBaconCheeseburger, AgricultureItems.baconCheeseburger, AgricultureItems.baconCheeseburger);
        this.addRecipe(AgricultureItems.iceCreamMix, AgricultureItems.ceramicBowl, AgricultureItems.milk, Item.sugar);
        this.addRecipe(AgricultureItems.pbSandwich, AgricultureItems.ceramicPlate, AgricultureItems.sliceOfBread, AgricultureItems.sliceOfBread, AgricultureItems.peanutButter);
        this.addRecipe(AgricultureItems.peanutButterCookieDough, AgricultureItems.dough, AgricultureItems.peanutButter);
        this.addRecipe(AgricultureItems.pumpkinCookieDough, AgricultureItems.dough, new ItemStack(Block.pumpkin));
        this.addRecipe(AgricultureItems.snickerDoodleDough, AgricultureItems.dough, AgricultureItems.cinnamonAndSugar);
        this.addRecipe(AgricultureItems.strawberryIceCreamChocolateSauce, AgricultureItems.strawberryIceCream, AgricultureItems.chocolate);
        this.addRecipe(AgricultureItems.strawberryIceCreamMix, AgricultureItems.iceCreamMix, AgricultureItems.strawberry);
        this.addRecipe(AgricultureItems.strawberryJellyCookieDough, AgricultureItems.dough, AgricultureItems.strawberryJelly);
        this.addRecipe(AgricultureItems.sugarCookieDough, AgricultureItems.dough, Item.sugar);
        this.addRecipe(AgricultureItems.vanillaIceCreamChocolateSauce, AgricultureItems.vanillaIceCream, AgricultureItems.chocolateSauce);
        this.addRecipe(AgricultureItems.vanillaIceCreamMix, AgricultureItems.iceCreamMix, AgricultureItems.vanilla);
    }

    public void addRecipe(final ItemStack par1ItemStack, final ItemStack baseItem, final Object... par2ArrayOfObj)
    {
        final ArrayList arraylist = new ArrayList();
        final Object[] aobject = par2ArrayOfObj;
        final int i = par2ArrayOfObj.length;

        for (int j = 0; j < i; ++j)
        {
            final Object object1 = aobject[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack) object1).copy());
            }
            else if (object1 instanceof SubItem)
            {
                arraylist.add(((SubItem) object1).getItemStack());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item) object1));
            }
            else
            {
                if (!(object1 instanceof Block)) { throw new RuntimeException("Invalid shapeless recipy!"); }

                arraylist.add(new ItemStack((Block) object1));
            }
        }

        recipes.add(new CounterRecipe(par1ItemStack, baseItem, arraylist));
    }

    public void addRecipe(final SubItem item, final SubItem baseItem, final Object... par2ArrayOfObj)
    {
        this.addRecipe(item.getItemStack(), baseItem.getItemStack(), par2ArrayOfObj);
    }

    public ItemStack findMatchingRecipe(final IInventory processor)
    {
        for (int j = 0; j < recipes.size(); ++j)
        {
            final ICounterRecipe irecipe = (ICounterRecipe) recipes.get(j);

            if (irecipe.matches(processor)) { return irecipe.getCraftingResult(processor); }
        }

        return null;
    }

    /**
     * returns the List<> of all recipes
     */
    public List getRecipeList()
    {
        return recipes;
    }

    public ArrayList<CounterRecipe> getRecipesFor(final ItemStack ingredient)
    {
        final ArrayList<CounterRecipe> list = new ArrayList<CounterRecipe>();

        for (int j = 0; j < recipes.size(); ++j)
        {
            final CounterRecipe irecipe = (CounterRecipe) recipes.get(j);

            if (irecipe.getCraftingResult().isItemEqual(ingredient))
            {
                list.add(irecipe);
            }
        }

        return list;
    }

    public ArrayList<CounterRecipe> getRecipesUsing(final ItemStack ingredient)
    {
        final ArrayList<CounterRecipe> list = new ArrayList<CounterRecipe>();

        for (int j = 0; j < recipes.size(); ++j)
        {
            final CounterRecipe irecipe = (CounterRecipe) recipes.get(j);

            if (irecipe.uses(ingredient))
            {
                list.add(irecipe);
            }
        }

        return list;
    }

    public boolean isMat(final ItemStack findMatchingRecipe, final ItemStack stack, final IInventory processor)
    {
        for (int j = 0; j < recipes.size(); ++j)
        {
            final ICounterRecipe irecipe = (ICounterRecipe) recipes.get(j);

            if (ItemStack.areItemStacksEqual(irecipe.getRecipeOutput(), findMatchingRecipe)) { return irecipe.isMat(stack); }
        }
        return false;

    }
}