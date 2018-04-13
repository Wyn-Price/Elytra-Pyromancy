package com.wynprice.fireworks.common.data;

import java.util.ArrayList;
import java.util.List;

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
}
