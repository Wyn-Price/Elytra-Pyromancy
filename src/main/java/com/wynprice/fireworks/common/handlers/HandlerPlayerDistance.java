package com.wynprice.fireworks.common.handlers;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;
import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.items.ItemModdedFirework;
import com.wynprice.fireworks.common.registries.RegistryItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
public class HandlerPlayerDistance {
	private static Map<UUID, Vec3d> previousMap = Maps.newHashMap();
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if(!player.world.isRemote && player.isElytraFlying()) {
			if(previousMap.containsKey(player.getUniqueID())) {
				Vec3d prevPos = previousMap.get(player.getUniqueID());
				double distance = prevPos.distanceTo(player.getPositionVector());
		        for(EnumHand hand : EnumHand.values()) {
		        	ItemStack stack = player.getHeldItem(hand);
		        	if(!stack.isEmpty() && stack.getItem() == Items.FIREWORKS && ItemModdedFirework.isUsable(stack)) {
		    	        FireworkData data = FireworkDataHelper.readDataFromStack(stack);
		    	        data.moveDistance(player, distance);
		    	        FireworkDataHelper.writeDataToStack(data, stack);
		        		break;
		        	}
		        }
			}
			previousMap.put(player.getUniqueID(), player.getPositionVector());
		}
	}
}
