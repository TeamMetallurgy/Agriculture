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
//import com.teammetallurgy.agriculture.gui.GUIIcebox;
//import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
//import com.teammetallurgy.agriculture.recipes.FreezerRecipe;
//import com.teammetallurgy.agriculture.recipes.FreezerRecipes;
//import com.teammetallurgy.agriculture.recipes.TempRecipe;
//
//public class IceBoxHandler extends TemplateRecipeHandler {
//    protected class LocalFuelPair extends FuelPair {
//
//        public LocalFuelPair(final ItemStack ingred, final int burnTime)
//        {
//            super(ingred, burnTime);
//            stack = new PositionedStack(ingred, 6, IceBoxHandler.YPOSITION, false);
//        }
//    }
//
//    private class NEIFreezerRecipe extends CachedRecipe {
//        public PositionedStack ingredient;
//        public PositionedStack result;
//
//        public NEIFreezerRecipe(final ItemStack input, final ItemStack output)
//        {
//            result = new PositionedStack(output, 101, IceBoxHandler.YPOSITION);
//            ingredient = new PositionedStack(input, 48, IceBoxHandler.YPOSITION);
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
//            return IceBoxHandler.afuels.get(IceBoxHandler.this.cycleticks / 48 % IceBoxHandler.afuels.size()).stack;
//        }
//
//        @Override
//        public PositionedStack getResult()
//        {
//            return result;
//        }
//    }
//
//    private static ArrayList<LocalFuelPair> afuels;
//
//    private static TreeSet<Integer> efuels;
//
//    private static final int YPOSITION = 22;
//
//    static
//    {
//        IceBoxHandler.removeFuels();
//    }
//
//    private static void removeFuels()
//    {
//        IceBoxHandler.efuels = new TreeSet<Integer>();
//    }
//
//    protected void addRecipes(final ArrayList<? extends TempRecipe> allRecipes)
//    {
//        for (final TempRecipe recipe : allRecipes)
//        {
//            final NEIFreezerRecipe recipeT = new NEIFreezerRecipe(recipe.getInput(), recipe.getResult());
//            arecipes.add(recipeT);
//        }
//    }
//
//    @Override
//    public void drawExtras(final int recipe)
//    {
//        this.drawProgressBar(71, 27, 179, 11, 25, 7, 100, 0);
//    }
//
//    protected void findFuels()
//    {
//        IceBoxHandler.afuels = new ArrayList<LocalFuelPair>();
//        for (final ItemStack item : ItemList.items)
//        {
//            if (!IceBoxHandler.efuels.contains(item.itemID))
//            {
//                final int burnTime = getItemBurnTime(item);
//                if (burnTime > 0)
//                {
//                    IceBoxHandler.afuels.add(new LocalFuelPair(item.copy(), burnTime));
//                }
//            }
//        }
//    }
//
//    @Override
//    public Class<? extends GuiContainer> getGuiClass()
//    {
//        return GUIIcebox.class;
//    }
//
//    @Override
//    public String getGuiTexture()
//    {
//        return "agriculture:textures/gui/IceboxNEI.png";
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
//        return "Frozen Recipe";
//    }
//
//    protected boolean isFuel(final ItemStack ingredient)
//    {
//        for (final LocalFuelPair fuel : IceBoxHandler.afuels)
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
//        final ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getRecipesFor(ingredient);
//
//        addRecipes(allRecipes);
//    }
//
//    @Override
//    public void loadCraftingRecipes(final String outputId, final Object... results)
//    {
//        if (outputId.equals("frozen") && this.getClass() == IceBoxHandler.class)
//        {
//            final ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getRecipes();
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
//        // transferRects.add(new RecipeTransferRect(new Rectangle(71, 27, 25,
//        // 7), "frozen"));
//        // transferRects.add(new RecipeTransferRect(new Rectangle(6, YPOSITION,
//        // 16, 16), "coldfuel"));
//    }
//
//    @Override
//    public void loadUsageRecipes(final ItemStack ingredient)
//    {
//        final ArrayList<FreezerRecipe> allRecipes = FreezerRecipes.getInstance().getUsageFor(ingredient);
//
//        if (isFuel(ingredient))
//        {
//            this.loadUsageRecipes("coldfuel");
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
//        if (inputId.equals("coldfuel") && this.getClass() == IceBoxHandler.class)
//        {
//            this.loadCraftingRecipes("frozen");
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
//        if (IceBoxHandler.afuels == null)
//        {
//            findFuels();
//        }
//        return super.newInstance();
//    }
//}
