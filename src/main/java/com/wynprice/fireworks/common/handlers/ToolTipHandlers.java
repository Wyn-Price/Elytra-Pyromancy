package com.wynprice.fireworks.common.handlers;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.registries.RegistryItem;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
public class ToolTipHandlers {
	@SubscribeEvent
	public static void onToolTipEvent(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if(stack.getItem() == Items.FIREWORKS) {
			FireworkData data = FireworkDataHelper.readDataFromStack(stack);
			event.getToolTip().add("Distance: " + String.valueOf(Math.round(data.getDistance() * 100D) / 100D) + "m / " + FireworkDataHelper.getDistanceNeededForLevelUp(data.getLevel()) + "m");
		}
	}
}
