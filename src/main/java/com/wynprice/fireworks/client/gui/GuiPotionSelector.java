package com.wynprice.fireworks.client.gui;

import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.common.container.ContainerPotionSelector;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import net.minecraftforge.items.ItemStackHandler;

public class GuiPotionSelector extends GuiContainer {
	private final EnumHand hand;
	private final ItemStack stack;
	private final EntityPlayer player;
	
	private final ItemStack potionStack;
	
	public GuiPotionSelector(EntityPlayer player, EnumHand hand, ItemStack stack) {
		super(new ContainerPotionSelector(player, stack));
		this.hand = hand;
		this.stack = stack;
		this.player = player;
		
		potionStack = new ItemStack(stack.getOrCreateSubCompound("potion_bit_item"));		
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/gui/container/generic_54.png"));
		TextureManager manager = Minecraft.getMinecraft().renderEngine;
		this.zLevel = 0;
		ResourceLocation slotLocation = new ResourceLocation("minecraft", "textures/gui/container/generic_54.png");
		manager.bindTexture(slotLocation);
		int id = 0;
		for(Slot slot : this.inventorySlots.inventorySlots) {
			if(id > 51) {
				GlStateManager.color(0.95f, 0.83f, 0.21f);
			} else {
				GlStateManager.color(1f, 1f, 1f);
			}
        	this.drawTexturedModalRect(this.guiLeft + slot.xPos - 1, this.guiTop + slot.yPos - 1, 7, 17, 18, 18);
        	if(id == 0 && !slot.getHasStack()) {
				manager.bindTexture(new ResourceLocation(FireworksMod.MODID, "textures/gui/widgits/slot_backgrounds.png"));
				this.drawModalRectWithCustomSizedTexture(this.guiLeft + slot.xPos, this.guiTop + slot.yPos, 16, 0, 16, 16, 32, 16);
				manager.bindTexture(slotLocation);
			}
        	id++;
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
}
