package com.wynprice.fireworks.common.registries;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.api.FireworkBit;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
@ObjectHolder(ElytraPyromancy.MODID)
public class RegistryFireworkBit {
	public static final FireworkBit SPEED = FireworkBit.MISSING;
	public static final FireworkBit SLOW = FireworkBit.MISSING;
	public static final FireworkBit AUTOFLIGHT = FireworkBit.MISSING;
	public static final FireworkBit DURATION = FireworkBit.MISSING;
	public static final FireworkBit NO_PARTICLES = FireworkBit.MISSING;
	public static final FireworkBit PARTICLE_COLOR = FireworkBit.MISSING;

	
	@SubscribeEvent
	public static void onRegistry(RegistryEvent.Register<FireworkBit> event) {
		event.getRegistry().registerAll(
				new FireworkBit(RegistryItem.SPEED_BIT, "speed").setRegistryName("speed"),
				new FireworkBit(RegistryItem.SLOW_BIT, "slow").setRegistryName("slow"),
				new FireworkBit(RegistryItem.AUTOFLIGHT_BIT, "autoflight").setRegistryName("autoflight"),
				new FireworkBit(RegistryItem.DURATION_BIT, "duration").setRegistryName("duration"),
				new FireworkBit(RegistryItem.NO_PARTICLES_BIT, "no_particles").setRegistryName("no_particles"),
				new FireworkBit(RegistryItem.PARTICLE_COLOR_BIT, "particle_color").setRegistryName("particle_color")
		);
	}
}
