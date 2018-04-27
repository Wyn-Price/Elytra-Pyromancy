package com.wynprice.fireworks.client.rendering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Comparators;
import com.google.common.collect.Lists;
import com.wynprice.fireworks.common.api.FireworkBit;
import com.wynprice.fireworks.common.data.FireworkDataHelper;

import io.netty.util.internal.IntegerHolder;
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
		originalModel.getQuads(null, null, 0L).forEach(quad -> {
			int[] aint = quad.getVertexData();
			EnumFacing dir = quad.getFace();
			for(int i = 0; i < 4; i++) {
				int pos = (aint.length / 4) * i;
				aint[pos + 0] = Float.floatToRawIntBits(Float.intBitsToFloat(aint[pos + 0]) - dir.getFrontOffsetX() * 0.00001f);
				aint[pos + 1] = Float.floatToRawIntBits(Float.intBitsToFloat(aint[pos + 1]) - dir.getFrontOffsetY() * 0.00001f);
				aint[pos + 2] = Float.floatToRawIntBits(Float.intBitsToFloat(aint[pos + 2]) - dir.getFrontOffsetZ() * 0.00001f);
			}
		});
	}
	
	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if(side == null) {
			List<BakedQuad> baseQuads = new ArrayList<>(super.getQuads(state, side, rand));
			List<FireworkBit> bits = Lists.newArrayList();
			IntegerHolder stackIndex = new IntegerHolder();
			FireworkDataHelper.readDataFromStack(itemstack).getHandler().getMappedBits().forEach(pair -> {
				stackIndex.value++;	
				IntegerHolder bitIndex = new IntegerHolder();
				pair.getRight().stream()
					.sorted((o1, o2) -> Comparator.<String>naturalOrder().compare(o1.getRegistryName().toString(), o2.getRegistryName().toString()))
					.forEach(bit -> {
						bitIndex.value++;
						if(!bits.contains(bit)) {
							bits.add(bit);
							bit.getQuads().forEach(quad -> baseQuads.add(new BakedQuad(quad.getVertexData(), (bitIndex.value * 100000) + (stackIndex.value * 1000) + quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), quad.getFormat())));
						}
					});
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
