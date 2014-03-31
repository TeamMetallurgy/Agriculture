//package com.teammetallurgy.agriculture.nei;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.item.ItemStack;
//import codechicken.nei.PositionedStack;
//import codechicken.nei.recipe.TemplateRecipeHandler;
//
//import com.teammetallurgy.agriculture.gui.GUIProcessor;
//import com.teammetallurgy.agriculture.recipes.ProcessRecipe;
//import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;
//
//public class BrewerHandler extends TemplateRecipeHandler {
//    class NEIProcessRecipe extends CachedRecipe {
//
//        public ArrayList<PositionedStack> ingredients;
//        public PositionedStack result;
//
//        public NEIProcessRecipe(final int width, final int height, final Object[] items, final ItemStack out, final ItemStack meh)
//        {
//            result = new PositionedStack(out, 98, 21);
//            ingredients = new ArrayList<PositionedStack>();
//            setIngredients(width, height, items, out);
//        }
//
//        @Override
//        public List<PositionedStack> getIngredients()
//        {
//            return getCycledIngredients(BrewerHandler.this.cycleticks / 20, ingredients);
//        }
//
//        @Override
//        public PositionedStack getResult()
//        {
//            return result;
//        }
//
//        private void setIngredients(final int width, final int height, final Object[] items, final ItemStack out)
//        {
//            PositionedStack stack = new PositionedStack(items[0], 16, 21);
//            stack.setMaxSize(1);
//            ingredients.add(stack);
//
//            if (items.length > 1 && items[1] != null)
//            {
//                stack = new PositionedStack(items[1], 52, 21);
//                stack.setMaxSize(1);
//                ingredients.add(stack);
//            }
//
//        }
//    }
//
//    @Override
//    public Class<? extends GuiContainer> getGuiClass()
//    {
//        return GUIProcessor.class;
//    }
//
//    @Override
//    public String getGuiTexture()
//    {
//        return "agriculture:textures/gui/Processor.png";
//    }
//
//    @Override
//    public String getRecipeName()
//    {
//        return "Processed Recipe";
//    }
//
//    @Override
//    public void loadCraftingRecipes(final ItemStack ingredient)
//    {
//        final ArrayList<ProcessRecipe> allRecipes = ProcessorRecipes.getInstance().getRecipesFor(ingredient);
//
//        for (final ProcessRecipe recipe : allRecipes)
//        {
//            final NEIProcessRecipe recipeT = new NEIProcessRecipe(3, 3, recipe.getIngredients(), recipe.getCraftingResult(), ingredient);
//            arecipes.add(recipeT);
//        }
//    }
//
//    @Override
//    public void loadUsageRecipes(final ItemStack ingredient)
//    {
//        final ArrayList<ProcessRecipe> allRecipes = ProcessorRecipes.getInstance().getAllRecipes(ingredient);
//
//        for (final ProcessRecipe recipe : allRecipes)
//        {
//            final NEIProcessRecipe recipeT = new NEIProcessRecipe(3, 3, recipe.getIngredients(), recipe.getCraftingResult(), ingredient);
//            arecipes.add(recipeT);
//        }
//    }
//}
