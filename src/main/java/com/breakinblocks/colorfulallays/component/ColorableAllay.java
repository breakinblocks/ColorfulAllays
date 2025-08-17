package com.breakinblocks.colorfulallays.component;

import net.minecraft.world.item.DyeColor;

public interface ColorableAllay {
    boolean colorfulallays$hasCustomColor();
    DyeColor colorfulallays$getDyeColor();
    void colorfulallays$setDyeColor(DyeColor dyeColor);
}