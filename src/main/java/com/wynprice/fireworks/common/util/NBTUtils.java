package com.wynprice.fireworks.common.util;

import net.minecraft.nbt.NBTTagCompound;

public class NBTUtils {
	public static NBTTagCompound getCompoundTag(NBTTagCompound tag, String key) {
		NBTTagCompound nbt = tag.getCompoundTag(key);
		tag.setTag(key, nbt);
		return nbt;
	}
 }
