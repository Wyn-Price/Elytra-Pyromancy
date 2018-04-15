package com.wynprice.fireworks.common.items;

import com.wynprice.fireworks.client.gui.GuiColorSelector;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemColorBit extends Item {
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote) {
			openGUI(handIn, playerIn.getHeldItem(handIn));
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@SideOnly(Side.CLIENT)
	private void openGUI(EnumHand hand,ItemStack stack) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiColorSelector(hand, stack));
	}	
}
