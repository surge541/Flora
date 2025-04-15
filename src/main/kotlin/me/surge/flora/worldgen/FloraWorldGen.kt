package me.surge.flora.worldgen

import me.surge.flora.Bootstrap
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.PlacedFeature


object FloraWorldGen {

    val FLORA_FLOWER_FOREST: RegistryKey<PlacedFeature?>? =
        RegistryKey.of<PlacedFeature?>(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "flora_flower_forest"))

    val FLORA_FLOWER_MEADOW: RegistryKey<PlacedFeature?>? =
        RegistryKey.of<PlacedFeature?>(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "flora_flower_meadow"))

    val DARK_OAK_BLUEBELL_PATCH: RegistryKey<PlacedFeature?>? =
        RegistryKey.of<PlacedFeature?>(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "dark_oak_bluebell_patch"))

    val SNOWY_TAIGA_MOONSHADE_LILY_PATCH: RegistryKey<PlacedFeature?>? =
        RegistryKey.of<PlacedFeature?>(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "snowy_taiga_moonshade_lily_patch"))

    val SNOWDROPS_NEAR_WATER: RegistryKey<PlacedFeature?>? =
        RegistryKey.of<PlacedFeature?>(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "snowdrops_near_water"))

    val FOREST_FMN_PATCH: RegistryKey<PlacedFeature?>? =
        RegistryKey.of<PlacedFeature?>(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "forest_fmn_patch"))

    fun init() {
        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
            GenerationStep.Feature.VEGETAL_DECORATION,
            FLORA_FLOWER_FOREST
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.MEADOW),
            GenerationStep.Feature.VEGETAL_DECORATION,
            FLORA_FLOWER_MEADOW
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
            GenerationStep.Feature.VEGETAL_DECORATION,
            DARK_OAK_BLUEBELL_PATCH
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.SNOWY_TAIGA),
            GenerationStep.Feature.VEGETAL_DECORATION,
            SNOWY_TAIGA_MOONSHADE_LILY_PATCH
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(
                BiomeKeys.FROZEN_RIVER,
                BiomeKeys.FROZEN_OCEAN,
                BiomeKeys.FROZEN_PEAKS,
                BiomeKeys.DEEP_FROZEN_OCEAN,
                BiomeKeys.SNOWY_BEACH,
                BiomeKeys.SNOWY_TAIGA,
                BiomeKeys.SNOWY_PLAINS,
                BiomeKeys.SNOWY_SLOPES
            ),
            GenerationStep.Feature.VEGETAL_DECORATION,
            SNOWDROPS_NEAR_WATER
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.FOREST),
            GenerationStep.Feature.VEGETAL_DECORATION,
            FOREST_FMN_PATCH
        )
    }

}