package com.wynprice.fireworks.common.container;

import com.wynprice.fireworks.common.container.slot.SlotPredicate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerPotionSelector extends Container {

	private final ItemStack stack;
	
	public ContainerPotionSelector(EntityPlayer player, ItemStack stack) {
		this.stack = stack;
		
		this.addSlotToContainer(new SlotPredicate(new ItemStack(stack.getOrCreateSubCompound("potion_bit_item")), 80, 25, itemstack -> itemstack.getItem() == Items.POTIONITEM));
		
		for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(player.inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(player.inventory, i1, 8 + i1 * 18, 161));
        }
		
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		NBTTagCompound tag = stack.getTagCompound();
		if(tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		
		tag.setTag("potion_bit_item", this.inventorySlots.get(0).getStack().serializeNBT());

		super.onContainerClosed(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = ItemStack.EMPTY;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();
	        if (fromSlot < 1) {
	            if (!this.mergeItemStack(current, 1, 37, true)) {
	                return ItemStack.EMPTY;
	            }
	        } else {
	            if (!this.mergeItemStack(current, 0, 1, false)) {
	                return ItemStack.EMPTY;
	            }
	        }
	        if (current.getCount() == 0)
	            slot.putStack(ItemStack.EMPTY);
	        else
	            slot.onSlotChanged();

	        if (current.getCount() == previous.getCount())
	            return null;
	        slot.onTake(playerIn, current);
	    }
	    return previous;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
