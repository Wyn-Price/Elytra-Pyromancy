package com.wynprice.fireworks;

import org.apache.logging.log4j.Logger;

import com.wynprice.fireworks.common.api.ElytraRegistery;
import com.wynprice.fireworks.common.api.FireworkBit;
import com.wynprice.fireworks.common.handlers.GuiHandler;
import com.wynprice.fireworks.common.network.EPNetwork;

import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(modid = ElytraPyromancy.MODID, name = ElytraPyromancy.NAME, version = ElytraPyromancy.VERSION)
public class ElytraPyromancy
{
    public static final String MODID = "elytrapyromancy";
    public static final String NAME = "Elytra Pyromancy";
    public static final String VERSION = "1.0";
    
    @Instance(MODID)
    private static ElytraPyromancy instance;
    private static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        EPNetwork.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(ElytraPyromancy.instance, new GuiHandler());
    }
    
    public static Logger getLogger() {
		return logger;
	}
    
    public static ElytraPyromancy getInstance() {
		return instance;
	}
}
