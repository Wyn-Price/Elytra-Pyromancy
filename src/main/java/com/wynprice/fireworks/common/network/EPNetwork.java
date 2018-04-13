package com.wynprice.fireworks.common.network;

import com.wynprice.fireworks.ElytraPyromancy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class EPNetwork {
	
	private static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ElytraPyromancy.MODID);

	public static void preInit() {
	}
	
	private static int idCount = -1;
    public static void registerMessage(Class claz, Side recievingSide) {
    	INSTANCE.registerMessage(claz, claz, idCount++, recievingSide);
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