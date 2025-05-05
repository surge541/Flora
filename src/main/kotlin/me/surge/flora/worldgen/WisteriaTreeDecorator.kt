package me.surge.flora.worldgen

import com.mojang.serialization.MapCodec
import me.surge.flora.blocks.FloraBlocks
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.Blocks
import net.minecraft.util.Util
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.gen.treedecorator.TreeDecorator
import net.minecraft.world.gen.treedecorator.TreeDecoratorType

object WisteriaTreeDecorator: TreeDecorator() {

    const val PROBABILITY = 0.6f

    val CODEC = MapCodec.unit { WisteriaTreeDecorator }

    override fun generate(generator: Generator) {
        val set: MutableSet<BlockPos> = HashSet()
        val random = generator.random

        for (leaf in generator.leavesPositions) {
            if (!generator.isAir(leaf.down()) || !generator.isAir(leaf.down().down())) {
                continue
            }

            generator.replace(leaf.down(), FloraBlocks.WISTERIA_FLOWERS_PLANT.defaultState)
            generator.replace(leaf.down().down(), FloraBlocks.WISTERIA_FLOWERS_PLANT.defaultState)
        }

        /*for (leaf in Util.copyShuffled(generator.leavesPositions, random)) {
            if (!generator.isAir(leaf.offset(Direction.DOWN)) || set.contains(leaf) || random.nextFloat() > PROBABILITY) {
                continue
            }

            for (i in 1..4) {
                val offset = leaf.offset(Direction.DOWN, i)

                val state = if (generator.isAir(offset.down())) {
                    FloraBlocks.WISTERIA_FLOWERS_PLANT.defaultState
                } else {
                    FloraBlocks.WISTERIA_FLOWERS.defaultState
                }

                if (!generator.isAir(offset)) {
                    break
                }

                generator.replace(offset, state)
                println(offset in set)
                set.add(offset)
            }

            set.add(leaf)
        }*/
    }

    override fun getType(): TreeDecoratorType<*>? {
        return FloraWorldGen.WISTERIA_TREE_DECORATOR
    }

}