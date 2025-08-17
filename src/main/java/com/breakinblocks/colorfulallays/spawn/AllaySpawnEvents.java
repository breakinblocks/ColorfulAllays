package com.breakinblocks.colorfulallays.spawn;

import com.breakinblocks.colorfulallays.Config;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

public class AllaySpawnEvents {
    
    @SubscribeEvent
    public static void onMobSpawnCheck(MobSpawnEvent.SpawnPlacementCheck event) {
        if (event.getEntityType() != EntityType.ALLAY || !Config.enableNaturalSpawning) {
            return;
        }
        
        boolean canSpawn = AllaySpawnHandler.checkAllaySpawnRules(
            (EntityType<Allay>) event.getEntityType(),
            event.getLevel(),
            event.getSpawnType(),
            event.getPos(),
            event.getLevel().getRandom()
        );
        
        if (!canSpawn) {
            event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
        }
    }
}