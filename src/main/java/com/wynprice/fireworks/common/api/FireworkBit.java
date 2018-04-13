package com.wynprice.fireworks.common.api;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.ElytraPyromancy;

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
		
	public static final ResourceLocation RESOURCELOCATION = new ResourceLocation(ElytraPyromancy.MODID, "fireworkbits");
	public static final FireworkBit MISSING = new FireworkBit(stack -> false, "missing").setRegistryName(new ResourceLocation(ElytraPyromancy.MODID, "missing"));

	
	private final String name;
	private final Predicate<ItemStack> predicate;
	
	@SideOnly(Side.CLIENT)
	private Supplier<List<BakedQuad>> bakedQuadSupplier;
	
	public FireworkBit(Predicate<ItemStack> predicate, String name) {
		this.predicate = predicate;
		this.name = name;
	}
	
	public FireworkBit(Item item, String name) {
		this(stack -> stack.getItem() == item, name);
	}
	
	@SideOnly(Side.CLIENT)
	public void setBakedQuadSupplier(Supplier<List<BakedQuad>> bakedQuadSupplier) {
		this.bakedQuadSupplier = bakedQuadSupplier;
	}
	
	public Predicate<ItemStack> getPredicate() {
		return predicate;
	}
	
	@SideOnly(Side.CLIENT)
	public List<BakedQuad> getQuads() {
		return bakedQuadSupplier == null ? Lists.newArrayList() : bakedQuadSupplier.get();
	};
	
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
