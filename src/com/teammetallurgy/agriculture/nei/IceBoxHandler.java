package com.teammetallurgy.agriculture.nei;

import java.util.ArrayList;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUIIcebox;
import com.teammetallurgy.agriculture.recipes.FreezerRecipe;
import com.teammetallurgy.agriculture.recipes.FreezerRecipes;

public class IceBoxHandler extends TemplateRecipeHandler
{
    class NEIFreezerRecipe extends CachedRecipe
    {

        public PositionedStack ingredient;
        public PositionedStack result;

        public NEIFreezerRecipe(ItemStack input, ItemStack output, ItemStack meh)
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
        ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getUsageFor(ingredient);

        for (FreezerRecipe recipe : allRecipes)
        {
            NEIFreezerRecipe recipeT = new NEIFreezerRecipe(recipe.getInput(), recipe.getResult(), ingredient);
            arecipes.add(recipeT);
        }
    }
    
    @Override
    public void loadCraftingRecipes(ItemStack ingredient)
    {
        ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getRecipesFor(ingredient);

        for (FreezerRecipe recipe : allRecipes)
        {
            NEIFreezerRecipe recipeT = new NEIFreezerRecipe(recipe.getInput(), recipe.getResult(), ingredient);
            arecipes.add(recipeT);
        }
    }

    public String getRecipeName()
    {
        return "Frozen Recipe";
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GUIIcebox.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "agriculture:textures/gui/Icebox.png";
    }
}
