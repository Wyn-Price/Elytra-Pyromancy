package com.wynprice.fireworks.common.handlers;

import com.wynprice.fireworks.client.gui.GuiColorSelector;
import com.wynprice.fireworks.client.gui.GuiFireworkTable;
import com.wynprice.fireworks.client.gui.GuiPotionSelector;
import com.wynprice.fireworks.common.container.ContainerFireworkTable;
import com.wynprice.fireworks.common.container.ContainerPotionSelector;
import com.wynprice.fireworks.common.tileentities.TileEntityFireworkTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int FIREWORKTABLE = 0;
	public static final int COLORSELECTOR = 1;
	public static final int POTIONSELECTOR = 2;

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == FIREWORKTABLE) {
			return new GuiFireworkTable(player, (TileEntityFireworkTable) world.getTileEntity(new BlockPos(x, y, z)));
		} else if(ID == COLORSELECTOR) {
			return new GuiColorSelector(EnumHand.values()[x], player.getHeldItem(EnumHand.values()[x]));
		} else if(ID == POTIONSELECTOR) {
			return new GuiPotionSelector(player, EnumHand.values()[x], player.getHeldItem(EnumHand.values()[x]));
		}
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == FIREWORKTABLE) {
			return new ContainerFireworkTable(player, (TileEntityFireworkTable) world.getTileEntity(new BlockPos(x, y, z)));
		} else if(ID == POTIONSELECTOR) {
			return new ContainerPotionSelector(player, player.getHeldItem(EnumHand.values()[x]));
		}
		return null;
	}
}
