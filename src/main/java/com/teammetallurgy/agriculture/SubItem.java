package com.teammetallurgy.agriculture;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class SubItem {
    private final int damage;
    private SuperItem item;
    private Icon itemIcon;

    public int itemID;
    private String textureName;
    private String unlocalizedName;

    public SubItem(final int id, final int damage)
    {
        if (Item.itemsList[id + 256] == null)
        {
            item = new SuperItem(id);
        }
        else
        {
            if (Item.itemsList[id + 256] instanceof SuperItem)
            {
                item = (SuperItem) Item.itemsList[id + 256];
            }
            else
            {
            }
        }

        itemID = id + 256;
        this.damage = damage;
        item.addSubItem(damage, this);
    }

    public int getDamage()
    {
        return damage;
    }

    public Icon getIcon()
    {
        return itemIcon;
    }

    public ItemStack getItemStack()
    {
        return this.getItemStack(1);
    }

    public ItemStack getItemStack(final int size)
    {
        return new ItemStack(item.itemID, size, damage);
    }

    public EnumAction getItemUseAction(final ItemStack par1ItemStack)
    {
        return EnumAction.none;
    }

    public int getMaxItemUseDuration(final ItemStack par1ItemStack)
    {
        return 0;
    }

    public String getUnlocalizedName(final ItemStack itemstack)
    {
        return unlocalizedName;
    }

    public ItemStack onEaten(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public ItemStack onItemRightClick(final ItemStack par1ItemStack, final World par2World, final EntityPlayer par3EntityPlayer)
    {
        return par1ItemStack;
    }

    public boolean onItemUse(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final World par3World, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10)
    {
        return false;
    }

    public void registerIcons(final IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("agriculture:" + textureName);
    }

    public SubItem setCreativeTab(final AgricultureTab tab)
    {
        item.setCreativeTab(tab);
        return this;
    }

    public SubItem setTextureName(final String textureName)
    {
        this.textureName = textureName;
        return this;
    }

    public SubItem setUnlocalizedName(final String unlocalizedName)
    {
        this.unlocalizedName = unlocalizedName;
        setTextureName(unlocalizedName);
        return this;
    }
}
