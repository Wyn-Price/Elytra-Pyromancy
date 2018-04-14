package com.wynprice.fireworks.common.blocks;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.handlers.GuiHandler;
import com.wynprice.fireworks.common.tileentities.TileEntityFireworkTable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFireworkTable extends Block {

	public BlockFireworkTable() {
		super(Material.IRON);
		setRegistryName("firework_table");
		setUnlocalizedName("firework_table");
		setHardness(0.5f);
		setResistance(0.5f);
		TileEntity.register(ElytraPyromancy.MODID + "_fireworktable", TileEntityFireworkTable.class);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(ElytraPyromancy.getInstance(), GuiHandler.FIREWORKTABLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFireworkTable();
	}

}
