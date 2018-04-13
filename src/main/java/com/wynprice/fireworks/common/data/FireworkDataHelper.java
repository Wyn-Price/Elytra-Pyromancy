package com.wynprice.fireworks.common.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.api.ElytraRegistery;
import com.wynprice.fireworks.common.api.FireworkBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class FireworkDataHelper {
	public static FireworkData readDataFromStack(ItemStack stack) {
		FireworkData data = new FireworkData();
		data.deserializeNBT(stack.getOrCreateSubCompound("fireworkdata"));
		return data;
	}
	
	public static void writeDataToStack(FireworkData data, ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();
		if(tag == null) {
			tag = new NBTTagCompound();
		}
		tag.setTag("fireworkdata", data.serializeNBT());
		stack.setTagCompound(tag);
	}
	
	public static double getXpNeededForLevel(double level) {
		return (25D + (level / 1.73D) * (level / 1.73D)) * 100D;
	}
	
	public static boolean isPlayerHoldingBit(EntityPlayer player, FireworkBit bit) {
		for(EnumHand hand : EnumHand.values()) {
			FireworkData data = readDataFromStack(player.getHeldItem(hand));
			FireworkItemStackHandler handler = data.getHandler();
			for(int i = 0; i < handler.getSlots(); i++) {
				if(bit.getPredicate().test(handler.getStackInSlot(i))) {
					return true;
				}
			}
		}
		return false;
	}
}
