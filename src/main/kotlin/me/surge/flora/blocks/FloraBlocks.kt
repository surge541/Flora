package me.surge.flora.blocks

import me.surge.flora.misc.Cutout
import me.surge.flora.misc.id
import me.surge.flora.worldgen.FloraWorldGen
import net.minecraft.block.*
import net.minecraft.block.AbstractBlock.OffsetType
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Items
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.sound.BlockSoundGroup

object FloraBlocks {

    lateinit var BLUEBELL: Block
    lateinit var MOONSHADE_LILY: Block
    lateinit var SNOWDROP: Block
    lateinit var FORGET_ME_NOT: Block
    lateinit var TIDEPETAL: Block
    lateinit var FOXBLOOM: Block
    lateinit var BUDDLEIA: Block

    lateinit var WISTERIA_LEAVES: Block
    lateinit var WISTERIA_FLOWERS: Block
    lateinit var WISTERIA_FLOWERS_PLANT: Block
    lateinit var WISTERIA_SAPLING: Block

    val blocks = mutableListOf<Block>()
    val itemGroupBlocks = mutableListOf<Block>()

    private val defaultSettings: Settings
        get() = Settings.create()
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .offset(OffsetType.XZ)
            .pistonBehavior(PistonBehavior.DESTROY)

    fun init() {
        BLUEBELL = register(
            "bluebell",
            flower(StatusEffects.POISON, 11.0F),
            defaultSettings.mapColor(MapColor.PURPLE)
        )

        MOONSHADE_LILY = register(
            "moonshade_lily",
            flower(StatusEffects.NIGHT_VISION, 25.0F),
            defaultSettings.mapColor(MapColor.LIGHT_BLUE)
                .emissiveLighting { _, _, _ -> true }
                .luminance { 4 }
        )

        SNOWDROP = register(
            "snowdrop",
            ::Snowdrop,
            defaultSettings.mapColor(MapColor.WHITE)
                .emissiveLighting { _, _, _ -> true }
                .ticksRandomly()
        )

        FORGET_ME_NOT = register(
            "forget_me_not",
            flower(StatusEffects.GLOWING, 30.0F),
            defaultSettings.mapColor(MapColor.LIGHT_BLUE_GRAY)
        )

        TIDEPETAL = register(
            "tidepetal",
            ::Tidepetal,
            defaultSettings.mapColor(MapColor.BLUE)
        )

        FOXBLOOM = register(
            "foxbloom",
            flower(StatusEffects.SATURATION, 5.0F),
            defaultSettings.mapColor(MapColor.ORANGE)
        )

        BUDDLEIA = register(
            "buddleia",
            ::TallFlowerBlock,
            defaultSettings.mapColor(MapColor.PALE_PURPLE)
        )

        WISTERIA_FLOWERS = register(
            "wisteria_flowers",
            { WisteriaFlowers(it) },
            defaultSettings
                .mapColor(MapColor.PALE_PURPLE)
                .replaceable()
                .sounds(BlockSoundGroup.HANGING_ROOTS)
                .ticksRandomly()
        )

        WISTERIA_FLOWERS_PLANT = register(
            "wisteria_flowers_plant",
            { WisteriaFlowersPlant(it) },
            defaultSettings
                .mapColor(MapColor.PALE_PURPLE)
                .replaceable()
                .sounds(BlockSoundGroup.HANGING_ROOTS),
            true
        )

        WISTERIA_LEAVES = register(
            "wisteria_leaves",
            { TintedParticleLeavesBlock(0f, it) },
            Blocks.createLeavesSettings(BlockSoundGroup.GRASS)
        )

        WISTERIA_SAPLING = register(
            "wisteria_sapling",
            { object : SaplingBlock(FloraWorldGen.WISTERIA_SAPLING_GENERATOR, it), Cutout {} },
            defaultSettings
                .mapColor(MapColor.DARK_GREEN)
                .ticksRandomly()
        )
    }

    private fun flower(effect: RegistryEntry<StatusEffect>, length: Float): (Settings) -> FlowerBlock {
        return { FlowerBlock(effect, length, it) }
    }

    private fun register(id: String, factory: (Settings) -> Block, settings: Settings, ignore_add: Boolean = false): Block {
        val key = RegistryKey.of(RegistryKeys.BLOCK, id(id))

        val block = Blocks.register(key, factory, settings)
        Items.register(block)

        blocks.add(block)

        if (!ignore_add) {
            itemGroupBlocks.add(block)
        }

        return block
    }

}