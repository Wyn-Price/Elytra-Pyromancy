package com.wynprice.fireworks.common.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FireworkData {
	private float speed;
	
	public FireworkData(float speed) {
		this.speed = speed;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void writeToStack(ItemStack stack) {
		NBTTagCompound nbt = stack.getOrCreateSubCompound("fireworkdata");
		nbt.setFloat("speed", speed);
	}
	
	public void writeToData(ByteBuf buf) {
		buf.writeFloat(speed);
	}
	
	public static FireworkData fromStack(ItemStack stack) {
		NBTTagCompound nbt = stack.getOrCreateSubCompound("fireworkdata");
		return new FireworkData(nbt.hasKey("speed", 99) ? nbt.getFloat("speed") : 1f);
	}
	
	public static FireworkData fromBuf(ByteBuf buf) {
		return new FireworkData(buf.readFloat());
	}

}
