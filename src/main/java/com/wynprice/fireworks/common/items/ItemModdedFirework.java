package com.wynprice.fireworks.common.items;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.entities.EntityFirework;
import com.wynprice.fireworks.common.registries.RegistryFireworkBit;
import com.wynprice.fireworks.common.registries.RegistryItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemModdedFirework extends ItemFirework {
	
	public ItemModdedFirework() {
		setRegistryName(new ResourceLocation("minecraft", "fireworks"));
		setUnlocalizedName("fireworks");
		setCreativeTab(ElytraPyromancy.TAB);
		setMaxStackSize(1);
	}
	
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
    	if (!worldIn.isRemote && isUsable(itemstack)) {
            EntityFirework entityfireworkrocket = new EntityFirework(worldIn, (double)((float)pos.getX() + hitX), (double)((float)pos.getY() + hitY), (double)((float)pos.getZ() + hitZ), itemstack);
            worldIn.spawnEntity(entityfireworkrocket);

            if (!player.capabilities.isCreativeMode) {
                itemstack.damageItem(itemstack.getMaxDamage() / 4, player);
            }
        }

        return EnumActionResult.SUCCESS;
    }
    
    public static boolean isUsable(ItemStack stack)
    {
        return stack.getItemDamage() < stack.getMaxDamage() - 1;
    }
    
    @Override
    public int getMaxDamage(ItemStack stack) {
    	FireworkData data = FireworkDataHelper.readDataFromStack(stack);
    	return (data.getLevel() + 1) * 100;
    }
    
    @Override
    public void setDamage(ItemStack stack, int damage) {
    	super.setDamage(stack, damage);
        if (stack.getItemDamage() < 1){
        	super.setDamage(stack, 1);
        }
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
    	if(isUsable(itemstack) && (FireworkDataHelper.isPlayerHoldingBit(playerIn, RegistryFireworkBit.AUTOFLIGHT) || playerIn.isElytraFlying())) {
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
