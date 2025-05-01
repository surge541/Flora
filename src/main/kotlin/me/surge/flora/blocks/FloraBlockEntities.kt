package me.surge.flora.blocks

import me.surge.flora.Bootstrap
import me.surge.flora.blocks.Tidepetal.TidepetalEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object FloraBlockEntities {

    lateinit var TIDEPETAL: BlockEntityType<TidepetalEntity>

    fun init() {
        TIDEPETAL = register("tidepetal", FabricBlockEntityTypeBuilder.create(::TidepetalEntity, FloraBlocks.TIDEPETAL).build())
    }

    private fun <T : BlockEntityType<*>?> register(path: String, blockEntityType: T): T {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Bootstrap.MOD_ID, path), blockEntityType)
    }

}