package com.wynprice.fireworks.client.handler;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.data.FireworkDataHelper;
import com.wynprice.fireworks.common.data.FireworkItemStackHandler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid=ElytraPyromancy.MODID, value=Side.CLIENT)
public class HandlerItemColors {
	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().registerItemColorHandler((stack, tint) -> {
			if(tint == 0) {
				return -1;
			}
			ItemStack inStack = FireworkDataHelper.readDataFromStack(stack).getHandler().getStackInSlot(Math.floorDiv(tint % 100000, 1000) - 1);
			return FireworkDataHelper.getBits(inStack).get(Math.floorDiv(tint, 100000) - 1).getTintColor(inStack, tint % 1000);
		}, Items.FIREWORKS);
	}
}
