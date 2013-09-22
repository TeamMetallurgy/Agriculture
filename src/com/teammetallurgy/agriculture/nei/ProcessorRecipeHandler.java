//package com.teammetallurgy.agriculture.nei;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.teammetallurgy.agriculture.gui.GUIProcessor;
//import com.teammetallurgy.agriculture.nei.ProcessorHandler.NEIProcessRecipe;
//import com.teammetallurgy.agriculture.recipes.ProcessRecipe;
//import com.teammetallurgy.agriculture.recipes.ProcessorRecipes;
//
//import net.minecraft.client.gui.inventory.GuiContainer;
//import net.minecraft.inventory.Container;
//import net.minecraft.item.ItemStack;
//import codechicken.nei.PositionedStack;
//import codechicken.nei.api.IOverlayHandler;
//import codechicken.nei.api.IRecipeOverlayRenderer;
//import codechicken.nei.recipe.GuiRecipe;
//import codechicken.nei.recipe.ICraftingHandler;
//import codechicken.nei.recipe.TemplateRecipeHandler;
//import codechicken.nei.recipe.TemplateRecipeHandler.CachedRecipe;
//
//public class ProcessorRecipeHandler extends TemplateRecipeHandler 
//{
//    class NEIProcessRecipe extends CachedRecipe
//    {
//
//        public ArrayList<PositionedStack> ingredients;
//        public PositionedStack result;
//
//        public NEIProcessRecipe(int width, int height, Object[] items, ItemStack out, ItemStack meh)
//        {
//            result = new PositionedStack(out, 98, 21);
//            ingredients = new ArrayList<PositionedStack>();
//            setIngredients(width, height, items, out);
//        }
//
//        private void setIngredients(int width, int height, Object[] items, ItemStack out)
//        {
//            PositionedStack stack = new PositionedStack(items[0], 16, 21);
//            stack.setMaxSize(1);
//            ingredients.add(stack);
//
//            if(items.length > 1 && items[1] != null) 
//            {
//                stack = new PositionedStack(items[1], 52, 21);
//                stack.setMaxSize(1);
//                ingredients.add(stack);
//            }
//            
//        }
//
//        @Override
//        public List<PositionedStack> getIngredients()
//        {
//            return getCycledIngredients(cycleticks / 20, ingredients);
//        }
//
//        @Override
//        public PositionedStack getResult()
//        {
//            return result;
//        }
//    }
//
//    @Override
//    public void loadCraftingRecipes(ItemStack ingredient)
//    {
//        ArrayList<ProcessRecipe> allRecipes = ProcessorRecipes.getInstance().getRecipesFor(ingredient);
//
//        for (ProcessRecipe recipe : allRecipes)
//        {
//            NEIProcessRecipe recipeT = new NEIProcessRecipe(3, 3, recipe.getIngredients(), recipe.getCraftingResult(), ingredient);
//            arecipes.add(recipeT);
//        }
//    }
//
//    public String getRecipeName()
//    {
//        return "Processed Recipe";
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
//}
