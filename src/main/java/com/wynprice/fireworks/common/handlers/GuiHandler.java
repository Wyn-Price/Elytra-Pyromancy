package com.wynprice.fireworks.common.handlers;

import com.wynprice.fireworks.client.gui.GuiFireworkTable;
import com.wynprice.fireworks.common.container.ContainerFireworkTable;
import com.wynprice.fireworks.common.tileentities.TileEntityFireworkTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int FIREWORKTABLE = 0;
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == FIREWORKTABLE) {
			return new GuiFireworkTable(player, (TileEntityFireworkTable) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == FIREWORKTABLE) {
			return new ContainerFireworkTable(player, (TileEntityFireworkTable) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
}
