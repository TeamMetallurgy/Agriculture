package com.teammetallurgy.agriculture.nei;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUIOven;
import com.teammetallurgy.agriculture.recipes.OvenRecipe;
import com.teammetallurgy.agriculture.recipes.OvenRecipes;

public class OvenHandler extends TemplateRecipeHandler
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
        ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getUsageFor(ingredient);

        for (OvenRecipe recipe : allRecipes)
        {
            NEIOvenRecipe recipeT = new NEIOvenRecipe(recipe.getInput(), recipe.getResult(), ingredient);
            arecipes.add(recipeT);
        }
    }
    
    @Override
    public void loadCraftingRecipes(ItemStack ingredient)
    {
        ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getRecipesFor(ingredient);

        for (OvenRecipe recipe : allRecipes)
        {
            NEIOvenRecipe recipeT = new NEIOvenRecipe(recipe.getInput(), recipe.getResult(), ingredient);
            arecipes.add(recipeT);
        }
    }

    public String getRecipeName()
    {
        return "Baked Recipe";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GUIOven.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "agriculture:textures/gui/Oven.png";
    }
}
