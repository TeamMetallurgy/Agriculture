//package com.teammetallurgy.agriculture.nei;
//
//import java.util.ArrayList;
//import java.util.TreeSet;
//
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntityFurnace;
//import codechicken.nei.ItemList;
//import codechicken.nei.PositionedStack;
//import codechicken.nei.recipe.FurnaceRecipeHandler.FuelPair;
//import codechicken.nei.recipe.TemplateRecipeHandler;
//
//import com.teammetallurgy.agriculture.gui.GUIOven;
//import com.teammetallurgy.agriculture.recipes.OvenRecipe;
//import com.teammetallurgy.agriculture.recipes.OvenRecipes;
//import com.teammetallurgy.agriculture.recipes.TempRecipe;
//
//public class OvenHandler extends TemplateRecipeHandler {
//
//    protected class LocalFuelPair extends FuelPair {
//
//        public LocalFuelPair(final ItemStack ingred, final int burnTime)
//        {
//            super(ingred, burnTime);
//            stack = new PositionedStack(ingred, 7, OvenHandler.YPOSITION, false);
//        }
//    }
//
//    private class NEIBakedRecipe extends CachedRecipe {
//
//        public PositionedStack ingredient;
//        public PositionedStack result;
//
//        public NEIBakedRecipe(final ItemStack input, final ItemStack output)
//        {
//            result = new PositionedStack(output, 98, OvenHandler.YPOSITION);
//            ingredient = new PositionedStack(input, 51, OvenHandler.YPOSITION);
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
//            return OvenHandler.afuels.get(OvenHandler.this.cycleticks / 48 % OvenHandler.afuels.size()).stack;
//        }
//
//        @Override
//        public PositionedStack getResult()
//        {
//            return result;
//        }
//
//    }
//
//    private static ArrayList<LocalFuelPair> afuels;
//
//    private static TreeSet<Integer> efuels;
//
//    private static final int YPOSITION = 23;
//
//    static
//    {
//        OvenHandler.removeFuels();
//    }
//
//    private static void removeFuels()
//    {
//        OvenHandler.efuels = new TreeSet<Integer>();
//    }
//
//    protected void addRecipes(final ArrayList<? extends TempRecipe> allRecipes)
//    {
//        for (final TempRecipe recipe : allRecipes)
//        {
//            final NEIBakedRecipe recipeT = new NEIBakedRecipe(recipe.getInput(), recipe.getResult());
//            arecipes.add(recipeT);
//        }
//    }
//
//    @Override
//    public void drawExtras(final int recipe)
//    {
//        this.drawProgressBar(75, 25, 179, 89, 14, 14, 100, 3);
//    }
//
//    protected void findFuels()
//    {
//        OvenHandler.afuels = new ArrayList<LocalFuelPair>();
//        for (final ItemStack item : ItemList.items)
//        {
//            if (!OvenHandler.efuels.contains(item.itemID))
//            {
//                final int burnTime = getItemBurnTime(item);
//                if (burnTime > 0)
//                {
//                    OvenHandler.afuels.add(new LocalFuelPair(item.copy(), burnTime));
//                }
//            }
//        }
//    }
//
//    @Override
//    public Class<? extends GuiContainer> getGuiClass()
//    {
//        return GUIOven.class;
//    }
//
//    @Override
//    public String getGuiTexture()
//    {
//        return "agriculture:textures/gui/OvenNEI.png";
//    }
//
//    protected int getItemBurnTime(final ItemStack item)
//    {
//        return TileEntityFurnace.getItemBurnTime(item);
//    }
//
//    @Override
//    public String getRecipeName()
//    {
//        return "Baked Recipe";
//    }
//
//    protected boolean isFuel(final ItemStack ingredient)
//    {
//        for (final LocalFuelPair fuel : OvenHandler.afuels)
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
//        final ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getRecipesFor(ingredient);
//
//        addRecipes(allRecipes);
//    }
//
//    @Override
//    public void loadCraftingRecipes(final String outputId, final Object... results)
//    {
//        if (outputId.equals("baked") && this.getClass() == OvenHandler.class)
//        {
//            final ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getRecipes();
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
//        // transferRects.add(new RecipeTransferRect(new Rectangle(75, 25, 14,
//        // 14), "baked"));
//        // transferRects.add(new RecipeTransferRect(new Rectangle(7, YPOSITION,
//        // 16, 16), "fuel"));
//    }
//
//    @Override
//    public void loadUsageRecipes(final ItemStack ingredient)
//    {
//        final ArrayList<OvenRecipe> allRecipes = OvenRecipes.getInstance().getUsageFor(ingredient);
//
//        if (isFuel(ingredient))
//        {
//            this.loadUsageRecipes("fuel");
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
//        if (inputId.equals("fuel") && this.getClass() == OvenHandler.class)
//        {
//            this.loadCraftingRecipes("baked");
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
//        if (OvenHandler.afuels == null)
//        {
//            findFuels();
//        }
//        return super.newInstance();
//    }
//}
