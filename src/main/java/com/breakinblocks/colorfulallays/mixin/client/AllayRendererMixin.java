package com.breakinblocks.colorfulallays.mixin.client;

import com.breakinblocks.colorfulallays.client.AllayTextures;
import com.breakinblocks.colorfulallays.component.ColorableAllay;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayRenderer.class)
public class AllayRendererMixin {

    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/allay/Allay;)Lnet/minecraft/resources/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    private void colorfulallays$getCustomTexture(Allay allay, CallbackInfoReturnable<ResourceLocation> cir) {
        boolean hasCustomColor = ((ColorableAllay)allay).colorfulallays$hasCustomColor();
        
        if (hasCustomColor) {
            DyeColor dyeColor = ((ColorableAllay)allay).colorfulallays$getDyeColor();
            
            ResourceLocation textureLocation = AllayTextures.getTextureForColor(dyeColor);
            if (textureLocation != null) {
                cir.setReturnValue(textureLocation);
            }
        }
    }
    
}