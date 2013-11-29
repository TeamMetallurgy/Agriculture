package com.teammetallurgy.agriculture.nei;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUIFrier;
import com.teammetallurgy.agriculture.recipes.FrierRecipe;
import com.teammetallurgy.agriculture.recipes.FrierRecipes;

public class FrierHandler extends TemplateRecipeHandler
{
    class NEIOvenRecipe extends CachedRecipe
    {

        public PositionedStack ingredient;
        public PositionedStack result;

        public NEIOvenRecipe(ItemStack input, ItemStack output, ItemStack meh)
        {
            result = new PositionedStack(output, 98, 21);
            ingredient = new PositionedStack(input, 98, 45);
        }

        @Override
        public PositionedStack getIngredient()
        {
            return ingredient;
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
        ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getUsageFor(ingredient);

        for (FrierRecipe recipe : allRecipes)
        {
            NEIOvenRecipe recipeT = new NEIOvenRecipe(recipe.getInput(), recipe.getResult(), ingredient);
            arecipes.add(recipeT);
        }
    }
    
    @Override
    public void loadCraftingRecipes(ItemStack ingredient)
    {
        ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getRecipesFor(ingredient);

        for (FrierRecipe recipe : allRecipes)
        {
            NEIOvenRecipe recipeT = new NEIOvenRecipe(recipe.getInput(), recipe.getResult(), ingredient);
            arecipes.add(recipeT);
        }
    }

    public String getRecipeName()
    {
        return "Deep Fried Recipe";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GUIFrier.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "agriculture:textures/gui/Frier.png";
    }
}
