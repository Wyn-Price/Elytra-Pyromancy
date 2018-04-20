package com.wynprice.fireworks;

import org.apache.logging.log4j.Logger;

import com.wynprice.fireworks.common.handlers.GuiHandler;
import com.wynprice.fireworks.common.network.FireworksNetwork;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = FireworksMod.MODID, name = FireworksMod.NAME, version = FireworksMod.VERSION)
public class FireworksMod
{
    public static final String MODID = "fireworks";
    public static final String NAME = "Fireworks";
    public static final String VERSION = "1.0";
    
    public static final CreativeTabs TAB = new CreativeTabs(MODID) {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.FIREWORKS);
		}
    	
    };
    
    @Instance(MODID)
    private static FireworksMod instance;
    private static Logger logger;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        FireworksNetwork.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	NetworkRegistry.INSTANCE.registerGuiHandler(FireworksMod.instance, new GuiHandler());
    }
    
    public static Logger getLogger() {
		return logger;
	}
    
    public static FireworksMod getInstance() {
		return instance;
	}
}
