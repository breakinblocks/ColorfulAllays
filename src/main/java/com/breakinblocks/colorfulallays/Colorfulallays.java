package com.breakinblocks.colorfulallays;

import com.breakinblocks.colorfulallays.spawn.AllaySpawnHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Colorfulallays.MODID)
public class Colorfulallays {
    public static final String MODID = "colorfulallays";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Colorfulallays(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
//        LOGGER.info("Colorful Allays mod initialized");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
//        LOGGER.info("Colorful Allays mod common setup complete");
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
//            LOGGER.info("Colorful Allays mod client setup complete");
        }
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
