package com.teammetallurgy.agriculture.gui;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBlock extends Entity {

    private int brightness = -1;
    public double iSize, jSize, kSize;
    public float rotationX = 0;
    public float rotationY = 0;
    public float rotationZ = 0;
    public float shadowSize = 0;
    @SideOnly(Side.CLIENT)
    public Icon texture;

    public EntityBlock(final World world)
    {
        super(world);
        preventEntitySpawning = false;
        noClip = true;
        isImmuneToFire = true;
    }

    public EntityBlock(final World world, final double xPos, final double yPos, final double zPos)
    {
        super(world);
        setPositionAndRotation(xPos, yPos, zPos, 0, 0);
    }

    public EntityBlock(final World world, final double i, final double j, final double k, final double iSize, final double jSize, final double kSize)
    {
        this(world);
        this.iSize = iSize;
        this.jSize = jSize;
        this.kSize = kSize;
        setPositionAndRotation(i, j, k, 0, 0);
        motionX = 0.0;
        motionY = 0.0;
        motionZ = 0.0;
    }

    @Override
    protected void entityInit()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public int getBrightnessForRender(final float par1)
    {
        return brightness > 0 ? brightness : super.getBrightnessForRender(par1);
    }

    @Override
    public void moveEntity(final double d, final double d1, final double d2)
    {
        setPosition(posX + d, posY + d1, posZ + d2);
    }

    @Override
    protected void readEntityFromNBT(final NBTTagCompound data)
    {
        iSize = data.getDouble("iSize");
        jSize = data.getDouble("jSize");
        kSize = data.getDouble("kSize");
    }

    public void setBrightness(final int brightness)
    {
        this.brightness = brightness;
    }

    @Override
    public void setPosition(final double d, final double d1, final double d2)
    {
        super.setPosition(d, d1, d2);
        boundingBox.minX = posX;
        boundingBox.minY = posY;
        boundingBox.minZ = posZ;

        boundingBox.maxX = posX + iSize;
        boundingBox.maxY = posY + jSize;
        boundingBox.maxZ = posZ + kSize;
    }

    @Override
    protected void writeEntityToNBT(final NBTTagCompound data)
    {
        data.setDouble("iSize", iSize);
        data.setDouble("jSize", jSize);
        data.setDouble("kSize", kSize);
    }
}
