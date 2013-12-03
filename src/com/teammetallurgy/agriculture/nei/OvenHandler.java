package com.teammetallurgy.agriculture.nei;

import java.util.ArrayList;
import java.util.TreeSet;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUIOven;
import com.teammetallurgy.agriculture.recipes.OvenRecipe;
import com.teammetallurgy.agriculture.recipes.OvenRecipes;
import com.teammetallurgy.agriculture.recipes.TempRecipe;

public class OvenHandler extends TemplateRecipeHandler  
{

    private static final int YPOSITION = 23;
    
    private static ArrayList<LocalFuelPair> afuels;
    private static TreeSet<Integer> efuels;

    
    private class NEIBakedRecipe extends CachedRecipe
    {

        public PositionedStack ingredient;
        public PositionedStack result;

        public NEIBakedRecipe(ItemStack input, ItemStack output)
        {
            result = new PositionedStack(output, 98, YPOSITION);
            ingredient = new PositionedStack(input, 51, YPOSITION);
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
        
        @Override
        public PositionedStack getOtherStack() 
        {
            return afuels.get((cycleticks/48) % afuels.size()).stack;
        }

        
    }
    
    @Override
    public void loadTransferRects()
    {
//        transferRects.add(new RecipeTransferRect(new Rectangle(75, 25, 14, 14), "baked"));
//        transferRects.add(new RecipeTransferRect(new Rectangle(7, YPOSITION, 16, 16), "fuel"));
    }
    
    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if(inputId.equals("fuel") && getClass() == OvenHandler.class)
        {
            loadCraftingRecipes("baked");
        }
        else
        {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getUsageFor(ingredient);
        
        if (isFuel(ingredient))
        {
            loadUsageRecipes("fuel");
        }
        else 
        {
            addRecipes(allRecipes);
        }
    }
    
    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("baked") && getClass() == OvenHandler.class)
        {
            ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getRecipes();
            addRecipes(allRecipes);
        }
        else 
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    @Override
    public void loadCraftingRecipes(ItemStack ingredient)
    {
        ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getRecipesFor(ingredient);

        addRecipes(allRecipes);
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
        return "agriculture:textures/gui/OvenNEI.png";
    }
    
    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(75, 25, 179, 89, 14, 14, 100, 3);
    }
    
    protected boolean isFuel(ItemStack ingredient)
    {
        for(LocalFuelPair fuel : afuels)
        {   
            if(fuel != null && ingredient.isItemEqual(fuel.stack.item))
            {
                return true;
            }
        }
        
        return false;
    }

    protected void addRecipes(ArrayList<? extends TempRecipe> allRecipes)
    {
        for (TempRecipe recipe : allRecipes)
        {
            NEIBakedRecipe recipeT = new NEIBakedRecipe(recipe.getInput(), recipe.getResult());
            arecipes.add(recipeT);
        }
    }

    @Override
    public TemplateRecipeHandler newInstance()
    {
        if(afuels == null)
        {
            findFuels();
        }
        return super.newInstance();
    }

    protected void findFuels()
    {
        afuels = new ArrayList<LocalFuelPair>();
        for(ItemStack item : ItemList.items)
        {
            if(!efuels.contains(item.itemID))
            {
                int burnTime = getItemBurnTime(item);
                if(burnTime > 0)
                    afuels.add(new LocalFuelPair(item.copy(), burnTime));
            }
        }
    }
    
    protected class LocalFuelPair extends FuelPair
    {

        public LocalFuelPair(ItemStack ingred, int burnTime)
        {
            super(ingred, burnTime);
            this.stack = new PositionedStack(ingred, 7, YPOSITION, false);
        }
    }

    protected int getItemBurnTime(ItemStack item)
    {
        return TileEntityFurnace.getItemBurnTime(item);
    }
    
    static 
    {
        removeFuels();
    }

    private static void removeFuels()
    {
        efuels = new TreeSet<Integer>(); 
    }
}
