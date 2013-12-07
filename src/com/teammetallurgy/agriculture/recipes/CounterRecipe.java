package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CounterRecipe implements ICounterRecipe
{

    private final ItemStack recipeOutput;

    public final List<ItemStack> recipeItems;

    private final ItemStack baseItem;

    public CounterRecipe(ItemStack outputStack, ItemStack baseItem, List<ItemStack> par2List)
    {
        recipeOutput = outputStack;
        recipeItems = par2List;
        this.baseItem = baseItem;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    public ItemStack getCraftingResult(IInventory procesor)
    {
        return recipeOutput.copy();
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return recipeOutput.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return recipeItems.size();
    }

    @Override
    public boolean isMat(ItemStack stack)
    {        
        for (final ItemStack stack2 : recipeItems)
        {
            if (ItemStack.areItemStacksEqual(stack, stack2))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(IInventory processor)
    {
        final ArrayList<ItemStack> arraylist = new ArrayList<ItemStack>(recipeItems);

        for (int k = 0; k < 4; ++k)
        {
            final ItemStack stackInSlot = processor.getStackInSlot(k);
            if (stackInSlot != null)
            {

                final int itemID = stackInSlot.getItem().itemID;
                final int damage = stackInSlot.getItemDamage();
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
                    final ItemStack itemstack = processor.getStackInSlot(i + j * 4 + 4);

                    if (itemstack != null)
                    {
                        boolean flag = false;
                        final Iterator<ItemStack> iterator = arraylist.iterator();

                        while (iterator.hasNext())
                        {
                            final ItemStack itemstack1 = iterator.next();

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

    public ItemStack getCraftingResult()
    {
        return recipeOutput;
    }

    public ItemStack[] getIngredients()
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(baseItem);
        ret.addAll(recipeItems);

        return ret.toArray(new ItemStack[]
        {});
    }

    public boolean uses(ItemStack ingredient)
    {
        return baseItem.isItemEqual(ingredient) || isMat(ingredient);
    }

}
