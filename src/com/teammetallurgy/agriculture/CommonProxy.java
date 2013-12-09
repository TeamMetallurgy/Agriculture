package com.teammetallurgy.agriculture;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.factories.Factories;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        return Factories.GUIFactory(ID, player, world, x, y, z, FMLCommonHandler.instance().getEffectiveSide());
    }

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        return Factories.GUIFactory(ID, player, world, x, y, z, FMLCommonHandler.instance().getEffectiveSide());
    }

    public void registerEventHandlers()
    {
    }

    public void registerRenderers()
    {
    }

    public void updateHunger(final float hunger)
    {
        // TODO Auto-generated method stub

    }

}
