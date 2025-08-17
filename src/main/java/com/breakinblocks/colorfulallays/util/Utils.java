package com.breakinblocks.colorfulallays.util;

import com.breakinblocks.colorfulallays.Config;
import net.minecraft.world.item.DyeColor;

import java.util.Map;
import java.util.Random;

public class Utils {
    private static int cachedTotalWeight = 0;

    public static void updateCachedWeight() {
        cachedTotalWeight = Config.colorWeights.values().stream().mapToInt(Integer::intValue).sum();
    }

    public static DyeColor getRandomWeightedColor(Random random) {
        if (cachedTotalWeight <= 0) {
            return DyeColor.LIGHT_BLUE;
        }
        
        int randomValue = random.nextInt(cachedTotalWeight);
        
        int currentWeight = 0;
        for (Map.Entry<DyeColor, Integer> entry : Config.colorWeights.entrySet()) {
            currentWeight += entry.getValue();
            if (randomValue < currentWeight) {
                return entry.getKey();
            }
        }
        
        return DyeColor.LIGHT_BLUE;
    }
}