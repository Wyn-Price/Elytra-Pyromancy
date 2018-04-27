package com.wynprice.fireworks.common.registries;

import java.awt.Color;

import com.wynprice.fireworks.FireworksMod;
import com.wynprice.fireworks.common.api.FireworkBit;
import com.wynprice.fireworks.common.util.ColorConfig;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@EventBusSubscriber(modid=FireworksMod.MODID)
@ObjectHolder(FireworksMod.MODID)
public class RegistryFireworkBit {
	public static final FireworkBit SPEED = FireworkBit.MISSING;
	public static final FireworkBit SLOW = FireworkBit.MISSING;
	public static final FireworkBit AUTOFLIGHT = FireworkBit.MISSING;
	public static final FireworkBit DURATION = FireworkBit.MISSING;
	public static final FireworkBit NO_PARTICLES = FireworkBit.MISSING;
	public static final FireworkBit PARTICLE_COLOR = FireworkBit.MISSING;
	public static final FireworkBit POTION = FireworkBit.MISSING;

	
	@SubscribeEvent
	public static void onRegistry(RegistryEvent.Register<FireworkBit> event) {
		event.getRegistry().registerAll(
				new FireworkBit(RegistryItem.SPEED_BIT, "speed").setRegistryName("speed"),
				new FireworkBit(RegistryItem.SLOW_BIT, "slow").setRegistryName("slow"),
				new FireworkBit(RegistryItem.AUTOFLIGHT_BIT, "autoflight").setRegistryName("autoflight"),
				new FireworkBit(RegistryItem.DURATION_BIT, "duration").setRegistryName("duration"),
				new FireworkBit(RegistryItem.NO_PARTICLES_BIT, "no_particles").setRegistryName("no_particles"),
				new FireworkBit(RegistryItem.PARTICLE_COLOR_BIT, "particle_color").setRegistryName("particle_color")
					.setTintColorFunction((stack, tintIndex) -> {
						ColorConfig config = ColorConfig.fromItemStack(stack);
						if(tintIndex == 0) {
							return config.getStart().getColor();
						} else if(tintIndex == 1) {
							Color color1 = new Color(config.getStart().getColor());	
							Color color2 = new Color(config.getEnd().getColor());							
							return new Color((color1.getRed() + color2.getRed()) / 2, (color1.getGreen() + color2.getGreen()) / 2, (color1.getBlue() + color2.getBlue()) / 2).getRGB();
						} else if(tintIndex == 2) {
							return config.getEnd().getColor();
						}
						return -1;
					}),
				new FireworkBit(RegistryItem.POTION_BIT, "potion").setRegistryName("potion").setTintColorFunction((stack, tint) -> PotionUtils.getColor(new ItemStack(stack.getOrCreateSubCompound("potion_bit_item"))))

		);
	}
}
