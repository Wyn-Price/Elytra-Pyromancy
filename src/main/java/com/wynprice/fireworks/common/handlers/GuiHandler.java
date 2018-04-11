package com.wynprice.fireworks.common.handlers;

import com.wynprice.fireworks.client.gui.GuiChangeFireworkSpeed;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int FIREWORKGUI = 0;
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == FIREWORKGUI) {
			EnumHand hand = EnumHand.values()[x];
			return new GuiChangeFireworkSpeed(player.getHeldItem(hand), hand);
		}
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
