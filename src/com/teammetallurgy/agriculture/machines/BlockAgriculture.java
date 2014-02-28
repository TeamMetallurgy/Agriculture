package com.teammetallurgy.agriculture.machines;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.Agriculture;
import com.teammetallurgy.metallurgycore.machines.BlockMetallurgyCore;

import cpw.mods.fml.client.registry.RenderingRegistry;

public abstract class BlockAgriculture extends BlockMetallurgyCore
{

    protected int renderId;

    public BlockAgriculture(int id)
    {
        super(id);
        renderId = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return Agriculture.tab;
    }

    @Override
    public int getRenderType()
    {
        return renderId;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
