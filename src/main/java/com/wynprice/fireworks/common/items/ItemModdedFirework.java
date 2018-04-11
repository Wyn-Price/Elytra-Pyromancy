package com.wynprice.fireworks.common.items;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.entities.EntityFirework;
import com.wynprice.fireworks.common.handlers.GuiHandler;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemModdedFirework extends Item {
	
	public ItemModdedFirework() {
		setRegistryName("firework");
		setUnlocalizedName("moddedfirework");
	}
	
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
//    	if(playerIn.isSneaking()) {
//    		playerIn.openGui(ElytraPyromancy.getInstance(), GuiHandler.FIREWORKGUI, worldIn, handIn.ordinal(), 0, 0);
//            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
//    	}
        if (playerIn.isElytraFlying()) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            if (!worldIn.isRemote) {
            	EntityFirework entityfireworkrocket = new EntityFirework(worldIn, itemstack, playerIn);
                worldIn.spawnEntity(entityfireworkrocket);
                if (!playerIn.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
        }
    }
}
