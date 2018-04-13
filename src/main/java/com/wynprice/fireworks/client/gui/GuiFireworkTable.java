package com.wynprice.fireworks.client.gui;

import com.wynprice.fireworks.common.container.ContainerFireworkTable;
import com.wynprice.fireworks.common.tileentities.TileEntityFireworkTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiFireworkTable extends GuiContainer {

	private final EntityPlayer player;
	private final TileEntityFireworkTable table;
	
	public GuiFireworkTable(EntityPlayer player, TileEntityFireworkTable table) {
		super(new ContainerFireworkTable(player, table));
		this.player = player;
		this.table = table;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		
		this.zLevel = 0;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/gui/container/generic_54.png"));
		int id = 0;
		for(Slot slot : this.inventorySlots.inventorySlots) {
			if(id++ > 51) {
				GlStateManager.color(0.95f, 0.83f, 0.21f);
			} else {
				GlStateManager.color(1f, 1f, 1f);
			}
        	this.drawTexturedModalRect(this.guiLeft + slot.xPos - 1, this.guiTop + slot.yPos - 1, 7, 17, 18, 18);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
