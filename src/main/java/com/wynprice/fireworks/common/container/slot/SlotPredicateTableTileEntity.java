package com.wynprice.fireworks.common.container.slot;

import java.util.function.Predicate;

import com.wynprice.fireworks.common.container.ContainerFireworkTable;

import net.minecraft.item.ItemStack;

public class SlotPredicateTableTileEntity extends SlotPredicate {

	private final ContainerFireworkTable container;
	
	public SlotPredicateTableTileEntity(ContainerFireworkTable container, ItemStack stack, int xPosition, int yPosition,
			Predicate<ItemStack> predicate) {
		super(stack, xPosition, yPosition, predicate);
		this.container = container;
	}
	
	@Override
	public void onSlotChanged() {
		this.container.onSlotAdditionsChanged();
		super.onSlotChanged();
	}

}
