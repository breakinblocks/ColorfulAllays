package com.breakinblocks.colorfulallays.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.HashMap;
import java.util.Map;

import static com.breakinblocks.colorfulallays.Colorfulallays.rl;

public class AllayTextures {
    
    private static final Map<DyeColor, ResourceLocation> COLOR_TEXTURES = new HashMap<>();
    
    static {
        COLOR_TEXTURES.put(DyeColor.WHITE, rl("textures/entity/allay/allay_white.png"));
        COLOR_TEXTURES.put(DyeColor.ORANGE, rl("textures/entity/allay/allay_orange.png"));
        COLOR_TEXTURES.put(DyeColor.MAGENTA, rl("textures/entity/allay/allay_magenta.png"));
        COLOR_TEXTURES.put(DyeColor.LIGHT_BLUE, rl("textures/entity/allay/allay_light_blue.png"));
        COLOR_TEXTURES.put(DyeColor.YELLOW, rl("textures/entity/allay/allay_yellow.png"));
        COLOR_TEXTURES.put(DyeColor.LIME, rl("textures/entity/allay/allay_lime.png"));
        COLOR_TEXTURES.put(DyeColor.PINK, rl("textures/entity/allay/allay_pink.png"));
        COLOR_TEXTURES.put(DyeColor.GRAY, rl("textures/entity/allay/allay_gray.png"));
        COLOR_TEXTURES.put(DyeColor.LIGHT_GRAY, rl("textures/entity/allay/allay_light_gray.png"));
        COLOR_TEXTURES.put(DyeColor.CYAN, rl("textures/entity/allay/allay_cyan.png"));
        COLOR_TEXTURES.put(DyeColor.PURPLE, rl("textures/entity/allay/allay_purple.png"));
        COLOR_TEXTURES.put(DyeColor.BLUE, rl("textures/entity/allay/allay_blue.png"));
        COLOR_TEXTURES.put(DyeColor.BROWN, rl("textures/entity/allay/allay_brown.png"));
        COLOR_TEXTURES.put(DyeColor.GREEN, rl("textures/entity/allay/allay_green.png"));
        COLOR_TEXTURES.put(DyeColor.RED, rl("textures/entity/allay/allay_red.png"));
        COLOR_TEXTURES.put(DyeColor.BLACK, rl("textures/entity/allay/allay_black.png"));
    }
    
    public static ResourceLocation getTextureForColor(DyeColor dyeColor) {
        return COLOR_TEXTURES.get(dyeColor);
    }
}