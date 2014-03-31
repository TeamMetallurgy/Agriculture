//package com.teammetallurgy.agriculture.nei;
//
//import java.util.ArrayList;
//import java.util.TreeSet;
//
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.item.ItemStack;
//import codechicken.nei.ItemList;
//import codechicken.nei.PositionedStack;
//import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
//import codechicken.nei.recipe.TemplateRecipeHandler;
//
//import com.teammetallurgy.agriculture.gui.GUIFrier;
//import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
//import com.teammetallurgy.agriculture.recipes.FrierRecipe;
//import com.teammetallurgy.agriculture.recipes.FrierRecipes;
//
//public class FrierHandler extends TemplateRecipeHandler {
//    protected class LocalFuelPair extends FuelPair {
//
//        public LocalFuelPair(final ItemStack ingred, final int burnTime)
//        {
//            super(ingred, burnTime);
//            stack = new PositionedStack(ingred, 17, FrierHandler.YPOSITION, false);
//        }
//    }
//
//    private class NEIDeepFriedRecipe extends CachedRecipe {
//        public PositionedStack ingredient;
//        public PositionedStack result;
//
//        public NEIDeepFriedRecipe(final ItemStack input, final ItemStack output)
//        {
//            result = new PositionedStack(output, 102, FrierHandler.YPOSITION);
//            ingredient = new PositionedStack(input, 66, FrierHandler.YPOSITION);
//        }
//
//        @Override
//        public PositionedStack getIngredient()
//        {
//            return ingredient;
//        }
//
//        @Override
//        public PositionedStack getOtherStack()
//        {
//            return FrierHandler.afuels.get(FrierHandler.this.cycleticks / 48 % FrierHandler.afuels.size()).stack;
//        }
//
//        @Override
//        public PositionedStack getResult()
//        {
//            return result;
//        }
//    }
//
//    private class PositionedTank {
//    }
//
//    private static ArrayList<LocalFuelPair> afuels;
//
//    private static TreeSet<Integer> efuels;
//
//    private static final int YPOSITION = 24;
//
//    static
//    {
//        FrierHandler.removeFuels();
//    }
//
//    private static void removeFuels()
//    {
//        FrierHandler.efuels = new TreeSet<Integer>();
//    }
//
//    protected void addRecipes(final ArrayList<FrierRecipe> allRecipes)
//    {
//        for (final FrierRecipe recipe : allRecipes)
//        {
//            final NEIDeepFriedRecipe recipeT = new NEIDeepFriedRecipe(recipe.getInput(), recipe.getResult());
//            arecipes.add(recipeT);
//        }
//    }
//
//    @Override
//    public void drawBackground(final int recipe)
//    {
//        super.drawBackground(recipe);
//        drawTank(recipe);
//    }
//
//    @Override
//    public void drawExtras(final int recipe)
//    {
//        this.drawProgressBar(85, 25, 179, 72, 14, 14, 100, 3);
//    }
//
//    private void drawTank(final int recipe)
//    {
//        final CachedRecipe cachedRecipe = arecipes.get(recipe);
//
//        if (cachedRecipe instanceof NEIDeepFriedRecipe)
//        {
//        }
//
//    }
//
//    protected void findFuels()
//    {
//        FrierHandler.afuels = new ArrayList<LocalFuelPair>();
//        for (final ItemStack item : ItemList.items)
//        {
//            if (!FrierHandler.efuels.contains(item.itemID))
//            {
//                final int burnTime = getItemBurnTime(item);
//                if (burnTime > 0)
//                {
//                    FrierHandler.afuels.add(new LocalFuelPair(item.copy(), burnTime));
//                }
//            }
//        }
//    }
//
//    @Override
//    public Class<? extends GuiContainer> getGuiClass()
//    {
//        return GUIFrier.class;
//    }
//
//    @Override
//    public String getGuiTexture()
//    {
//        return "agriculture:textures/gui/FrierNEI.png";
//    }
//
//    protected int getItemBurnTime(final ItemStack item)
//    {
//        return TileEntityIcebox.getItemBurnTime(item);
//    }
//
//    @Override
//    public String getRecipeName()
//    {
//        return "Deep Fried Recipe";
//    }
//
//    protected boolean isFuel(final ItemStack ingredient)
//    {
//        for (final LocalFuelPair fuel : FrierHandler.afuels)
//        {
//            if (fuel != null && ingredient.isItemEqual(fuel.stack.item)) { return true; }
//        }
//
//        return false;
//    }
//
//    @Override
//    public void loadCraftingRecipes(final ItemStack ingredient)
//    {
//        final ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getRecipesFor(ingredient);
//
//        addRecipes(allRecipes);
//    }
//
//    @Override
//    public void loadCraftingRecipes(final String outputId, final Object... results)
//    {
//        if (outputId.equals("fried") && this.getClass() == FrierHandler.class)
//        {
//            final ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getRecipes();
//            addRecipes(allRecipes);
//        }
//        else
//        {
//            super.loadCraftingRecipes(outputId, results);
//        }
//    }
//
//    @Override
//    public void loadTransferRects()
//    {
//        // transferRects.add(new RecipeTransferRect(new Rectangle(85, 25, 14,
//        // 14), "fried"));
//        // transferRects.add(new RecipeTransferRect(new Rectangle(17, YPOSITION,
//        // 16, 16), "oils"));
//    }
//
//    @Override
//    public void loadUsageRecipes(final ItemStack ingredient)
//    {
//        final ArrayList<FrierRecipe> allRecipes = FrierRecipes.getInstance().getUsageFor(ingredient);
//
//        if (isFuel(ingredient))
//        {
//            this.loadUsageRecipes("oils");
//        }
//        else
//        {
//            addRecipes(allRecipes);
//        }
//    }
//
//    @Override
//    public void loadUsageRecipes(final String inputId, final Object... ingredients)
//    {
//        if (inputId.equals("oils") && this.getClass() == FrierHandler.class)
//        {
//            this.loadCraftingRecipes("fried");
//        }
//        else
//        {
//            super.loadUsageRecipes(inputId, ingredients);
//        }
//    }
//
//    @Override
//    public TemplateRecipeHandler newInstance()
//    {
//        if (FrierHandler.afuels == null)
//        {
//            findFuels();
//        }
//        return super.newInstance();
//    }
//}
