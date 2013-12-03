package com.teammetallurgy.agriculture.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.agriculture.AgricultureItems;
import com.teammetallurgy.agriculture.SubItem;

public class OvenRecipes {

    private static final int         BURNTIME    = 50000;

    private static final int         DEFAULTTEMP = 100000;

    private static final OvenRecipes instance    = new OvenRecipes();

    public static OvenRecipes getInstance()
    {
        return OvenRecipes.instance;
    }

    private final ArrayList<OvenRecipe> recipes = new ArrayList<OvenRecipe>();

    private OvenRecipes()
    {
        this.addRecipe(AgricultureItems.caramel.getItemStack(), new ItemStack(Item.sugar));
        this.addRecipe(AgricultureItems.shortcake, AgricultureItems.batter);
        this.addRecipe(AgricultureItems.carrotCake, AgricultureItems.carrotCakeBatter);
        this.addRecipe(AgricultureItems.cheeseSandwich, AgricultureItems.grilledCheese);
        this.addRecipe(AgricultureItems.loafOfBread, AgricultureItems.dough);
        this.addRecipe(AgricultureItems.pasta, AgricultureItems.pastaDough);
        this.addRecipe(AgricultureItems.toastedPbjSandwichApple, AgricultureItems.pbjSandwichApple);
        this.addRecipe(AgricultureItems.toastedPbjSandwichStrawberry, AgricultureItems.pbjSandwichStrawberry);
        this.addRecipe(AgricultureItems.roastedPeanuts, AgricultureItems.peanuts);
        this.addRecipe(AgricultureItems.applePie, AgricultureItems.rawApplePie);
        this.addRecipe(AgricultureItems.frenchToast, AgricultureItems.rawFrenchToast);
        this.addRecipe(AgricultureItems.hamburgerPatty, AgricultureItems.rawHamburgerPatty);
        this.addRecipe(AgricultureItems.strawberryPie, AgricultureItems.rawStrawberryPie);
        this.addRecipe(AgricultureItems.beefJerkey, AgricultureItems.saltedBeef);
        this.addRecipe(AgricultureItems.bacon, AgricultureItems.saltedPork);
        this.addRecipe(AgricultureItems.toast, AgricultureItems.sliceOfBread);

        this.addRecipe(AgricultureItems.appleCinnamonCookie, AgricultureItems.appleCinnamonCookieDough);
        this.addRecipe(AgricultureItems.butterCookie, AgricultureItems.butterCookieDough);
        this.addRecipe(AgricultureItems.chocolateChipCookie, AgricultureItems.chocolateChipCookieDough);
        this.addRecipe(AgricultureItems.peanutButterCookie, AgricultureItems.peanutButterCookieDough);
        this.addRecipe(AgricultureItems.pumpkinCookie, AgricultureItems.pumpkinCookieDough);
        this.addRecipe(AgricultureItems.snickerDoodle, AgricultureItems.snickerDoodleDough);
        this.addRecipe(AgricultureItems.strawberryJellyCookie, AgricultureItems.strawberryJellyCookieDough);
        this.addRecipe(AgricultureItems.sugarCookie, AgricultureItems.sugarCookieDough);
        this.addRecipe(AgricultureItems.chocolateSauce, AgricultureItems.chocolate);
        this.addRecipe(AgricultureItems.roastedMarshmellows, AgricultureItems.marshmellows);
        this.addRecipe(AgricultureItems.toastedPBSandwich, AgricultureItems.pbSandwich);

        this.addBurntRecipe(AgricultureItems.appleCinnamonCookieBurned, AgricultureItems.appleCinnamonCookie);
        this.addBurntRecipe(AgricultureItems.applePieBurned, AgricultureItems.applePie);
        this.addBurntRecipe(AgricultureItems.baconBurned, AgricultureItems.bacon);
        this.addBurntRecipe(AgricultureItems.beefJerkeyBurned, AgricultureItems.beefJerkey);
        this.addBurntRecipe(AgricultureItems.butterCookieBurned, AgricultureItems.butterCookie);
        this.addBurntRecipe(AgricultureItems.caramelBurned, AgricultureItems.caramel);
        this.addBurntRecipe(AgricultureItems.carrotCakeBurned, AgricultureItems.carrotCake);
        this.addBurntRecipe(AgricultureItems.chocolateChipCookieBurned, AgricultureItems.chocolateChipCookie);
        this.addBurntRecipe(AgricultureItems.chocolateSauceBurned, AgricultureItems.chocolateSauce);
        this.addBurntRecipe(AgricultureItems.frenchToastBurned, AgricultureItems.frenchToast);
        this.addBurntRecipe(AgricultureItems.grilledCheeseBurned, AgricultureItems.grilledCheese);
        this.addBurntRecipe(AgricultureItems.hamburgerPattyBurned, AgricultureItems.hamburgerPattyBurned);
        this.addBurntRecipe(AgricultureItems.loafOfBreadBurned, AgricultureItems.loafOfBread);
        this.addBurntRecipe(AgricultureItems.pastaBurned, AgricultureItems.pasta);
        this.addBurntRecipe(AgricultureItems.peanutButterCookieBurned, AgricultureItems.peanutButter);
        this.addBurntRecipe(AgricultureItems.pumpkinCookieBurned, AgricultureItems.pumpkinCookie);
        this.addBurntRecipe(AgricultureItems.roastedMarshmellowsBurned, AgricultureItems.roastedMarshmellows);
        this.addBurntRecipe(AgricultureItems.roastedPeanutsBurned, AgricultureItems.roastedMarshmellows);
        this.addBurntRecipe(AgricultureItems.shortcakeBurned, AgricultureItems.shortcake);
        this.addBurntRecipe(AgricultureItems.snickerDoodleBurned, AgricultureItems.snickerDoodle);
        this.addBurntRecipe(AgricultureItems.strawberryJellyCookieBurned, AgricultureItems.strawberryJellyCookie);
        this.addBurntRecipe(AgricultureItems.strawberryPieBurned, AgricultureItems.strawberryPie);
        this.addBurntRecipe(AgricultureItems.sugarCookieBurned, AgricultureItems.sugarCookie);
        this.addBurntRecipe(AgricultureItems.toastBurned, AgricultureItems.toast);
        this.addBurntRecipe(AgricultureItems.toastedPBJSandwichAppleBurned, AgricultureItems.toastedPbjSandwichApple);
        this.addBurntRecipe(AgricultureItems.toastedPBJSandwichStrawberryBurned, AgricultureItems.toastedPbjSandwichStrawberry);
        this.addBurntRecipe(AgricultureItems.toastedPBSandwichBurned, AgricultureItems.toastedPBSandwich);
    }

    public void addBurntRecipe(final ItemStack in, final ItemStack out)
    {
        this.addRecipe(in, out, OvenRecipes.BURNTIME);
    }

    public void addBurntRecipe(final SubItem in, final SubItem out)
    {
        this.addBurntRecipe(in.getItemStack(), out.getItemStack());
    }

    public void addRecipe(final ItemStack in, final ItemStack out)
    {
        this.addRecipe(in, out, OvenRecipes.DEFAULTTEMP);
    }

    public void addRecipe(final ItemStack in, final ItemStack out, final int heat)
    {
        this.recipes.add(new OvenRecipe(out, in, heat));
    }

    public void addRecipe(final SubItem in, final SubItem out)
    {
        this.addRecipe(in.getItemStack(), out.getItemStack());
    }

    public ItemStack findMatchingRecipe(final ItemStack in, final int heat)
    {
        OvenRecipe recipe = this.getMatchingRecipe(in, heat);
        
        if (recipe != null) 
        {
            return recipe.getResult();
        }
        
        return null;
    }

    public OvenRecipe getMatchingRecipe(final ItemStack in, final int heat)
    {
        for (final OvenRecipe recipe : this.recipes)
        {
            if (recipe.matches(in, heat)) { return recipe; }
        }

        return null;
    }

    public ArrayList<OvenRecipe> getRecipesFor(final ItemStack ingredient)
    {
        final ArrayList<OvenRecipe> retRecipes = new ArrayList<OvenRecipe>();

        for (final OvenRecipe recipe : this.recipes)
        {
            if (recipe.getResult().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }

    public ArrayList<OvenRecipe> getUsageFor(final ItemStack ingredient)
    {
        final ArrayList<OvenRecipe> retRecipes = new ArrayList<OvenRecipe>();

        for (final OvenRecipe recipe : this.recipes)
        {
            if (recipe.getInput().isItemEqual(ingredient))
            {
                retRecipes.add(recipe);
            }
        }

        return retRecipes;
    }
    
    public ArrayList<OvenRecipe> getRecipes()
    {
        return recipes;
    }
}
