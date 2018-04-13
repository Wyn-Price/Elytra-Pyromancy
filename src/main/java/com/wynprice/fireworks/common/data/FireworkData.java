package com.wynprice.fireworks.common.data;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import io.netty.util.internal.IntegerHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

public class FireworkData implements INBTSerializable<NBTTagCompound> {

	private int modifiers;
	private int timesBroken;
	private int totalUse;
	private double distance;	
	private int level;
	private double totalDistance;
	
	FireworkItemStackHandler handler = new FireworkItemStackHandler();
	
	public FireworkData setModifiers(int modifiers) {
		this.modifiers = modifiers;
		return this;
	}
	
	public FireworkData setTimesBroken(int timesBroken) {
		this.timesBroken = timesBroken;
		return this;
	}
	
	public FireworkData setTotalUse(int totalUse) {
		this.totalUse = totalUse;
		return this;
	}
	
	public int getModifiers() {
		return modifiers;
	}
	
	public int getTimesBroken() {
		return timesBroken;
	}
	
	public int getTotalUse() {
		return totalUse;
	}
	
	public int getLevel() {
		return level;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public double getTotalDistance() {
		return totalDistance;
	}
	
	public FireworkItemStackHandler getHandler() {
		return handler;
	}
	
	public void moveDistance(EntityPlayer player, double distance) {
		this.distance += distance;
		if(this.distance >= FireworkDataHelper.getXpNeededForLevel(distance)) {
			levelUp(player);
		}
	}
	
	public void levelUp(EntityPlayer player) {
		if(this.level >= 15) {
			return;
		}
		this.level += 1;
		player.sendStatusMessage(new TextComponentTranslation("messages.firework.levelup", this.level), true);
		this.distance = 0;
		modifiers++;
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("modifiers", modifiers);
		nbt.setInteger("times_broken", timesBroken);
		nbt.setInteger("total_used", totalUse);
		nbt.setDouble("distance", distance);
		nbt.setInteger("level", level);
		nbt.setDouble("total_distance", totalDistance);
		nbt.setTag("modules", handler.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		modifiers = MathHelper.clamp(nbt.getInteger("modifiers"), 1, 17);
		timesBroken = nbt.getInteger("times_broken");
		totalUse = nbt.getInteger("total_used");
		distance = nbt.getDouble("distance");
		level = Math.min(nbt.getInteger("level"), 15);
		totalDistance = nbt.getDouble("total_distance");
		handler.deserializeNBT(nbt.getCompoundTag("modules"));
		if(handler.getSlots() != modifiers) {
			handler.forceSize(modifiers);
		}
	}
	
}
