package com.wynprice.fireworks.common.handlers;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.registries.RegistryItem;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
public class ToolTipHandlers {
	@SubscribeEvent
	public static void onToolTipEvent(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if(stack.getItem() == RegistryItem.FIREWORK) {
			event.getToolTip().add("Distance: " + String.valueOf(Math.round(FireworkDataHelper.readDataFromStack(stack).getDistance() * 100D) / 100D) + "m");
		}
	}
}
