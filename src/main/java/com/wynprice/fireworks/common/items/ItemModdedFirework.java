package com.wynprice.fireworks.common.items;

import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.entities.EntityFirework;
import com.wynprice.fireworks.common.registries.RegistryFireworkBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemModdedFirework extends Item {
	
	public ItemModdedFirework() {
		setRegistryName("firework");
		setUnlocalizedName("firework");
	}
	
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
    
    @Override
    public int getMaxDamage(ItemStack stack) {
    	FireworkData data = FireworkDataHelper.readDataFromStack(stack);
    	return (data.getLevel() + 1) * 100;
    }
    
    @Override
    public boolean isDamageable() {
    	return true;
    }
    
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
    	NBTTagCompound oldData = FireworkDataHelper.readDataFromStack(oldStack).serializeNBT();
    	NBTTagCompound newData = FireworkDataHelper.readDataFromStack(newStack).serializeNBT();
    	for(String key : oldData.getKeySet()) {
    		if(!oldData.getTag(key).equals(newData.getTag(key)) && !key.equals("distance")) {
    			return true;
    		}
    	}
    	return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    	ItemStack itemstack = playerIn.getHeldItem(handIn);
    	if(FireworkDataHelper.isPlayerHoldingBit(playerIn, RegistryFireworkBit.AUTOFLIGHT) || playerIn.isElytraFlying()) {
	        if (!worldIn.isRemote) {
	        	EntityFirework entityfireworkrocket = new EntityFirework(worldIn, itemstack, playerIn);
	            worldIn.spawnEntity(entityfireworkrocket);
	            itemstack.damageItem(1, playerIn);
	        }
	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    	}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);

    }
}
