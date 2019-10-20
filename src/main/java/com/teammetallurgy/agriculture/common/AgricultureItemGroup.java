package com.teammetallurgy.agriculture.common;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class AgricultureItemGroup extends ItemGroup {
    private static AgricultureItemGroup INSTANCE;

    public static AgricultureItemGroup GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AgricultureItemGroup("agriculture");
        }
        return INSTANCE;
    }

    private AgricultureItemGroup(String label) {
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