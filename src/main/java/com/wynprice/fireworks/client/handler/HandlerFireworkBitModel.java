package com.wynprice.fireworks.client.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.common.api.FireworksModRegistery;
import com.wynprice.fireworks.common.util.NonIndentedPrintStream;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber(modid=FireworksMod.MODID, value=Side.CLIENT)
@SideOnly(Side.CLIENT)
public class HandlerFireworkBitModel {
	@SubscribeEvent
	public static void onModelBaked(TextureStitchEvent event) {
		FireworksModRegistery.getRegistry().forEach(bit -> {
			ResourceLocation loc = bit.getRegistryName();
			IBakedModel model;
			try {
				model = getModel(new ResourceLocation(loc.getResourceDomain(), "bits/" + loc.getResourcePath()), event);
			} catch (Exception e) {
				e.printStackTrace(NonIndentedPrintStream.INSTANCE);
				return;
			}
			List<BakedQuad> quadList = model.getQuads(null, null, 0);
			bit.setBakedQuads(quadList);
		});
	}
		
	public static IBakedModel getModel(ResourceLocation resourceLocation, TextureStitchEvent event) throws Exception {
	    return ModelLoaderRegistry.getModel(resourceLocation).bake(TRSRTransformation.identity(), DefaultVertexFormats.ITEM, event.getMap()::registerSprite);
	}
}
