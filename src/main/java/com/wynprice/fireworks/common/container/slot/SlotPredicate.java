package com.wynprice.fireworks.common.container.slot;

import java.util.function.Predicate;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPredicate extends SlotItemHandler {

	private final Predicate<ItemStack> predicate;

	private ItemStack stack;
	
	private int stackSize = 64;
	
	public SlotPredicate(ItemStack stack, int xPosition, int yPosition, Predicate<ItemStack> predicate) {
		super(new ItemStackHandler(NonNullList.withSize(1, stack)), 0, xPosition, yPosition);
		this.predicate = predicate;
		this.stack = stack;
	}
	
	public SlotPredicate setStackSize(int stackSize) {
		this.stackSize = stackSize;
		return this;
	}
	
	public int getStackSize() {
		return stackSize;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return predicate.test(stack);
	}
	
	@Override
	public int getSlotStackLimit() {
		return stackSize;
	}

}
