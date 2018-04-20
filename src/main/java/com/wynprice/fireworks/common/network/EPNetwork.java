package com.wynprice.fireworks.common.network;

import com.wynprice.fireworks.ElytraPyromancy;
import com.wynprice.fireworks.common.network.packets.MessagePacketUpdateColorBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class EPNetwork {
	
	private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ElytraPyromancy.MODID);

    private static int id = 1;
	
	public static void preInit() {
		registerMessage(MessagePacketUpdateColorBit.class, Side.SERVER);
	}
    
    private static void registerMessage(Class<? extends MessageBase> message, Side recievingSide) {
        INSTANCE.registerMessage((m, ctx) -> {
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(() -> m.process(ctx, ctx.side == Side.SERVER ? ctx.getServerHandler().player : FMLClientHandler.instance().getClientPlayerEntity()));
            return null;
        }, message, id++, recievingSide);
    }
    
	public static void sendToServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}
	
	public static void sendToPlayer(EntityPlayer player, IMessage message) {
		if(!player.world.isRemote)
			INSTANCE.sendTo(message, (EntityPlayerMP) player);
	}
	
	public static void sendToPlayersInWorld(World world, IMessage message) {
		if(world == null)
			sendToAll(message);
		else if(!world.isRemote)
			INSTANCE.sendToDimension(message, world.provider.getDimension());
	}
	
	public static void sendToAll(IMessage message) {
		INSTANCE.sendToAll(message);
	}	
}