package me.surge.flora.datagen

import me.surge.flora.blocks.FloraBlocks
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.FlowerBlock
import net.minecraft.block.TallFlowerBlock
import net.minecraft.block.TallPlantBlock
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class BootstrapDataGenerator : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::BlockLootTableProvider)
    }

    private class BlockLootTableProvider(dataOutput: FabricDataOutput, registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>) : FabricBlockLootTableProvider(dataOutput, registryLookup) {

        override fun generate() {
            for (block in FloraBlocks.flowers) {
                if (block is FlowerBlock) {
                    addDrop(block)
                } else if (block is TallFlowerBlock) {
                    addDrop(block, LootTable.builder()
                        .pool(addSurvivesExplosionCondition(
                            FloraBlocks.BUDDLEIA,
                            LootPool.builder()
                                .rolls(ConstantLootNumberProvider(1f))
                                .with(ItemEntry.builder(block).conditionally(
                                    BlockStatePropertyLootCondition.builder(block)
                                    .properties(StatePredicate.Builder.create()
                                        .exactMatch(TallPlantBlock.HALF, "lower"))
                                ))
                        ))
                    )
                }
            }
        }

    }

}