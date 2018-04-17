package com.wynprice.fireworks.common.api;

import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.BiMap;
import com.google.common.collect.Maps;
import com.wynprice.fireworks.ElytraPyromancy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
public class ElytraRegistery {
	
	private static IForgeRegistry<FireworkBit> REGISTRY;

	@SubscribeEvent
	public static void register(RegistryEvent.NewRegistry event) {		
 		REGISTRY = new RegistryBuilder<FireworkBit>()
 			.setType(FireworkBit.class)
 			.setName(FireworkBit.RESOURCELOCATION)
	 		.set(key -> new FireworkBit(stack -> false, "missing").setRegistryName(key))
	 		.set((key, isNetwork) -> FireworkBit.MISSING)
	 		.create();
	}
	
    public static IForgeRegistry<FireworkBit> getRegistry() {
		return REGISTRY;
	}
    
}
