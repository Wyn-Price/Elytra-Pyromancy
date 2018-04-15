package com.wynprice.fireworks.common.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.api.FireworkBit;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class FireworkItemStackHandler extends ItemStackHandler {

	public void forceSize(int size) {
		if(size == this.getSlots()) {
			return;
		} else {
			List<ItemStack> list = new ArrayList<>(this.stacks);
			while(list.size() != size) {
				if(size < list.size()) {
					list.remove(list.size() - 1);
				} else {
					list.add(ItemStack.EMPTY);
				}
			}
			this.stacks = NonNullList.from(ItemStack.EMPTY, list.toArray(new ItemStack[0]));
		}
	}
	
	public List<FireworkBit> getBits() {
		List<FireworkBit> bits = Lists.newArrayList();
		for(int i = 0; i < getSlots(); i++) {
			ItemStack itemstack = getStackInSlot(i);
			bits.addAll(FireworkDataHelper.getBits(itemstack));
		}
		return bits;
	}
	
	public List<Pair<ItemStack, List<FireworkBit>>> getMappedBits() {
		List<Pair<ItemStack, List<FireworkBit>>> list = Lists.newArrayList();
		for(int i = 0; i < getSlots(); i++) {
			ItemStack itemstack = getStackInSlot(i);
			list.add(Pair.of(itemstack, FireworkDataHelper.getBits(itemstack)));
		}
		return list;
	}
}
