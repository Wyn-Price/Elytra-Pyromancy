package com.wynprice.fireworks.common.registries;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.items.ItemModdedFirework;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
@ObjectHolder(value=ElytraPyromancy.MODID)
public class RegistryItem {
	public static final Item FIREWORK = Items.AIR;
	public static final Item SPEED_BIT = Items.AIR;
	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(new ItemModdedFirework(), new Item().setRegistryName("speed_bit").setUnlocalizedName("speed_bit"));
	}

}
