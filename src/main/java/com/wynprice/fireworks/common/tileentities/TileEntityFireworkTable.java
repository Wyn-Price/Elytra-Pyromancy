package com.wynprice.fireworks.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityFireworkTable extends SyncableTileEntity {
	private ItemStack stack = ItemStack.EMPTY;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		stack = new ItemStack(compound.getCompoundTag("itemstack"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("itemstack", stack.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	public void setStack(ItemStack stack) {
		this.stack = stack;
	}
}
