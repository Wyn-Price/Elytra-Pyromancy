package com.wynprice.fireworks.common.data;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.api.FireworksModRegistery;
import com.wynprice.fireworks.common.api.FireworkBit;
import com.wynprice.fireworks.common.util.MathReader;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class FireworkDataHelper {
	public static final String EXPRESION = "((25 + (x / 1.73)^2) * 100000) / 100";
	
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
		return MathReader.eval(EXPRESION.replace("x", String.valueOf(currentLevel)));
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
		for(FireworkBit bit : FireworksModRegistery.getRegistry().getValuesCollection()) {
			if(bit.getPredicate().test(stack)) {
				bits.add(bit);
			}
		}
		return bits;
	}
}
