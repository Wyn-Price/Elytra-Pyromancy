package com.wynprice.fireworks.client.rendering;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.api.FireworkBit;
import com.wynprice.fireworks.common.data.FireworkDataHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.BakedModelWrapper;

public class FireworkModel extends BakedModelWrapper<IBakedModel> {

	private ItemStack itemstack;
	
	public FireworkModel(IBakedModel originalModel) {
		super(originalModel);
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if(side == null) {
			List<BakedQuad> baseQuads = new ArrayList<>(super.getQuads(state, side, rand));
			List<FireworkBit> bits = Lists.newArrayList();
			FireworkDataHelper.readDataFromStack(itemstack).getHandler().getBits().forEach(bit -> {
				if(!bits.contains(bit)) {
					bits.add(bit);
					baseQuads.addAll(bit.getQuads());
				}
			});
			return baseQuads;
		}
		return Lists.newArrayList();
	}
	
	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
		return Pair.of(this, super.handlePerspective(cameraTransformType).getRight());
	}
		
	@Override
	public ItemOverrideList getOverrides() {
		return new ItemOverrideList(originalModel.getOverrides().getOverrides()) {
			@Override
			public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world,
					EntityLivingBase entity) {
				itemstack = stack;
				return super.handleItemState(originalModel, stack, world, entity);
			}
		};
	}
	
	

}
