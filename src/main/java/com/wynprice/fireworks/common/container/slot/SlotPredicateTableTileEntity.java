package com.wynprice.fireworks.common.container.slot;

import java.util.function.Predicate;

import com.wynprice.fireworks.common.container.ContainerFireworkTable;

import net.minecraft.item.ItemStack;

public class SlotPredicateTableTileEntity extends SlotPredicate {

	public SlotPredicateTableTileEntity(ContainerFireworkTable container, ItemStack stack, int xPosition, int yPosition,
			Predicate<ItemStack> predicate) {
		super(container, stack, xPosition, yPosition, predicate);
	}
	
	@Override
	public void onSlotChanged() {
		this.container.onSlotAdditionsChanged();
		super.onSlotChanged();
	}

}
