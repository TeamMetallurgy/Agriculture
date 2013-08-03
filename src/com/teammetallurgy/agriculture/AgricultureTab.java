package com.teammetallurgy.agriculture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class AgricultureTab extends CreativeTabs
{
	private int itemID;

	public AgricultureTab(String label)
	{
		super(label);
	}
	
	public void setItemID(int itemID)
	{
		this.itemID = itemID;
	}

    @SideOnly(Side.CLIENT)
    /**
     * the itemID for the item to be displayed on the tab
     */
    public int getTabIconItemIndex()
    {
    	return itemID;
    }
}
