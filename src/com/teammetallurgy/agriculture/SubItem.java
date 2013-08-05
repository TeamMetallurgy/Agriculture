package com.teammetallurgy.agriculture;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class SubItem
{
	private SuperItem item;
	public int itemID;
	private int damage;
	
	private Icon itemIcon;
	private String unlocalizedName;
	private String textureName;
	
	public SubItem(int id, int damage)
	{
		if(Item.itemsList[id+256] == null)
		{
			item = new SuperItem(id);
		}
		else
		{
			if(Item.itemsList[id+256] instanceof SuperItem)
			{
				item = (SuperItem) Item.itemsList[id+256];
			}
			else
			{
	            System.out.println("CONFLICT @ " + id + " item slot already occupied by " + Item.itemsList[256 + id] + " while adding " + this);
			}
		}
		
		this.itemID = id;
		this.damage = damage;
		item.addSubItem(damage, this);
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public SubItem setUnlocalizedName(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
		this.setTextureName(unlocalizedName);
		return this;
	}
	
	public SubItem setTextureName(String textureName)
	{
		this.textureName = textureName;
		return this;
	}
	
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return unlocalizedName;
	}

	public Icon getIcon()
	{
		return itemIcon;
	}

	public void registerIcons(IconRegister iconRegister)
	{
		itemIcon = iconRegister.registerIcon("agriculture:" + textureName);
	}

	public SubItem setCreativeTab(AgricultureTab tab)
	{
		item.setCreativeTab(tab);
		return this;
	}
	
	public ItemStack getItemStack()
	{
		return getItemStack(1);
	}

	public ItemStack getItemStack(int size)
	{
		return new ItemStack(item.itemID, size, damage);
	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.none;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 0;
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		return false;
	}
}
