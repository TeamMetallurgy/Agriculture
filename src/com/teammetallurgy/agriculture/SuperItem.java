package com.teammetallurgy.agriculture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SuperItem extends Item
{
	private Map<Integer, SubItem> subItems;

	public SuperItem(int par1)
	{
		super(par1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	public void addSubItem(int id, SubItem subItem)
	{
		if(subItems == null)
			subItems = new HashMap<Integer, SubItem>();
		
		subItems.put(id, subItem);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();
		
		SubItem item = subItems.get(i);
		
		if(item != null)
			return super.getUnlocalizedName() + ".agriculture" + item.getUnlocalizedName(itemstack);
		
		return super.getUnlocalizedName() + "." + "Unknown" + itemstack.getItemDamage();
	}
	
	@Override
	public void getSubItems(int meta, CreativeTabs creativeTabs, List list)
	{
		for(Integer itemDamage : subItems.keySet())
		{
			list.add(new ItemStack(itemID, 1, itemDamage));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIconFromDamage(int damage)
	{
		if(subItems.containsKey(damage))
			return subItems.get(damage).getIcon();
		
		return itemIcon;
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		return getIconFromDamage(stack.getItemDamage());
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass)
	{
		return getIconFromDamage(stack.getItemDamage());
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		for(SubItem item : subItems.values())
		{
			item.registerIcons(iconRegister);
		}
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
    	int damage = par1ItemStack.getItemDamage();
    	if(subItems.containsKey(damage))
    	{
    		return subItems.get(damage).getMaxItemUseDuration(par1ItemStack);
    	}
    	
    	return 0;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
    	int damage = par1ItemStack.getItemDamage();
    	if(subItems.containsKey(damage))
    	{
    		return subItems.get(damage).getItemUseAction(par1ItemStack);
    	}
    	
    	return EnumAction.none;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
    	int damage = par1ItemStack.getItemDamage();
    	if(subItems.containsKey(damage))
    	{
    		return subItems.get(damage).onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
    	}
    	
    	return par1ItemStack;
	}

    public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer player)
    {
    	int damage = itemstack.getItemDamage();
    	if(subItems.containsKey(damage))
    	{
    		return subItems.get(damage).onEaten(itemstack, world, player);
    	}
    	
    	return itemstack;
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	int damage = par1ItemStack.getItemDamage();
    	if(subItems.containsKey(damage))
    	{
    		System.out.println(subItems.get(damage).getClass().getSimpleName());
    		return subItems.get(damage).onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    	}
    	
    	return false;
    }
}
