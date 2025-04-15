package me.surge.flora.blocks

import me.surge.flora.Bootstrap
import net.minecraft.block.AbstractBlock.OffsetType
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.FlowerBlock
import net.minecraft.block.MapColor
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Items
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object FloraBlocks {

    lateinit var BLUEBELL: Block
    lateinit var MOONSHADE_LILY: Block
    lateinit var SNOWDROP: Block
    lateinit var FORGET_ME_NOT: Block
    lateinit var TIDEPETAL: Block

    val flowers = mutableListOf<Block>()

    fun init() {
        BLUEBELL = register("bluebell", { settings ->
            FlowerBlock(StatusEffects.POISON, 11.0F, settings)
        }, Settings.create()
            .mapColor(MapColor.DARK_GREEN)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .offset(OffsetType.XZ)
            .pistonBehavior(PistonBehavior.DESTROY)
        )

        MOONSHADE_LILY = register("moonshade_lily", { settings ->
            FlowerBlock(StatusEffects.NIGHT_VISION, 25.0F, settings)
        }, Settings.create()
            .mapColor(MapColor.LIGHT_BLUE)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .offset(OffsetType.XZ)
            .pistonBehavior(PistonBehavior.DESTROY)
            .emissiveLighting { _, _, _ -> true }
            .luminance { 4 }
        )

        SNOWDROP = register("snowdrop", { settings ->
            Snowdrop(settings)
        }, Settings.create()
            .mapColor(MapColor.WHITE)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .offset(OffsetType.XZ)
            .pistonBehavior(PistonBehavior.DESTROY)
            .emissiveLighting { _, _, _ -> true }
            .ticksRandomly()
        )

        FORGET_ME_NOT = register("forget_me_not", { settings ->
            FlowerBlock(StatusEffects.GLOWING, 30.0F, settings)
        }, Settings.create()
            .mapColor(MapColor.LIGHT_BLUE_GRAY)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .offset(OffsetType.XZ)
            .pistonBehavior(PistonBehavior.DESTROY)
        )

        TIDEPETAL = register("tidepetal", { settings ->
            Tidepetal(settings)
        }, Settings.create()
            .mapColor(MapColor.BLUE)
            .noCollision()
            .breakInstantly()
            .sounds(BlockSoundGroup.GRASS)
            .offset(OffsetType.XZ)
            .pistonBehavior(PistonBehavior.DESTROY)
        )
    }

    private fun register(id: String, factory: (Settings) -> Block, settings: Settings): Block {
        val identifier = Identifier.of(Bootstrap.MOD_ID, id)
        val key = RegistryKey.of(RegistryKeys.BLOCK, identifier)

        val block = Blocks.register(key, factory, settings)
        Items.register(block)

        if (block is FlowerBlock) {
            flowers.add(block)
        }

        return block
    }

}