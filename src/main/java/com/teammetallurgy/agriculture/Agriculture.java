package com.teammetallurgy.agriculture;

import com.teammetallurgy.agriculture.client.proxy.ClientProxy;
import com.teammetallurgy.agriculture.common.proxy.IProxy;
import com.teammetallurgy.agriculture.common.proxy.ServerProxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("agriculture")
public class Agriculture {
    private static final Logger LOGGER = LogManager.getLogger("agriculture");

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Agriculture() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        LOGGER.info("Running CommonSetup");
        proxy.init();
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }
}
