package com.teammetallurgy.agriculture.nei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUICounter;
import com.teammetallurgy.agriculture.recipes.CounterRecipe;
import com.teammetallurgy.agriculture.recipes.CounterRecipes;
import com.teammetallurgy.agriculture.recipes.ProcessRecipe;
import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;

public class CounterHandler extends TemplateRecipeHandler
{
    class NEICounterRecipe extends CachedRecipe
    {

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public NEICounterRecipe(int width, int height, Object[] items, ItemStack out, ItemStack meh)
        {
            result = new PositionedStack(out, 135, 14);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(width, height, items, out);
        }

        private void setIngredients(int width, int height, Object[] items, ItemStack out)
        {

            PositionedStack stack = new PositionedStack(items[0], 11, 14);
            stack.setMaxSize(1);
            ingredients.add(stack);
            
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    if(x == 0 && y == 0)
                    {
                        continue;
                    }
                    
                    if ((x + y * width) >= items.length)
                    {
                        continue;
                    }

                    if (items[x + y * width] == null)
                    {
                        continue;
                    }

                    stack = new PositionedStack(items[x + y * width], 14 + x * 18, 14 + y * 18);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                }
            }

        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        ArrayList<CounterRecipe> allRecipes = CounterRecipes.getInstance().getRecipesUsing(ingredient);

        if (allRecipes == null)
        {
            return;
        }

        for (CounterRecipe recipe : allRecipes)
        {
            NEICounterRecipe recipeT = new NEICounterRecipe(4, 2, recipe.getIngredients(), recipe.getCraftingResult(), ingredient);
            arecipes.add(recipeT);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack ingredient)
    {
        ArrayList<CounterRecipe> allRecipes = CounterRecipes.getInstance().getRecipesFor(ingredient);

        if (allRecipes == null)
        {
            return;
        }

        for (CounterRecipe recipe : allRecipes)
        {
            NEICounterRecipe recipeT = new NEICounterRecipe(4, 2, recipe.getIngredients(), recipe.getCraftingResult(), ingredient);
            arecipes.add(recipeT);
        }
    }

    public String getRecipeName()
    {
        return "Prepaired Recipe";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GUICounter.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "agriculture:textures/gui/CounterNEI.png";
    }
}
