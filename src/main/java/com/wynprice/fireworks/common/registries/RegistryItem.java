package com.wynprice.fireworks.common.registries;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.common.items.ItemColorBit;
import com.wynprice.fireworks.common.items.ItemModdedFirework;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid=FireworksMod.MODID)
@ObjectHolder(value=FireworksMod.MODID)
public class RegistryItem {
	
	public static final Item SPEED_BIT = Items.AIR;
	public static final Item SLOW_BIT = Items.AIR;
	public static final Item AUTOFLIGHT_BIT = Items.AIR;
	public static final Item DURATION_BIT = Items.AIR;
	public static final Item NO_PARTICLES_BIT = Items.AIR;
	public static final Item PARTICLE_COLOR_BIT = Items.AIR;

	
	@SubscribeEvent
	public static void onItemRegistry(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(new ItemModdedFirework());
		registerItems(event.getRegistry(), 
				new Item().setRegistryName("speed_bit").setUnlocalizedName("speed_bit"),
				new Item().setRegistryName("slow_bit").setUnlocalizedName("slow_bit"),
				new Item().setRegistryName("autoflight_bit").setUnlocalizedName("autoflight_bit"),
				new Item().setRegistryName("duration_bit").setUnlocalizedName("duration_bit"),
				new Item().setRegistryName("no_particles_bit").setUnlocalizedName("no_particles_bit"),
				new ItemColorBit().setRegistryName("particle_color_bit").setUnlocalizedName("particle_color_bit")
		);
	}

	@SubscribeEvent
	public static void onRenderEvent(ModelRegistryEvent event) {
		registerRenderForItems(itemList.toArray(new Item[0]));
	}
	
	private final static ArrayList<Item> itemList = new ArrayList<>();
	
	public static void registerItems(IForgeRegistry<Item> registry, Item... items) {
		itemList.addAll(Lists.newArrayList(items));
		for(Item item : items) {
			if(item instanceof ItemBlock) {
				((ItemBlock)item).getBlock().setCreativeTab(FireworksMod.TAB);
			}
			item.setCreativeTab(FireworksMod.TAB);
		}
		registry.registerAll(items);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenderForItems(Item... items) {
		for(Item item : items) {
			registerRenderForItem(item);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderForItem(Item item) {
		registerRenderForItem(item, (stack) -> new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderForItem(Item item, ItemMeshDefinition definition) {
		ModelLoader.setCustomMeshDefinition(item, definition);
	}
}
