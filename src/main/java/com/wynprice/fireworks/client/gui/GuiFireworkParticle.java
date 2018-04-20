package com.wynprice.fireworks.client.gui;

import java.util.Random;

import lombok.Data;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;

@Data
public class GuiFireworkParticle {
	
    private static final AxisAlignedBB EMPTY_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	
	private final int textureIdx;
    private final int numAgingFrames;
    private final float yAccel;
    protected double posX;
    protected double posY;
    protected double motionX;
    protected double motionY;
    protected int particleTextureIndexX;
    protected int particleTextureIndexY;
    protected float particleTextureJitterX;
    protected float particleTextureJitterY;
    protected int particleAge;
    protected int particleMaxAge;
    protected float particleRed;
    protected float particleGreen;
    protected float particleBlue;
    protected float particleAlpha;
    protected boolean isDead;
    private float baseAirFriction = 0.91F;
    private float fadeTargetRed;
    private float fadeTargetGreen;
    private float fadeTargetBlue;
    private boolean fadingColor;
    
    private final Random rand;
    
    private int guiPosX;
    private int guiPosY;
    
    public GuiFireworkParticle(int guiPosX, int guiPosY) { 
    	this.guiPosX = guiPosX;
    	this.guiPosY = guiPosY;
		this.textureIdx = 160;
		this.numAgingFrames = 8;
		this.yAccel = -0.004F;
		
		this.rand = new Random();
        this.particleAlpha = 1.0F;
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
        this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
        this.particleMaxAge = (int) (35 + this.rand.nextFloat() * 10);
        this.particleAge = 0;
        
        this.motionY = 50 + this.rand.nextFloat() * 16f;
        this.motionX = (this.rand.nextFloat() - 0.5f) * 32f;
        
        onUpdate();
	}

    public void setColor(int color)
    {
        this.particleRed = ((color & 16711680) >> 16) / 255.0F;
        this.particleGreen = ((color & 65280) >> 8) / 255.0F;
        this.particleBlue = ((color & 255) >> 0) / 255.0F;
    }
    
    public void setFadeColor(int color)
    {
        this.fadeTargetRed = ((color & 16711680) >> 16) / 255.0F;
        this.fadeTargetGreen = ((color & 65280) >> 8) / 255.0F;
        this.fadeTargetBlue = ((color & 255) >> 0) / 255.0F;
    }
	
	public void onUpdate() {
		if (this.particleAge++ >= this.particleMaxAge)
        {
			isDead = true;
			return;
        }
		if (this.particleAge > this.particleMaxAge / 2)
        {
            this.particleAlpha = 1.0F - ((float)this.particleAge - (float)(this.particleMaxAge / 2F)) / (float)this.particleMaxAge;

            if (this.fadingColor)
            {
                this.particleRed += (this.fadeTargetRed - this.particleRed) * 0.2F;
                this.particleGreen += (this.fadeTargetGreen - this.particleGreen) * 0.2F;
                this.particleBlue += (this.fadeTargetBlue - this.particleBlue) * 0.2F;
            }
        }

        int particleTextureIndex = this.textureIdx + (this.numAgingFrames - 1 - this.particleAge * this.numAgingFrames / this.particleMaxAge);
        this.particleTextureIndexX = particleTextureIndex % 16;
        this.particleTextureIndexY = particleTextureIndex / 16;
        
        
        this.motionY += (double)this.yAccel;
        this.move(this.motionX, this.motionY);
        this.motionX *= (double)this.baseAirFriction;
        this.motionY *= (double)this.baseAirFriction;
	}
	
	public void move(double x, double y)
    {
        double origX = x;
        
        this.posX += x / 16D;
        this.posY = y;
        
        if (origX != x)
        {
            this.motionX = 0.0D;
        }
    }
	
	public void render(GuiScreen gui, float partialTicks) {
		GlStateManager.color(this.particleRed, this.particleGreen, this.particleBlue);
		int scale = 12;
        gui.drawModalRectWithCustomSizedTexture((int) (guiPosX / 2 - this.posX - scale / 2), (int) (guiPosY / 2 - this.posY + 75), (float)this.particleTextureIndexX * scale, (float)this.particleTextureIndexY * scale, scale, scale, 16 * scale, 16 * scale);
	}
	
	public boolean isDead() {
		return isDead;
	}
}
