package com.wynprice.fireworks.common.container.slot;

import java.util.function.Predicate;

import com.wynprice.fireworks.common.container.ContainerFireworkTable;
import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.registries.RegistryItem;
import com.wynprice.fireworks.common.tileentities.TileEntityFireworkTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotFireworkTableTileEntity extends SlotItemHandler {

	private final TileEntityFireworkTable table;
	private final ContainerFireworkTable container;
	
	public SlotFireworkTableTileEntity(ContainerFireworkTable container, TileEntityFireworkTable table, int index, int xPosition, int yPosition) {
		super(new DummyItemHandler(table), index, xPosition, yPosition);
		this.table = table;
		this.container = container;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == RegistryItem.FIREWORK;
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		if(super.canTakeStack(playerIn)) {
			ItemStack stack = getStack();
			FireworkData data = FireworkDataHelper.readDataFromStack(stack);
			for(int i = 37; i < container.inventorySlots.size(); i++) {
				data.getHandler().setStackInSlot(i - 37, container.inventorySlots.get(i).getStack());
			}
			FireworkDataHelper.writeDataToStack(data, stack);
			return true;
		}
		return false;
	}
	
	@Override
	public void onSlotChanged() {
		container.onSLot0Changed();
		super.onSlotChanged();
	}
	
	static class DummyItemHandler extends ItemStackHandler {
		
		private final TileEntityFireworkTable table;

		
		public DummyItemHandler(TileEntityFireworkTable table) {
			this.table = table;
			this.stacks = NonNullList.withSize(1, table.getStack());
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			this.table.setStack(this.getStackInSlot(slot));
			this.table.markDirty();
		}
	}

}
