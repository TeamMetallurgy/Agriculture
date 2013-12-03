package com.teammetallurgy.agriculture.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.TreeSet;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUIIcebox;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.recipes.FreezerRecipe;
import com.teammetallurgy.agriculture.recipes.FreezerRecipes;
import com.teammetallurgy.agriculture.recipes.TempRecipe;

public class IceBoxHandler extends TemplateRecipeHandler
{
    private static final int YPOSITION = 22;
    
    private static ArrayList<LocalFuelPair> afuels;
    private static TreeSet<Integer> efuels;

    
    private class NEIFreezerRecipe extends CachedRecipe
    {
        public PositionedStack ingredient;
        public PositionedStack result;

        public NEIFreezerRecipe(ItemStack input, ItemStack output)
        {
            result = new PositionedStack(output, 101, YPOSITION);
            ingredient = new PositionedStack(input, 48, YPOSITION);
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
//        transferRects.add(new RecipeTransferRect(new Rectangle(71, 27, 25, 7), "frozen"));
//        transferRects.add(new RecipeTransferRect(new Rectangle(6, YPOSITION, 16, 16), "coldfuel"));
    }
    
    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if(inputId.equals("coldfuel") && getClass() == IceBoxHandler.class)
        {
            loadCraftingRecipes("frozen");
        }
        else
        {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getUsageFor(ingredient);
        
        if (isFuel(ingredient))
        {
            loadUsageRecipes("coldfuel");
        }
        else 
        {
            addRecipes(allRecipes);
        }
    }
    
    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("frozen") && getClass() == IceBoxHandler.class)
        {
            ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getRecipes();
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
        ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getRecipesFor(ingredient);

        addRecipes(allRecipes);
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
        return "agriculture:textures/gui/IceboxNEI.png";
    }
    
    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(71, 27, 179, 11, 25, 7, 100, 0);
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
            NEIFreezerRecipe recipeT = new NEIFreezerRecipe(recipe.getInput(), recipe.getResult());
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
            this.stack = new PositionedStack(ingred, 6, YPOSITION, false);
        }
    }

    protected int getItemBurnTime(ItemStack item)
    {
        return TileEntityIcebox.getItemBurnTime(item);
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
