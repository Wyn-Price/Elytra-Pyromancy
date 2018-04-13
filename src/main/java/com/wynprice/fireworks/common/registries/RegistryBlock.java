package com.wynprice.fireworks.common.registries;

import java.util.ArrayList;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.blocks.BlockFireworkTable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid=ElytraPyromancy.MODID)
@ObjectHolder(ElytraPyromancy.MODID)
public class RegistryBlock {
	
	public static final Block FIREWORK_TABLE = Blocks.AIR;
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
				new BlockFireworkTable()
		);
	}
	
	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
		RegistryItem.registerItems(event.getRegistry(), 
				createItemBlock(FIREWORK_TABLE, 64)
		);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelRegister(ModelRegistryEvent event) {
		RegistryItem.registerRenderForItems(items.toArray(new Item[0]));
	}
	
	
	
	private static ArrayList<Item> items = new ArrayList<>();
	
	private static Item createItemBlock(Block block, int stacksize) {
		ItemBlock item = new ItemBlock(block);
		item.setMaxStackSize(stacksize);
		item.setRegistryName(block.getRegistryName());
		items.add(item);
		return item;
	}
}
