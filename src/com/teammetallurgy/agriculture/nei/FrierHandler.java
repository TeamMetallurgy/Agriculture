package com.teammetallurgy.agriculture.nei;

import java.util.ArrayList;
import java.util.TreeSet;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;
import codechicken.nei.ItemList;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.agriculture.gui.GUIFrier;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.recipes.FrierRecipe;
import com.teammetallurgy.agriculture.recipes.FrierRecipes;

public class FrierHandler extends TemplateRecipeHandler
{
private static final int YPOSITION = 24;
    
    private static ArrayList<LocalFuelPair> afuels;
    private static TreeSet<Integer> efuels;

    
    private class NEIDeepFriedRecipe extends CachedRecipe
    {
        public PositionedStack ingredient;
        public PositionedStack result;
        public PositionedTank tank;

        public NEIDeepFriedRecipe(ItemStack input, ItemStack output)
        {
            result = new PositionedStack(output, 102, YPOSITION);
            ingredient = new PositionedStack(input, 66, YPOSITION);
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
    
    private class PositionedTank
    {
        public FluidTank tank;
        public int x;
        public int y;
        
        public PositionedTank(FluidTank tank, int x, int y)
        {
            this.tank = tank;
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void loadTransferRects()
    {
//        transferRects.add(new RecipeTransferRect(new Rectangle(85, 25, 14, 14), "fried"));
//        transferRects.add(new RecipeTransferRect(new Rectangle(17, YPOSITION, 16, 16), "oils"));
    }
    
    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if(inputId.equals("oils") && getClass() == FrierHandler.class)
        {
            loadCraftingRecipes("fried");
        }
        else
        {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getUsageFor(ingredient);
        
        if (isFuel(ingredient))
        {
            loadUsageRecipes("oils");
        }
        else 
        {
            addRecipes(allRecipes);
        }
    }
    
    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("fried") && getClass() == FrierHandler.class)
        {
            ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getRecipes();
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
        ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getRecipesFor(ingredient);

        addRecipes(allRecipes);
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
        return "agriculture:textures/gui/FrierNEI.png";
    }
    
    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(85, 25, 179, 72, 14, 14, 100, 3);
    }

    @Override
        public void drawBackground(int recipe)
        {
            super.drawBackground(recipe);
            drawTank(recipe);
        }
    
    private void drawTank(int recipe)
    {
        CachedRecipe cachedRecipe = arecipes.get(recipe);
        
        if(cachedRecipe instanceof NEIDeepFriedRecipe)
        {
            PositionedTank tank = ((NEIDeepFriedRecipe) cachedRecipe).tank;
        }
        
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

    protected void addRecipes(ArrayList<FrierRecipe> allRecipes)
    {
        for (FrierRecipe recipe : allRecipes)
        {
            NEIDeepFriedRecipe recipeT = new NEIDeepFriedRecipe(recipe.getInput(), recipe.getResult());
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
            this.stack = new PositionedStack(ingred, 17, YPOSITION, false);
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
