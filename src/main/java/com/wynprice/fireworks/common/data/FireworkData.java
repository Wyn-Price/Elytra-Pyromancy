package com.wynprice.fireworks.common.data;

import lombok.Data;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.INBTSerializable;

@Data
public class FireworkData implements INBTSerializable<NBTTagCompound> {
	
	private int modifiers;
	private int timesBroken;
	private int totalUse;
	private double distance;	
	private int level;
	private double totalDistance;
	
	FireworkItemStackHandler handler = new FireworkItemStackHandler();
	
	public void moveDistance(EntityPlayer player, double distance) {
		this.distance += distance;
		if(this.distance >= FireworkDataHelper.getDistanceNeededForLevelUp(level)) {
			levelUp(player);
		}
	}
	
	public void levelUp(EntityPlayer player) {
		if(this.level >= 15) {
			return;
		}
		this.level += 1;
		player.sendStatusMessage(new TextComponentTranslation("messages.firework.levelup", this.level), false);
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
		level = MathHelper.clamp(nbt.getInteger("level"), 1, 15);
		totalDistance = nbt.getDouble("total_distance");
		handler.deserializeNBT(nbt.getCompoundTag("modules"));
		if(handler.getSlots() != modifiers) {
			handler.forceSize(modifiers);
		}
	}
	
}
