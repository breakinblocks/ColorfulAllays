package com.breakinblocks.colorfulallays.spawn;

import com.breakinblocks.colorfulallays.Config;
import com.breakinblocks.colorfulallays.Colorfulallays;
import com.breakinblocks.colorfulallays.datagen.ModBiomeTagProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

@EventBusSubscriber(modid = Colorfulallays.MODID)
public class AllaySpawnHandler {
    
    
    @SubscribeEvent
    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(EntityType.ALLAY, SpawnPlacementTypes.ON_GROUND, 
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AllaySpawnHandler::checkAllaySpawnRules, 
            RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if (Config.enableNaturalSpawning) {
                NeoForge.EVENT_BUS.register(AllaySpawnEvents.class);
                Colorfulallays.LOGGER.info("Registered natural spawning for Allays");
            } else {
                Colorfulallays.LOGGER.info("Natural spawning disabled");
            }
        });
    }

    public static boolean checkAllaySpawnRules(EntityType<Allay> entityType, LevelAccessor level, 
                                              MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (spawnType != MobSpawnType.NATURAL && spawnType != MobSpawnType.CHUNK_GENERATION) {
            return level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir();
        }
        
        if (!Config.enableNaturalSpawning || Config.naturalSpawnWeight <= 0) {
            return false;
        }
        
        if (!level.canSeeSky(pos)) {
            return false;
        }
        
        if (level.getRawBrightness(pos, 0) < 7) {
            return false;
        }
        
        if (!level.getBlockState(pos.below()).isSolid()) {
            return false;
        }
        
        if (!level.getBlockState(pos).isAir() || !level.getBlockState(pos.above()).isAir()) {
            return false;
        }
        
        var biome = level.getBiome(pos);
        if (!biome.is(ModBiomeTagProvider.ALLAY_SPAWN_BIOME)) {
            return false;
        }
        
        if (pos.getY() < 50 || pos.getY() > 120) {
            return false;
        }

        if (Config.naturalSpawnWeight < 10 && random.nextInt(10) >= Config.naturalSpawnWeight) {
            return false;
        }

        return true;
    }
}