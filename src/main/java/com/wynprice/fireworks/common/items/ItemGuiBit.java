package com.wynprice.fireworks.common.items;

import com.wynprice.fireworks.FireworksMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGuiBit extends Item {	
	
	private final int guiId;
	
	public ItemGuiBit(int guiId) {
		this.guiId = guiId;	
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.openGui(FireworksMod.getInstance(), guiId, worldIn, handIn.ordinal(), 0, 0);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}
