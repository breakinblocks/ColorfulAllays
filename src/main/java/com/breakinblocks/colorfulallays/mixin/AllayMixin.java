package com.breakinblocks.colorfulallays.mixin;

import com.breakinblocks.colorfulallays.Colorfulallays;
import com.breakinblocks.colorfulallays.Config;
import com.breakinblocks.colorfulallays.component.ColorableAllay;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Allay.class)
public class AllayMixin implements ColorableAllay {
    
    @Unique
    private static final DyeColor colorfulallays$DEFAULT_DYE_COLOR = DyeColor.LIGHT_BLUE;
    
    @Unique
    private static final EntityDataAccessor<Integer> colorfulallays$DYE_COLOR_ID = SynchedEntityData.defineId(Allay.class, EntityDataSerializers.INT);
    
    @Shadow
    private void resetDuplicationCooldown() {}
    
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void colorfulallays$defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(colorfulallays$DYE_COLOR_ID, colorfulallays$DEFAULT_DYE_COLOR.getId());
    }
    
    @Unique
    public DyeColor colorfulallays$getDyeColor() {
        int dyeId = ((Allay)(Object)this).getEntityData().get(colorfulallays$DYE_COLOR_ID);
        return DyeColor.byId(dyeId);
    }
    
    @Unique
    public void colorfulallays$setDyeColor(DyeColor dyeColor) {
        ((Allay)(Object)this).getEntityData().set(colorfulallays$DYE_COLOR_ID, dyeColor.getId());
    }
    
    @Unique
    @Override
    public boolean colorfulallays$hasCustomColor() {
        return colorfulallays$getDyeColor() != colorfulallays$DEFAULT_DYE_COLOR;
    }
    
    @Inject(method = "mobInteract", at = @At("HEAD"))
    private void colorfulallays$handleAnyInteraction(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Allay allay = (Allay) (Object) this;
        
        if (!allay.level().isClientSide && Config.enableInteractionPersistence && !allay.isPersistenceRequired()) {
            allay.setPersistenceRequired();
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void colorfulallays$handleDyeInteraction(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Allay allay = (Allay) (Object) this;
        ItemStack itemStack = player.getItemInHand(hand);
        
        if (player.isShiftKeyDown() && itemStack.getItem() instanceof DyeItem dyeItem) {
            if (!allay.level().isClientSide) {
                ((ColorableAllay)allay).colorfulallays$setDyeColor(dyeItem.getDyeColor());
                
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                
                if (allay.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 10; i++) {
                        double offsetX = allay.getRandom().nextGaussian() * 0.5;
                        double offsetY = allay.getRandom().nextGaussian() * 0.5;
                        double offsetZ = allay.getRandom().nextGaussian() * 0.5;
                        
                        serverLevel.sendParticles(ParticleTypes.HEART,
                                allay.getX() + offsetX,
                                allay.getY() + 0.5 + offsetY,
                                allay.getZ() + offsetZ,
                                1, 0, 0, 0, 0);
                    }
                }
                
                allay.playSound(SoundEvents.DYE_USE, 1.0F, 1.0F);
            }
            
            cir.setReturnValue(InteractionResult.SUCCESS);
            return;
        }
        
        if (player.isShiftKeyDown() && itemStack.is(Items.WATER_BUCKET)) {
            if (!allay.level().isClientSide && ((ColorableAllay)allay).colorfulallays$hasCustomColor()) {
                ((ColorableAllay)allay).colorfulallays$setDyeColor(colorfulallays$DEFAULT_DYE_COLOR);
                
                if (allay.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < 15; i++) {
                        double offsetX = allay.getRandom().nextGaussian() * 0.5;
                        double offsetY = allay.getRandom().nextGaussian() * 0.5;
                        double offsetZ = allay.getRandom().nextGaussian() * 0.5;
                        
                        serverLevel.sendParticles(ParticleTypes.SPLASH,
                                allay.getX() + offsetX,
                                allay.getY() + 0.5 + offsetY,
                                allay.getZ() + offsetZ,
                                1, 0, 0, 0, 0);
                    }
                }
                
                allay.playSound(SoundEvents.GENERIC_SPLASH, 1.0F, 1.0F);
            }
            
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
    
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void colorfulallays$saveColorData(CompoundTag compound, CallbackInfo ci) {
        DyeColor dyeColor = colorfulallays$getDyeColor();
        if (dyeColor != colorfulallays$DEFAULT_DYE_COLOR) {
            compound.putInt("colorfulallays:dyeColor", dyeColor.getId());
        }
    }
    
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void colorfulallays$loadColorData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("colorfulallays:dyeColor")) {
            int dyeId = compound.getInt("colorfulallays:dyeColor");
            colorfulallays$setDyeColor(DyeColor.byId(dyeId));
        }
    }
    
    @Inject(method = "duplicateAllay", at = @At("HEAD"), cancellable = true)
    private void colorfulallays$overrideDuplicateAllay(CallbackInfo ci) {
        Allay thisAllay = (Allay) (Object) this;
        
        Allay duplicatedAllay = (Allay) net.minecraft.world.entity.EntityType.ALLAY.create(thisAllay.level());
        if (duplicatedAllay != null) {
            duplicatedAllay.moveTo(thisAllay.position());
            duplicatedAllay.setPersistenceRequired();
            
            ((AllayMixin)(Object)duplicatedAllay).resetDuplicationCooldown();
            this.resetDuplicationCooldown();
            
            DyeColor parentColor = colorfulallays$getDyeColor();
            ((ColorableAllay) duplicatedAllay).colorfulallays$setDyeColor(parentColor);
            
            thisAllay.level().addFreshEntity(duplicatedAllay);
        }
        ci.cancel();
    }
    
}