package com.wynprice.fireworks.client.gui;

import java.io.IOException;

import com.wynprice.fireworks.common.data.FireworkData;
import com.wynprice.fireworks.common.network.EPNetwork;
import com.wynprice.fireworks.common.network.packets.MessagePacketSpeedGuiClosed;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class GuiChangeFireworkSpeed extends GuiScreen {
	private final FireworkData data;
	private final EnumHand hand;
	public GuiChangeFireworkSpeed(ItemStack stack, EnumHand hand) {
		this.data = FireworkData.fromStack(stack);
		this.hand = hand;
	}
		
	@Override
	public void initGui() {
		addButton(new GuiButton(0, this.width / 2 - 15, this.height / 2 - 50, 30, 20, "^"));
		addButton(new GuiButton(1, this.width / 2 - 15, this.height / 2 + 30, 30, 20, "V"));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, String.valueOf(Math.round(data.getSpeed() * 10f) / 10f), this.width / 2, this.height / 2, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 0) {
			data.setSpeed(data.getSpeed() + 0.1f);
		} else if(button.id == 1) {
			data.setSpeed(data.getSpeed() - 0.1f);
		}
		super.actionPerformed(button);
	}
	
	@Override
	public void onGuiClosed() {
		EPNetwork.sendToServer(new MessagePacketSpeedGuiClosed(data, hand));
	}
}
