package com.breakinblocks.colorfulallays;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.minecraft.world.item.DyeColor;

import com.breakinblocks.colorfulallays.util.Utils;

import java.util.Map;
import java.util.HashMap;

/**
 * Configuration class for Colorful Allays mod.
 */
@EventBusSubscriber(modid = Colorfulallays.MODID)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // SPAWNING SECTION
    private static final ModConfigSpec.BooleanValue ENABLE_NATURAL_SPAWNING;
    private static final ModConfigSpec.ConfigValue<Integer> NATURAL_SPAWN_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> NATURAL_SPAWN_MIN_GROUP;
    private static final ModConfigSpec.ConfigValue<Integer> NATURAL_SPAWN_MAX_GROUP;
    private static final ModConfigSpec.BooleanValue ENABLE_INTERACTION_PERSISTENCE;
    
    // RANDOM COLOR SPAWNING
    private static final ModConfigSpec.BooleanValue ENABLE_RANDOM_SPAWNING;
    
    // COLOR WEIGHTS SECTION
    private static final ModConfigSpec.ConfigValue<Integer> WHITE_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> ORANGE_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> MAGENTA_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> LIGHT_BLUE_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> YELLOW_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> LIME_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> PINK_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> GRAY_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> LIGHT_GRAY_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> CYAN_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> PURPLE_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> BLUE_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> BROWN_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> GREEN_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> RED_WEIGHT;
    private static final ModConfigSpec.ConfigValue<Integer> BLACK_WEIGHT;

    private static ModConfigSpec.ConfigValue<Integer> defineWeight(String name, int defaultValue) {
        return BUILDER.define(name, defaultValue, 
            value -> value instanceof Integer && (Integer) value >= 0 && (Integer) value <= 1000);
    }

    static {
        BUILDER.comment("Natural spawning configuration for Allays")
               .push("spawning");
        
        ENABLE_NATURAL_SPAWNING = BUILDER
            .comment("Enable natural spawning of Allays in the world")
            .define("enableNaturalSpawning", true);
            
        NATURAL_SPAWN_WEIGHT = BUILDER
            .comment("Weight for natural Allay spawning (higher = more common, 0 = disabled)")
            .define("naturalSpawnWeight", 5, 
                value -> value instanceof Integer && (Integer) value >= 0 && (Integer) value <= 100);
                
        NATURAL_SPAWN_MIN_GROUP = BUILDER
            .comment("Minimum group size for natural Allay spawning")
            .define("naturalSpawnMinGroup", 1,
                value -> value instanceof Integer && (Integer) value >= 1 && (Integer) value <= 10);
                
        NATURAL_SPAWN_MAX_GROUP = BUILDER
            .comment("Maximum group size for natural Allay spawning")
            .define("naturalSpawnMaxGroup", 2,
                value -> value instanceof Integer && (Integer) value >= 1 && (Integer) value <= 10);
                
        ENABLE_INTERACTION_PERSISTENCE = BUILDER
            .comment("Make naturally spawned Allays persistent when a player interacts with them")
            .define("enableInteractionPersistence", true);
        
        BUILDER.pop();
        
        BUILDER.comment("Color randomization settings")
               .push("colors");
        
        ENABLE_RANDOM_SPAWNING = BUILDER
            .comment("Enable random color assignment when Allays spawn naturally")
            .define("enableRandomColorSpawning", true);
        
        BUILDER.comment("Color weights: higher values = more common spawns (0 = disabled, max 1000)")
               .push("weights");
        
        WHITE_WEIGHT = defineWeight("white", 10);
        ORANGE_WEIGHT = defineWeight("orange", 8);
        MAGENTA_WEIGHT = defineWeight("magenta", 6);
        LIGHT_BLUE_WEIGHT = defineWeight("lightBlue", 15);
        YELLOW_WEIGHT = defineWeight("yellow", 8);
        LIME_WEIGHT = defineWeight("lime", 7);
        PINK_WEIGHT = defineWeight("pink", 8);
        GRAY_WEIGHT = defineWeight("gray", 5);
        LIGHT_GRAY_WEIGHT = defineWeight("lightGray", 7);
        CYAN_WEIGHT = defineWeight("cyan", 6);
        PURPLE_WEIGHT = defineWeight("purple", 5);
        BLUE_WEIGHT = defineWeight("blue", 8);
        BROWN_WEIGHT = defineWeight("brown", 4);
        GREEN_WEIGHT = defineWeight("green", 6);
        RED_WEIGHT = defineWeight("red", 7);
        BLACK_WEIGHT = defineWeight("black", 3);
        
        BUILDER.pop(); // Close weights section
        BUILDER.pop(); // Close colors section
    }
    
    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableRandomSpawning;
    public static boolean enableNaturalSpawning;
    public static int naturalSpawnWeight;
    public static int naturalSpawnMinGroup;
    public static int naturalSpawnMaxGroup;
    public static boolean enableInteractionPersistence;
    public static Map<DyeColor, Integer> colorWeights = new HashMap<>();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        // Load spawning configuration
        enableRandomSpawning = ENABLE_RANDOM_SPAWNING.get();
        enableNaturalSpawning = ENABLE_NATURAL_SPAWNING.get();
        naturalSpawnWeight = NATURAL_SPAWN_WEIGHT.get();
        naturalSpawnMinGroup = NATURAL_SPAWN_MIN_GROUP.get();
        naturalSpawnMaxGroup = NATURAL_SPAWN_MAX_GROUP.get();
        enableInteractionPersistence = ENABLE_INTERACTION_PERSISTENCE.get();
        
        // Load color weights
        colorWeights.put(DyeColor.WHITE, WHITE_WEIGHT.get());
        colorWeights.put(DyeColor.ORANGE, ORANGE_WEIGHT.get());
        colorWeights.put(DyeColor.MAGENTA, MAGENTA_WEIGHT.get());
        colorWeights.put(DyeColor.LIGHT_BLUE, LIGHT_BLUE_WEIGHT.get());
        colorWeights.put(DyeColor.YELLOW, YELLOW_WEIGHT.get());
        colorWeights.put(DyeColor.LIME, LIME_WEIGHT.get());
        colorWeights.put(DyeColor.PINK, PINK_WEIGHT.get());
        colorWeights.put(DyeColor.GRAY, GRAY_WEIGHT.get());
        colorWeights.put(DyeColor.LIGHT_GRAY, LIGHT_GRAY_WEIGHT.get());
        colorWeights.put(DyeColor.CYAN, CYAN_WEIGHT.get());
        colorWeights.put(DyeColor.PURPLE, PURPLE_WEIGHT.get());
        colorWeights.put(DyeColor.BLUE, BLUE_WEIGHT.get());
        colorWeights.put(DyeColor.BROWN, BROWN_WEIGHT.get());
        colorWeights.put(DyeColor.GREEN, GREEN_WEIGHT.get());
        colorWeights.put(DyeColor.RED, RED_WEIGHT.get());
        colorWeights.put(DyeColor.BLACK, BLACK_WEIGHT.get());
        
        Utils.updateCachedWeight();
    }
}