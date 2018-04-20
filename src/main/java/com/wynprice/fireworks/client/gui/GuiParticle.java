package com.wynprice.fireworks.client.gui;

import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GuiParticle {
	
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
    
    public GuiParticle() { //TODO make values dynamic
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
        
        this.motionY = 35 + this.rand.nextFloat() * 8f;
        this.motionX = (this.rand.nextFloat() - 0.5f) * 32f;
        
        onUpdate();
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
        gui.drawModalRectWithCustomSizedTexture((int) (100 - this.posX), (int) (100 - this.posY), (float)this.particleTextureIndexX * 8, (float)this.particleTextureIndexY * 8, 8, 8, 128, 128);
	}
	
	public boolean isDead() {
		return isDead;
	}
}
