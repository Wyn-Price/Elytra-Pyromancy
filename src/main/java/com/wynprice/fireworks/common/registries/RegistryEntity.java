package com.wynprice.fireworks.common.registries;

import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.common.entities.EntityFirework;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@EventBusSubscriber(modid=FireworksMod.MODID)
public class RegistryEntity {
	@SubscribeEvent
	public static void onEntityRegister(RegistryEvent.Register<EntityEntry> event) {
		event.getRegistry().register(EntityEntryBuilder.create()
				.entity(EntityFirework.class)
				.id(new ResourceLocation(FireworksMod.MODID, "firework"), 71)//TODO config network ID
				.factory(EntityFirework::new)
				.name("fireworksfirework")
				.tracker(64, 20, true)
				.build());
	}

}
