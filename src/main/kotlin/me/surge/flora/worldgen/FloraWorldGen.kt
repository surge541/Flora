package me.surge.flora.worldgen

import me.surge.flora.Bootstrap
import me.surge.flora.misc.id
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.SaplingGenerator
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.biome.BiomeKeys
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.treedecorator.TreeDecoratorType
import java.util.*


object FloraWorldGen {

    private val FLORA_FLOWER_FOREST = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "flora_flower_forest"))
    private val FLORA_FLOWER_MEADOW = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "flora_flower_meadow"))
    private val DARK_OAK_BLUEBELL_PATCH = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "dark_oak_bluebell_patch"))
    private val SNOWY_TAIGA_MOONSHADE_LILY_PATCH = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "snowy_taiga_moonshade_lily_patch"))
    private val SNOWDROPS_NEAR_WATER = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "snowdrops_near_water"))
    private val FOREST_FMN_PATCH = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "forest_fmn_patch"))
    private val TIDEPETAL = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "tidepetal"))
    private val FOXBLOOM = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "foxbloom"))
    private val BUDDLEIA = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "buddleia"))

    private val WISTERIA = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Bootstrap.MOD_ID, "wisteria"))
    private val WISTERIA_CF = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, id("wisteria"))

    val WISTERIA_SAPLING_GENERATOR = SaplingGenerator("wisteria", Optional.empty(), Optional.of(WISTERIA_CF), Optional.empty())
    val WISTERIA_TREE_DECORATOR = Registry.register(Registries.TREE_DECORATOR_TYPE, id("wisteria"), TreeDecoratorType(WisteriaTreeDecorator.CODEC))

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

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(
                BiomeKeys.LUKEWARM_OCEAN,
                BiomeKeys.WARM_OCEAN
            ),
            GenerationStep.Feature.VEGETAL_DECORATION,
            TIDEPETAL
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(
                BiomeKeys.TAIGA,
                BiomeKeys.OLD_GROWTH_PINE_TAIGA,
                BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA
            ),
            GenerationStep.Feature.VEGETAL_DECORATION,
            FOXBLOOM
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.LUSH_CAVES),
            GenerationStep.Feature.VEGETAL_DECORATION,
            BUDDLEIA
        )

        BiomeModifications.addFeature(
            BiomeSelectors.includeByKey(BiomeKeys.RIVER),
            GenerationStep.Feature.VEGETAL_DECORATION,
            WISTERIA
        )
    }

}