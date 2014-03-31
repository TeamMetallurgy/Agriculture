package com.teammetallurgy.agriculture.factories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.agriculture.gui.ContainerBrewer;
import com.teammetallurgy.agriculture.gui.ContainerCabinet;
import com.teammetallurgy.agriculture.gui.ContainerCounter;
import com.teammetallurgy.agriculture.gui.ContainerFrier;
import com.teammetallurgy.agriculture.gui.ContainerFuel;
import com.teammetallurgy.agriculture.gui.ContainerIcebox;
import com.teammetallurgy.agriculture.gui.ContainerOven;
import com.teammetallurgy.agriculture.gui.ContainerProcessor;
import com.teammetallurgy.agriculture.gui.GUIBrewer;
import com.teammetallurgy.agriculture.gui.GUICabinet;
import com.teammetallurgy.agriculture.gui.GUICounter;
import com.teammetallurgy.agriculture.gui.GUIFrier;
import com.teammetallurgy.agriculture.gui.GUIFuelSlot;
import com.teammetallurgy.agriculture.gui.GUIIcebox;
import com.teammetallurgy.agriculture.gui.GUIOven;
import com.teammetallurgy.agriculture.gui.GUIProcessor;
import com.teammetallurgy.agriculture.libs.GUIIds;
import com.teammetallurgy.agriculture.machines.BaseMachineTileEntity;
import com.teammetallurgy.agriculture.machines.IFuelSlot;
import com.teammetallurgy.agriculture.machines.brewer.TileEntityBrewer;
import com.teammetallurgy.agriculture.machines.counter.TileEntityCounter;
import com.teammetallurgy.agriculture.machines.frier.TileEntityFrier;
import com.teammetallurgy.agriculture.machines.icebox.TileEntityIcebox;
import com.teammetallurgy.agriculture.machines.oven.TileEntityOven;
import com.teammetallurgy.agriculture.machines.processor.TileEntityProcessor;

import cpw.mods.fml.relauncher.Side;

public class Factories {
    public static Object GUIFactory(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side)
    {
        final TileEntity te = world.getTileEntity(x, y, z);

        switch (id)
        {
            case GUIIds.OVEN:
                if (te != null && te instanceof TileEntityOven)
                {
                    if (side.isClient())
                    {
                        return new GUIOven(new ContainerOven(player.inventory, (TileEntityOven) te));
                    }
                    else
                    {
                        return new ContainerOven(player.inventory, (TileEntityOven) te);
                    }
                }
                break;
            case GUIIds.CABINET:
                if (te != null && te instanceof TileEntityCounter)
                {
                    if (side.isClient())
                    {
                        return new GUICabinet(new ContainerCabinet(player.inventory, (TileEntityCounter) te));
                    }
                    else
                    {
                        return new ContainerCabinet(player.inventory, (TileEntityCounter) te);
                    }
                }
                break;
            case GUIIds.PROCESSOR:
                if (te != null && te instanceof TileEntityProcessor)
                {
                    if (side.isClient())
                    {
                        return new GUIProcessor(new ContainerProcessor(player.inventory, (TileEntityProcessor) te));
                    }
                    else
                    {
                        return new ContainerProcessor(player.inventory, (TileEntityProcessor) te);
                    }
                }
                break;
            case GUIIds.COUNTER:

                if (te != null && te instanceof BaseMachineTileEntity)
                {
                    if (side.isClient())
                    {
                        return new GUICounter(new ContainerCounter(player.inventory, ((BaseMachineTileEntity) te).getInventoryCounter()));
                    }
                    else
                    {
                        return new ContainerCounter(player.inventory, ((BaseMachineTileEntity) te).getInventoryCounter());
                    }
                }
                break;
            case GUIIds.FUEL:
                if (te != null && te instanceof IFuelSlot)
                {
                    if (side.isClient())
                    {
                        return new GUIFuelSlot(new ContainerFuel(player.inventory, ((IFuelSlot) te).getFuelInventory(), ((IFuelSlot) te).getFuelSlot(), (IFuelSlot) te));
                    }
                    else
                    {
                        return new ContainerFuel(player.inventory, ((IFuelSlot) te).getFuelInventory(), ((IFuelSlot) te).getFuelSlot(), (IFuelSlot) te);
                    }
                }
                break;
            case GUIIds.BREWER:
                if (te != null && te instanceof TileEntityBrewer)
                {
                    if (side.isClient())
                    {
                        return new GUIBrewer(new ContainerBrewer(player.inventory, (TileEntityBrewer) te));
                    }
                    else
                    {
                        return new ContainerBrewer(player.inventory, (TileEntityBrewer) te);
                    }
                }
                break;
            case GUIIds.FRIER:
                if (te != null && te instanceof TileEntityFrier)
                {
                    if (side.isClient())
                    {
                        return new GUIFrier(new ContainerFrier(player.inventory, (TileEntityFrier) te));
                    }
                    else
                    {
                        return new ContainerFrier(player.inventory, (TileEntityFrier) te);
                    }
                }
            case GUIIds.ICEBOX:
                if (te != null && te instanceof TileEntityIcebox)
                {
                    if (side.isClient())
                    {
                        return new GUIIcebox(new ContainerIcebox(player.inventory, (TileEntityIcebox) te));
                    }
                    else
                    {
                        return new ContainerIcebox(player.inventory, (TileEntityIcebox) te);
                    }
                }
                break;
            default:
                return null;
        }

        return null;
    }
}