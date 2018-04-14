package com.wynprice.fireworks.client.handler;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.client.rendering.FireworkModel;
import com.wynprice.fireworks.common.registries.RegistryItem;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid=ElytraPyromancy.MODID, value=Side.CLIENT)
public class ModelHandler {
		
	@SubscribeEvent
	public static void onModelsLoaded(ModelBakeEvent event) {
		for(ModelResourceLocation location : event.getModelRegistry().getKeys()) {
			if(location.getVariant().equals("inventory")) {
				if(Items.FIREWORKS.getRegistryName().equals(location)) {
					event.getModelRegistry().putObject(location, new FireworkModel(event.getModelRegistry().getObject(location)));
				}
			}
		}
	}
}
