package com.originem.approlight;

import com.originem.approlight.init.ModRecipes;
import com.originem.approlight.network.PacketHandler;
import com.originem.approlight.proxy.CommonProxy;
import com.originem.approlight.util.Reference;
import com.originem.approlight.util.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

    @Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy PROXY;

    public static Logger LOGGER;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        //prepare the packet
        PacketHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
//        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        ModRecipes.init();
        RegistryHandler.initRegisters();
    }

    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {

    }
}
