package com.teammetallurgy.agriculture;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AgricultureItemGroup extends ItemGroup {

    public AgricultureItemGroup(String label) {
        super(label);
        this.setBackgroundImageName("item_search.png");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.CARROT);
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}