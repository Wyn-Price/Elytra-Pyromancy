package com.wynprice.fireworks.client.handler;

import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.client.gui.GuiColorSelector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid=FireworksMod.MODID, value=Side.CLIENT)
public class GameLoopHandler {
	private static int tick = 0;
	@SubscribeEvent
	public static void onTick(TickEvent.ClientTickEvent event) {
		GuiScreen screen = Minecraft.getMinecraft().currentScreen;
		if(event.phase == Phase.END && screen instanceof GuiColorSelector) {
			((GuiColorSelector)screen).updateParticles();
		}
	}
}
