package com.wynprice.fireworks.common.util;

import java.awt.Point;

import lombok.Data;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
@Data
public class ColorConfig {
	
	private final SingleConfig start;
	private final SingleConfig end;
	
	@Data
	public static class SingleConfig {
		
		public SingleConfig(NBTTagCompound nbt) {
			this.nbtTagCompound = nbt;
			color = nbt.hasKey("color", 99) ? nbt.getInteger("color") : 0xFFFFFFFF;
			point = new Point(nbt.getInteger("pointX"), nbt.getInteger("pointY"));
			lightnessBar = nbt.hasKey("lightness_bar", 99) ? nbt.getFloat("lightness_bar") : 1f;
		}
		
		private final NBTTagCompound nbtTagCompound;
		private int color;
		private Point point;
		private float lightnessBar;
		
		public void write() {
			nbtTagCompound.setInteger("color", color);
			nbtTagCompound.setInteger("pointX", point.x);
			nbtTagCompound.setInteger("pointY", point.y);
			nbtTagCompound.setFloat("lightness_bar", lightnessBar);
		}
		
	}

	public static ColorConfig fromItemStack(ItemStack key) {
		NBTTagCompound nbt = key.getOrCreateSubCompound("color_configs");
		return new ColorConfig(new SingleConfig(NBTUtils.getCompoundTag(nbt, "start")), new SingleConfig(NBTUtils.getCompoundTag(nbt, "end")));
	}

	public void write() {
		this.start.write();
		this.end.write();
	}
}
