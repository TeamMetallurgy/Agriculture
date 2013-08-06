package com.teammetallurgy.agriculture.food;

import net.minecraft.item.ItemStack;

public interface ICookable
{
	public void heatUpdate(ItemStack stack, int temp, int time);
}
