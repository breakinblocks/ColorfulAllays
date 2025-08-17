package com.breakinblocks.colorfulallays;

import com.breakinblocks.colorfulallays.component.ColorableAllay;
import com.breakinblocks.colorfulallays.util.Utils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import java.util.Random;

@EventBusSubscriber(modid = Colorfulallays.MODID)
public class SpawnEventHandler {
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity().getType() == EntityType.ALLAY) {
            Allay allay = (Allay) event.getEntity();
            
            if (!event.getLevel().isClientSide()) {
                ColorableAllay colorableAllay = (ColorableAllay) allay;
                
                if (Config.enableRandomSpawning && !colorableAllay.colorfulallays$hasCustomColor()) {
                    Random random = new Random(allay.getRandom().nextLong());
                    DyeColor randomColor = Utils.getRandomWeightedColor(random);
                    colorableAllay.colorfulallays$setDyeColor(randomColor);
                }
            }
        }
    }
    
}