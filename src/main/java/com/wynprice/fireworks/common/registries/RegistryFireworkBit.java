package com.wynprice.fireworks.common.registries;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.api.FireworkBit;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
public class RegistryFireworkBit {
	@SubscribeEvent
	public static void onRegistry(RegistryEvent.Register<FireworkBit> event) {
		event.getRegistry().registerAll(new FireworkBit(RegistryItem.SPEED_BIT, "speed").setRegistryName("speed"));
	}
}
