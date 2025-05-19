package me.surge.flora.datagen

import me.surge.flora.Bootstrap
import me.surge.flora.blocks.FloraBlocks
import me.surge.flora.items.FloraItems
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.block.TallFlowerBlock
import net.minecraft.block.TallPlantBlock
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer


class BootstrapDataGenerator : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::BlockLootTableProvider)
        pack.addProvider(::AdvancementProvider)
    }

    private class BlockLootTableProvider(dataOutput: FabricDataOutput, registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>) : FabricBlockLootTableProvider(dataOutput, registryLookup) {

        override fun generate() {
            for (block in FloraBlocks.blocks) {
                if (block is TallFlowerBlock) {
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
                } else {
                    addDrop(block)
                }
            }
        }

    }

     class AdvancementProvider(output: FabricDataOutput, registryLookup: CompletableFuture<WrapperLookup>) : FabricAdvancementProvider(output, registryLookup) {

        override fun generateAdvancement(wrapperLookup: WrapperLookup, consumer: Consumer<AdvancementEntry>) {
            val root = Advancement.Builder.create()
                .display(
                    FloraBlocks.MOONSHADE_LILY,
                    Text.literal("Flora"),
                    Text.literal("Oooh, pretty flowers!"),
                    Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"),
                    AdvancementFrame.TASK,
                    true,
                    true,
                    false
                ).build(consumer, Bootstrap.MOD_ID + ":root")


        }

    }


}