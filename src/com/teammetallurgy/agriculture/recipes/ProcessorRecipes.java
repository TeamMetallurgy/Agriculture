package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class ProcessorRecipes {
    /** The static instance of this class */
    private static final ProcessorRecipes instance = new ProcessorRecipes();

    /**
     * Returns the static instance of this class
     */
    public static final ProcessorRecipes getInstance()
    {
        return ProcessorRecipes.instance;
    }

    public static int getProcessTime(final ItemStack stackInSlot)
    {
        return stackInSlot != null ? 20 : 0;
    }

    /** A list of all the recipes added */
    private final List<ProcessRecipe> recipes = new ArrayList<ProcessRecipe>();

    private ProcessorRecipes()
    {
        this.addRecipe(AgricultureItems.appleMush, Item.sugar, AgricultureItems.appleJelly);
        this.addRecipe(Item.appleRed, AgricultureItems.appleMush);
        this.addRecipe(Item.bread, AgricultureItems.breadCrumbs.getItemStack(8));
        this.addRecipe(AgricultureItems.milk, AgricultureItems.butter);
        this.addRecipe(AgricultureItems.groundCinnamon, Item.sugar, AgricultureItems.cinnamonAndSugar);
        this.addRecipe(AgricultureItems.roastedPeanuts, AgricultureItems.water, AgricultureItems.cookingOil);
        this.addRecipe(AgricultureItems.roastedPeanuts, AgricultureItems.crushedPeanuts);
        this.addRecipe(Item.wheat, AgricultureItems.flour);
        this.addRecipe(Item.bone, AgricultureItems.gelatin);
        this.addRecipe(Item.beefRaw, AgricultureItems.groundBeef.getItemStack(2));
        this.addRecipe(Item.chickenRaw, AgricultureItems.groundChicken.getItemStack(2));
        this.addRecipe(AgricultureItems.cinnamon, AgricultureItems.groundCinnamon);
        this.addRecipe(Item.porkRaw, AgricultureItems.groundPork.getItemStack(2));
        this.addRecipe(AgricultureItems.crushedPeanuts, Item.sugar, AgricultureItems.peanutButter);
        this.addRecipe(AgricultureItems.strawberryMush, Item.sugar, AgricultureItems.strawberryJelly);
        this.addRecipe(AgricultureItems.strawberry, AgricultureItems.strawberryMush);
        this.addRecipe(AgricultureItems.milk, Item.sugar, AgricultureItems.whippedCream);
    }

    public void addRecipe(final Object item, final Object result)
    {
        this.addRecipe(item, null, result);
    }

    public void addRecipe(final Object par1ItemStack, final Object baseItem, final Object result)
    {
        recipes.add(new ProcessRecipe(getItemStack(par1ItemStack), getItemStack(baseItem), getItemStack(result)));
    }

    public ItemStack findMatchingRecipe(final ItemStack first, final ItemStack second)
    {
        for (int j = 0; j < recipes.size(); ++j)
        {
            final ProcessRecipe irecipe = recipes.get(j);

            if (irecipe.matches(first, second)) { 
                return irecipe.getCraftingResult();
                }
        }

        return null;
    }

    public ArrayList<ProcessRecipe> getAllRecipes(final ItemStack ingredient)
    {
        final ArrayList<ProcessRecipe> recipesTemp = new ArrayList<ProcessRecipe>();

        for (int j = 0; j < recipes.size(); ++j)
        {
            final ProcessRecipe irecipe = recipes.get(j);

            if (irecipe.uses(ingredient))
            {
                recipesTemp.add(irecipe);
            }
        }

        return recipesTemp;
    }

    public ItemStack getItemStack(final Object object)
    {
        ItemStack stack = null;
        if (object instanceof ItemStack)
        {
            stack = (ItemStack) object;
        }
        else if (object instanceof SubItem)
        {
            stack = ((SubItem) object).getItemStack();
        }
        else if (object instanceof Item)
        {
            stack = new ItemStack((Item) object);
        }
        return stack;
    }

    public ArrayList<ProcessRecipe> getRecipesFor(final ItemStack ingredient)
    {
        final ArrayList<ProcessRecipe> recipesTemp = new ArrayList<ProcessRecipe>();

        for (int j = 0; j < recipes.size(); ++j)
        {
            final ProcessRecipe irecipe = recipes.get(j);

            if (irecipe.getCraftingResult().isItemEqual(ingredient))
            {
                recipesTemp.add(irecipe);
            }
        }

        return recipesTemp;
    }
}
