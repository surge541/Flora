package me.surge.flora.blocks

import me.surge.flora.Bootstrap
import net.minecraft.block.AbstractBlock.OffsetType
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FlowerBlock
import net.minecraft.block.MapColor
import net.minecraft.block.TallFlowerBlock
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Items
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object FloraBlocks {

    lateinit var BLUEBELL: Block
    lateinit var MOONSHADE_LILY: Block
    lateinit var SNOWDROP: Block
    lateinit var FORGET_ME_NOT: Block
    lateinit var TIDEPETAL: Block
    lateinit var FOXBLOOM: Block
    lateinit var BUDDLEIA: Block

    val flowers = mutableListOf<Block>()

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
    }

    private fun flower(effect: RegistryEntry<StatusEffect>, length: Float): (Settings) -> FlowerBlock {
        return { FlowerBlock(effect, length, it) }
    }

    private fun register(id: String, factory: (Settings) -> Block, settings: Settings): Block {
        val identifier = Identifier.of(Bootstrap.MOD_ID, id)
        val key = RegistryKey.of(RegistryKeys.BLOCK, identifier)

        val block = Blocks.register(key, factory, settings)
        Items.register(block)

        if (block is FlowerBlock || block is TallFlowerBlock) {
            flowers.add(block)
        }

        return block
    }

}