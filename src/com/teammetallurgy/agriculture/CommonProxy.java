package com.teammetallurgy.agriculture;

import sun.org.mozilla.javascript.internal.ast.NewExpression;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.processor.ContainerProcessor;
import com.teammetallurgy.agriculture.machines.processor.GUIProcessor;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        switch (ID)
        {
            case GUIIds.PROCESSOR:
                return new GUIProcessor(new ContainerProcessor(player.inventory, (TileEntityProcessor) tileEntity));
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        switch (ID)
        {
            case GUIIds.PROCESSOR:
                return new ContainerProcessor(player.inventory, (TileEntityProcessor) tileEntity);
            default:
                return null;
        }
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
