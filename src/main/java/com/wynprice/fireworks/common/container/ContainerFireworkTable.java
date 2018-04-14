package com.wynprice.fireworks.common.container;

import java.awt.Point;
import java.util.List;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.container.slot.SlotFireworkTableTileEntity;
import com.wynprice.fireworks.common.container.slot.SlotPredicate;
import com.wynprice.fireworks.common.container.slot.SlotPredicateTableTileEntity;
import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.data.FireworkItemStackHandler;
import com.wynprice.fireworks.common.tileentities.TileEntityFireworkTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ContainerFireworkTable extends Container {

	private final EntityPlayer player;
	private final TileEntityFireworkTable table;
	
	public ContainerFireworkTable(EntityPlayer player, TileEntityFireworkTable table) {
		this.player = player;
		this.table = table;
		addSlotToContainer(new SlotFireworkTableTileEntity(this, table, 0, 80, 25));
		
		
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
        
        onSlot0Changed();
	}
	
	public void onSlotAdditionsChanged() {
		Slot slot = getSlot(0);
		ItemStack stack = slot.getStack();
		FireworkData data = FireworkDataHelper.readDataFromStack(stack);
		for(int i = 37; i < inventorySlots.size(); i++) {
			data.getHandler().setStackInSlot(i - 37, inventorySlots.get(i).getStack());
		}
		FireworkDataHelper.writeDataToStack(data, stack);
		slot.putStack(stack);
	}
	
	public void onSlot0Changed() {
		FireworkData data = FireworkDataHelper.readDataFromStack(getSlot(0).getStack());
		if(this.getSlot(0).getHasStack()) {
			if(this.inventorySlots.size() != data.getModifiers() + 37) {
				double theta = ((Math.PI*2) / (double) Math.min(data.getModifiers(), 15));
				double innerTheta = ((Math.PI*2) / (15D - data.getModifiers()));
				Point center = new Point(80, 25);
				FireworkItemStackHandler handler = data.getHandler();
				for(int i = 0; i < Math.min(handler.getSlots(), 15); i++) {
					Point point = rotatePointAbout(i, center, theta, 57);
					this.addSlotToContainer(new SlotPredicateTableTileEntity(this, handler.getStackInSlot(i), point.x, point.y, stack -> true/*//TODO change to only registered items*/).setStackSize(1));
				}
				if(handler.getSlots() > 15) {
					for(int i = 15; i < handler.getSlots(); i++) {
						Point point = rotatePointAbout(i, center, innerTheta, 30);
						this.addSlotToContainer(new SlotPredicateTableTileEntity(this, handler.getStackInSlot(i), point.x, point.y, stack -> true/*//TODO change to only top tier items*/).setStackSize(1));
					}
				}
			}
			
		} else if(this.inventorySlots.size() > 37){
			pollSlots();
		}
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		onSlotAdditionsChanged();
		super.onContainerClosed(playerIn);
	}
	
	private Point rotatePointAbout(int index, Point about, double theta, int radius) {
		double angle = theta * index;
		double newX = Math.sin(angle) * radius + about.x;
		double newY = -Math.cos(angle) * radius + about.y;
		return new Point((int) newX, (int) newY);
	}
	
	private void pollSlots() {
		List<Slot> dummyInventorySlots = Lists.<Slot>newArrayList();
	    for(int i = 0; i < 37; i++) {
	    	dummyInventorySlots.add(this.inventorySlots.get(i));
	    }
	    this.inventorySlots = dummyInventorySlots;
	    
	    NonNullList<ItemStack> dummyInventoryItemStacks = NonNullList.<ItemStack>create();
	    for(int i = 0; i < 37; i++) {
	    	dummyInventoryItemStacks.add(this.inventoryItemStacks.get(i));
	    }
	    this.inventoryItemStacks = dummyInventoryItemStacks;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = ItemStack.EMPTY;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();
	        if (fromSlot < 1 || fromSlot > 36) {
	            if (!this.mergeItemStack(current, 1, 37, true)) {
	                return ItemStack.EMPTY;
	            }
	        } else {
	            if (!this.mergeItemStack(current, 0, 1, false) && !this.mergeItemStack(current, 37, this.inventorySlots.size(), false)) {
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
