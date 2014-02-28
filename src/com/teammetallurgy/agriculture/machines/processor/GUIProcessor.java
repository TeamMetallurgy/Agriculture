package com.teammetallurgy.agriculture.machines.processor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycore.machines.GUIMetallurgyMachine;

public class GUIProcessor extends GUIMetallurgyMachine
{

    public GUIProcessor(final ContainerProcessor processor)
    {
        super(processor, "agriculture:textures/gui/Processor.png");

    }

    @Override
    protected void drawCookScale(int i1)
    {
        this.drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 37, 176, 11, i1, 20);
    }

    @Override
    protected void drawFlame(int i1)
    {
        this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 27 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
    }

    @Override
    public void initGui()
    {
        xSize = 177;
        ySize = 167;

        super.initGui();
    }
}
