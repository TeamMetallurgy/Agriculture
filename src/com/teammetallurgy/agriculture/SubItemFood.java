package com.teammetallurgy.agriculture;

import com.teammetallurgy.agriculture.hunger.HungerSystem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SubItemFood extends SubItem
{
	/** The amount this food item heals the player. */
	private int wellFedAmount;
	private final int healAmount;
	private final float saturationModifier;
	private final boolean isWolfsFavoriteMeat;
	private boolean alwaysEdible;

	public SubItemFood(int id, int damage, int heal)
	{
		super(id, damage);
		this.wellFedAmount = heal;
		this.healAmount = heal/4;
		this.saturationModifier = 0.5f;
		this.isWolfsFavoriteMeat = false;
	}

	public SubItemFood(int foodid, int i, int j, float f)
	{
		this(foodid, i, j);
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		--par1ItemStack.stackSize;
		par3EntityPlayer.getFoodStats().addStats(this.getHealAmount(), this.getSaturationModifier());
		par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
		HungerSystem.addPoints(par3EntityPlayer, healAmount);
		return par1ItemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 32;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (par3EntityPlayer.canEat(this.alwaysEdible))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		}
		else
		{
			par3EntityPlayer.getFoodStats().addStats(-this.getHealAmount(), -this.getSaturationModifier());
		}

		return par1ItemStack;
	}

	public int getHealAmount()
	{
		return this.healAmount;
	}

	/**
	 * gets the saturationModifier of the ItemFood
	 */
	public float getSaturationModifier()
	{
		return this.saturationModifier;
	}

	/**
	 * Whether wolves like this food (true for raw and cooked porkchop).
	 */
	public boolean isWolfsFavoriteMeat()
	{
		return this.isWolfsFavoriteMeat;
	}

	/**
	 * Set the field 'alwaysEdible' to true, and make the food edible even if
	 * the player don't need to eat.
	 */
	public SubItemFood setAlwaysEdible()
	{
		this.alwaysEdible = true;
		return this;
	}
}
