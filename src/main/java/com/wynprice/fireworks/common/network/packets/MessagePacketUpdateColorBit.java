package com.wynprice.fireworks.common.network.packets;

import java.io.IOException;

import com.wynprice.fireworks.common.network.MessageBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePacketUpdateColorBit extends MessageBase {

	private NBTTagCompound tag; 
	private EnumHand hand;
	
	public MessagePacketUpdateColorBit(NBTTagCompound tag, EnumHand hand) {
		this.tag = tag;
		this.hand = hand;
	}
	
	public MessagePacketUpdateColorBit(){}
	
	@Override
	public void serialize(PacketBuffer buffer) {
		ByteBufUtils.writeTag(buffer, tag);
		buffer.writeInt(hand.ordinal());
	}

	@Override
	public void deserialize(PacketBuffer buffer) throws IOException {
		this.tag = ByteBufUtils.readTag(buffer);
		this.hand = EnumHand.values()[buffer.readInt()];
	}

	@Override
	public IMessage process(MessageContext context, EntityPlayer player) {
		ItemStack stack = player.getHeldItem(hand);
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setTag("color_configs", tag);
		return null;
	}

}
