package com.wynprice.fireworks.common.network.packets;

import com.wynprice.fireworks.common.data.FireworkData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class MessagePacketSpeedGuiClosed extends MessagePacket<MessagePacketSpeedGuiClosed> {

	private EnumHand hand;
	private FireworkData data;
	
	public MessagePacketSpeedGuiClosed() {
	}
	
	public MessagePacketSpeedGuiClosed(FireworkData data, EnumHand hand) {
		this.data = data;
		this.hand = hand;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(hand.ordinal());
		data.writeToData(buf);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		hand = EnumHand.values()[buf.readInt()];
		data = FireworkData.fromBuf(buf);
	}
	
	@Override
	public void onReceived(MessagePacketSpeedGuiClosed message, EntityPlayer player) {
		message.data.writeToStack(player.getHeldItem(message.hand));
	}

}
