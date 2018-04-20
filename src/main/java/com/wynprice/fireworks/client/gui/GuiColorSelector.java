package com.wynprice.fireworks.client.gui;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.omg.CORBA.BooleanHolder;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.network.EPNetwork;
import com.wynprice.fireworks.common.network.packets.MessagePacketUpdateColorBit;
import com.wynprice.fireworks.common.util.ColorConfig;

import io.netty.util.internal.IntegerHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFirework;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiColorSelector extends GuiScreen {

	private Point currentColorWheel = new Point(-1, -1);
	private int noBrightnessColor = -1;
	private boolean lightnessBarClicked = false;
	private float barAmount = 1f;
	
	private final EnumHand hand;
	private final ItemStack stack;
	private final ColorConfig config;
	
	private IntegerHolder startColor = new IntegerHolder();
	private IntegerHolder endColor = new IntegerHolder();
	
	private IntegerHolder currentSelected = null;
	
	private final List<GuiFireworkParticle> renderParticles = Lists.newArrayList();
	
	public GuiColorSelector(EnumHand hand, ItemStack stack) {
		this.hand = hand;
		this.stack = stack;
		this.config = ColorConfig.fromItemStack(stack);
		
		startColor.value = this.config.getStart().getColor() | 0xFF000000;
		endColor.value = this.config.getEnd().getColor() | 0xFF000000;
		this.currentColorWheel = new Point(Minecraft.getMinecraft().displayWidth / 2, Minecraft.getMinecraft().displayHeight / 2);
		barAmount = 1f;
	}
	
	private Rectangle startColorButton;
	private Rectangle endColorButton;
	
	@Override
	public void initGui() {
		this.startColorButton = new Rectangle(this.width / 2 - 120, this.height / 4 - 35 , 50, 20);
		this.endColorButton = new Rectangle(this.width / 2 + 70, this.height / 4 - 35, 50, 20);
		renderParticles.forEach(particle -> {
			particle.setGuiPosX(this.width);
			particle.setGuiPosY(this.height);
		});
	}
	
	private int ticks;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		List<GuiFireworkParticle> newRenderParticle = Lists.newArrayList();
		if(ticks++ % 30 == 0) {
			GuiFireworkParticle p = new GuiFireworkParticle(this.width, this.height);
			p.setColor(this.startColor.value);
			p.setFadeColor(this.endColor.value);
			p.setFadingColor(true);
			newRenderParticle.add(p);
		}
		renderParticles.forEach(particle -> {
			if(!particle.isDead()) {
				newRenderParticle.add(particle);
			}
		});
		this.renderParticles.clear();
		this.renderParticles.addAll(newRenderParticle);
		
		this.drawDefaultBackground();
		this.drawCenteredString(fontRenderer, I18n.format("gui.colors.start"), this.width / 2 - 95, this.height / 4 - 50, 0xFFFFFFFF);
		this.drawCenteredString(fontRenderer, I18n.format("gui.colors.end"), this.width / 2 + 95, this.height / 4 - 50, 0xFFFFFFFF);

        if(currentSelected != null) {
        	 GlStateManager.enableAlpha();
     		GlStateManager.disableLighting();
     		
     		int colorPre = getColorUnderMouse();
     		
     		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(new ResourceLocation(ElytraPyromancy.MODID, "textures/gui/widgits/color_wheel.png"));
     		this.drawModalRectWithCustomSizedTexture(this.width / 2 - 64, this.height / 2 - 64, 0, 0, 128, 128, 128, 128);

     		int newColor = getColorUnderMouse();
     	
     		if(colorPre != newColor && Mouse.isButtonDown(0) && !lightnessBarClicked) {
     			this.currentColorWheel = new Point(Mouse.getX(), Mouse.getY());
     			currentSelected.value = newColor;
     			this.noBrightnessColor = newColor;
     		}
     		
     		if(lightnessBarClicked || noBrightnessColor == -1) {
     			IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
     	        int[] ints = new int[1];
     	        GlStateManager.glReadPixels(currentColorWheel.x, currentColorWheel.y, 1, 1, 32993, 33639, intbuffer);
     	        intbuffer.get(ints);
     	        currentSelected.value = ints[0];
     	        if(noBrightnessColor == -1) {
     	        	noBrightnessColor = currentSelected.value;
     	        }
     	        
     		}
     				
     		float[] hsbs = Color.RGBtoHSB((currentSelected.value >> 16) & 0xFF, (currentSelected.value >> 8) & 0xFF, currentSelected.value & 0xFF, null);
     		currentSelected.value = Color.HSBtoRGB(hsbs[0], hsbs[1], barAmount);
     		
     		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(new ResourceLocation(ElytraPyromancy.MODID, "textures/gui/widgits/color_wheel_border.png"));
     		this.drawModalRectWithCustomSizedTexture(this.width / 2 - 65, this.height / 2 - 65, 0, 0, 130, 130, 130, 130);
     		
     		Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(new ResourceLocation(ElytraPyromancy.MODID, "textures/gui/widgits/color_wheel_cursor.png"));
     		this.drawModalRectWithCustomSizedTexture(currentColorWheel.x * new ScaledResolution(this.mc).getScaledWidth() / this.mc.displayWidth - 4, 
     				new ScaledResolution(this.mc).getScaledHeight() - currentColorWheel.y * new ScaledResolution(this.mc).getScaledHeight() / this.mc.displayHeight - 1 - 4, 0, 0, 8, 8, 8, 8);
     		
     		
     		this.drawHorizontalGradient(this.width / 2 + 70, this.height / 5 * 4 + 20, this.width / 2 - 70, this.height / 5 * 4, 0xFF000000, noBrightnessColor);		
     		
     		int xPosition = (int) (this.width / 2 + 70 - (1 - barAmount) * 140);
     		this.drawRect(xPosition - 3, this.height / 5 * 4 - 3, xPosition + 3, this.height / 5 * 4 + 23, 0xFF000000);
     		
     		drawBorder(this.width / 2 + 70, this.height / 5 * 4 + 20, this.width / 2 - 70, this.height / 5 * 4, 0xFF000000, 2);
        } else {
        	int scale = 5;
        	float jitterX = (new Random().nextFloat() - 0.5f) * 2f;
        	float jitterY = (new Random().nextFloat() - 0.5f) * 2f;

            GlStateManager.translate(jitterX, jitterY, 0);
        	GlStateManager.scale(scale, scale, scale);
        	itemRender.renderItemIntoGUI(new ItemStack(Items.FIREWORKS), this.width / (2 * scale) - 8, this.height / (2 * scale) - 10);
        	GlStateManager.scale(1f / scale, 1f / scale, 1f / scale);
            GlStateManager.translate(-jitterX, -jitterY, 0);
        	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/particle/particles.png")); //TODO: cache
        	renderParticles.forEach(particle -> particle.render(this, partialTicks));
        }
		
		int startColor = this.startColor.value;
		int endColor = this.endColor.value;
		
		int boxStartColor = startColor;
		int boxEndColor = endColor;

		if(isMouseOverRect(startColorButton, mouseX, mouseY) || currentSelected == this.startColor) {
			boxStartColor = 0xFF0078D7;
		} else if(isMouseOverRect(endColorButton, mouseX, mouseY) || currentSelected == this.endColor) {
			boxEndColor = 0xFF0078D7;
		}
		this.drawRect(startColorButton.x, startColorButton.y, startColorButton.x + startColorButton.width, startColorButton.y + startColorButton.height, boxStartColor);
		this.drawRect(endColorButton.x, endColorButton.y, endColorButton.x + endColorButton.width, endColorButton.y + endColorButton.height, boxEndColor);
		
		drawHorizontalGradient(endColorButton.x, endColorButton.y + endColorButton.height, startColorButton.x + startColorButton.width, startColorButton.y, startColor, endColor);
		drawBorder(startColorButton.x, startColorButton.y, endColorButton.x + endColorButton.width, endColorButton.y + endColorButton.height, 0xFF000000, 2);
		
        GuiScreen.drawRect(startColorButton.x + startColorButton.width + 1, startColorButton.y + 1, startColorButton.x + startColorButton.width - 1, startColorButton.y + startColorButton.height - 1, 0xFF000000);
        GuiScreen.drawRect(endColorButton.x - 1, endColorButton.y + 1, endColorButton.x + 1, endColorButton.y + endColorButton.height - 1, 0xFF000000);
                
        ColorConfig.SingleConfig config = null;
        if(currentSelected == this.startColor) {
        	config = this.config.getStart();
        } else if(currentSelected == this.endColor) {
        	config = this.config.getEnd();
        }
        
        if(config != null) {
        	config.setPoint(new Point(this.currentColorWheel.x - Minecraft.getMinecraft().displayWidth / 2, this.currentColorWheel.y - Minecraft.getMinecraft().displayHeight / 2));
        	config.setColor(this.currentSelected.value);
        	config.setLightnessBar(this.barAmount);
        }
	}
	
	public void updateParticles() {
    	renderParticles.forEach(p -> p.onUpdate());

	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ColorConfig.SingleConfig config = null;
        IntegerHolder currentSelected = null;
		if(isMouseOverRect(startColorButton, mouseX, mouseY)) {
			currentSelected = startColor;
			config = this.config.getStart();
		} else if(isMouseOverRect(endColorButton, mouseX, mouseY)) {
			currentSelected = endColor;
			config = this.config.getEnd();
		} else if(mouseX >= this.width / 2 - 70 && mouseX <= this.width / 2 + 70 && mouseY >= this.height / 5 * 4 && mouseY <= this.height / 5 * 4 + 20) {
			lightnessBarClicked = true;
			barAmount = (mouseX - this.width / 2 + 70) / 140f;
		}
		
		if(config != null) {
			if(this.currentSelected == currentSelected) {
				this.currentSelected = null;
			} else {
				this.currentSelected = currentSelected;
				this.currentColorWheel = new Point(Minecraft.getMinecraft().displayWidth / 2 + config.getPoint().x, Minecraft.getMinecraft().displayHeight / 2 + config.getPoint().y);
				this.currentSelected.value = config.getColor();
				this.barAmount = config.getLightnessBar();
			}
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
		lightnessBarClicked = false;
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
		super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
		if(lightnessBarClicked) {
			barAmount = MathHelper.clamp((mouseX - this.width / 2 + 70) / 140f, 0f, 1f);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 && this.currentSelected != null) {
			this.currentSelected = null;
		} else {
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	public void onGuiClosed() {
		this.config.write();
		EPNetwork.sendToServer(new MessagePacketUpdateColorBit(this.stack.getOrCreateSubCompound("color_configs"), this.hand));
		super.onGuiClosed();
	}
	
	public static int getColorUnderMouse()
	{
        IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
        int[] ints = new int[1];
        GlStateManager.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, 32993, 33639, intbuffer);
        intbuffer.get(ints);
        return ints[0];
	}
	
	private boolean isMouseOverRect(Rectangle rect, int mouseX, int mouseY) {
		return mouseX > rect.x && mouseX < rect.x + rect.width && mouseY > rect.y && mouseY < rect.y + rect.height;
	}
	
	public void drawHorizontalGradient(int left, int top, int right, int bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)this.zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)this.zLevel).color(f1, f2, f3, f).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
	
	public void drawBorder(int left, int top, int right, int bottom, int color, int thickness) {
		thickness /= 2;
		GuiScreen.drawRect(left - thickness, top - thickness, right + thickness, top + thickness, color);
        GuiScreen.drawRect(left - thickness, bottom - thickness, right + thickness, bottom + thickness, color);
        GuiScreen.drawRect(left - thickness, top + thickness, left + thickness, bottom - thickness, color);
        GuiScreen.drawRect(right + thickness, top + thickness, right - thickness, bottom - thickness, color);
	}

}
