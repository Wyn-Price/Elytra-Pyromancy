package com.wynprice.fireworks.common.api;

import java.util.Map;

import com.google.common.collect.Maps;
import com.wynprice.fireworks.ElytraPyromancy;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry.ClearCallback;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
public class ElytraRegistery {
	
	private static IForgeRegistry<FireworkBit> REGISTRY;
	
	private static final Map<Item, String> initItems = Maps.newHashMap();
	
	@SubscribeEvent
	public static void register(RegistryEvent.NewRegistry event) {
		RegistryBuilder<FireworkBit> builder = initBuilder(new RegistryBuilder<>(), FireworkBit.class);
 		REGISTRY = builder
 			.setName(FireworkBit.RESOURCELOCATION)
	 		.set(key -> new FireworkBit(Items.AIR, "missing").setRegistryName(key))
	 		.set((key, isNetwork) -> FireworkBit.MISSING)
	 		.add(createClearCallback((owner, stage) -> initItems.clear()))
	 		.add((owner, stage, id, bit, oldBit) -> {
	 			Item item = bit.getItem();
	 			String currentModID = Loader.instance().activeModContainer().getModId();
	 			if(initItems.containsKey(item)) {
	 				ElytraPyromancy.getLogger().warn("Item {} was registered twice, from mods {} and {}. ElytraPyromancy will not be liable for any issues this may cause", item.getRegistryName(), initItems.get(item), currentModID);
	 			} else {
	 				initItems.put(item, currentModID);
	 			}
	 		})
	 		.create();
	}
	
    //Needed for some reason
    private static <T> RegistryBuilder initBuilder(RegistryBuilder builder, Class<T> type) {
        return builder.setType(type);
    }
    
    private static ClearCallback<FireworkBit> createClearCallback(ClearCallback<FireworkBit> callback) {
    	return callback;
    }
    
    public static IForgeRegistry<FireworkBit> getRegistry() {
		return REGISTRY;
	}
    
}
