package com.breakinblocks.colorfulallays.datagen;

import com.breakinblocks.colorfulallays.Colorfulallays;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends BiomeTagsProvider {
    
    public static final TagKey<Biome> ALLAY_SPAWN_BIOME = TagKey.create(
        Registries.BIOME,
        ResourceLocation.fromNamespaceAndPath(Colorfulallays.MODID, "allay_spawn_biome")
    );
    
    public ModBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Colorfulallays.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ALLAY_SPAWN_BIOME).addTag(BiomeTags.IS_OVERWORLD);
    }
}