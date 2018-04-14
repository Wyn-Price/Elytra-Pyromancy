package com.wynprice.fireworks.common.data;

import java.util.List;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.api.ElytraRegistery;
import com.wynprice.fireworks.common.api.FireworkBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

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
	
	public static double getDistanceNeededForLevelUp(int currentLevel) {
		currentLevel--;
		return Math.round((25D + (currentLevel / 1.73D) * (currentLevel / 1.73D)) * 10000D) / 100D;
	}
	
	public static boolean isPlayerHoldingBit(EntityPlayer player, FireworkBit bit) {
		return getAmountPlayerHoldingBit(player, bit) != 0;
	}
	
	public static int getAmountPlayerHoldingBit(EntityPlayer player, FireworkBit bit) {
		int amount = 0;
		for(EnumHand hand : EnumHand.values()) {
			FireworkData data = readDataFromStack(player.getHeldItem(hand));
			FireworkItemStackHandler handler = data.getHandler();
			for(int i = 0; i < handler.getSlots(); i++) {
				if(bit.getPredicate().test(handler.getStackInSlot(i))) {
					amount++;
				}
			}
			if(amount != 0) {
				break;
			}
		}
		return amount;
	}
	
	public static List<FireworkBit> getBits(ItemStack stack) {
		List<FireworkBit> bits = Lists.newArrayList();
		for(FireworkBit bit : ElytraRegistery.getRegistry().getValuesCollection()) {
			if(bit.getPredicate().test(stack)) {
				bits.add(bit);
			}
		}
		return bits;
	}
}
