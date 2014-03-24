package com.teammetallurgy.agriculture;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AgricultureTab extends CreativeTabs {
    private int itemID;

    public AgricultureTab(final String label)
    {
        super(label);
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
        return itemID;
    }

    public void setItemID(final int itemID)
    {
        this.itemID = itemID;
    }
}
