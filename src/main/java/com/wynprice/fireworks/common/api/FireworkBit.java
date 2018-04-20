package com.wynprice.fireworks.common.api;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.FireworksMod;

import akka.util.Collections;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FireworkBit extends IForgeRegistryEntry.Impl<FireworkBit> {
		
	public static final ResourceLocation RESOURCELOCATION = new ResourceLocation(FireworksMod.MODID, "fireworkbits");
	public static final FireworkBit MISSING = new FireworkBit(stack -> false, "missing").setRegistryName(new ResourceLocation(FireworksMod.MODID, "missing"));

	protected final String name;
	protected final Predicate<ItemStack> predicate;
	
	@SideOnly(Side.CLIENT)
	protected List<BakedQuad> bakedQuads = Lists.newArrayList();
	@SideOnly(Side.CLIENT)
	protected BiFunction<ItemStack, Integer, Integer> tintColorFunction = (itemstack, tint) -> -1;
	
	public FireworkBit(Predicate<ItemStack> predicate, String name) {
		this.predicate = predicate;
		this.name = name;
	}
	
	public FireworkBit(Item item, String name) {
		this(stack -> stack.getItem() == item, name);
	}
	
	@SideOnly(Side.CLIENT)
	public FireworkBit setBakedQuads(List<BakedQuad> bakedQuads) {
		this.bakedQuads = bakedQuads;
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	public FireworkBit setTintColorFunction(BiFunction<ItemStack, Integer, Integer> tintColorFunction) {
		this.tintColorFunction = tintColorFunction;
		return this;
	}
	
	public Predicate<ItemStack> getPredicate() {
		return predicate;
	}
	
	@SideOnly(Side.CLIENT)
	public List<BakedQuad> getQuads() {
		return bakedQuads;
	};
	
	@SideOnly(Side.CLIENT)
	public int getTintColor(ItemStack stack, int tintIndex) {
		return tintColorFunction.apply(stack, tintIndex);
	}
	
	public String getName() {
		return name;
	}
		
	public String getDescriptionKey() {
		return "ep." + name + ".desc";
	}
	
	public String getDescription() {
		return I18n.format(getDescriptionKey());
	}
}
