package com.wynprice.fireworks.client.handler;

import java.util.List;

import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.common.api.FireworkBit;
import com.wynprice.fireworks.common.data.FireworkDataHelper;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid=FireworksMod.MODID, value=Side.CLIENT)
public class HandlerItemColors {
	@SubscribeEvent
	public static void registerItemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().registerItemColorHandler((stack, tint) -> {
			if(tint == 0) {
				return -1;
			}
			ItemStack inStack = FireworkDataHelper.readDataFromStack(stack).getHandler().getStackInSlot(Math.floorDiv(tint % 100000, 1000) - 1);
			List<FireworkBit> list = FireworkDataHelper.getBits(inStack);
			int index = Math.floorDiv(tint, 100000) - 1;
			if(list.size() <= index) {
				return -1;
			}
			return list.get(index).getTintColor(inStack, tint % 1000);
		}, Items.FIREWORKS);
	}
}
