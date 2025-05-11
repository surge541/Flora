package me.surge.flora.worldgen

import com.mojang.serialization.MapCodec
import me.surge.flora.blocks.FloraBlocks
import net.minecraft.world.gen.treedecorator.TreeDecorator
import net.minecraft.world.gen.treedecorator.TreeDecoratorType

object WisteriaTreeDecorator: TreeDecorator() {

    const val PROBABILITY = 0.6f

    val CODEC = MapCodec.unit { WisteriaTreeDecorator }

    override fun generate(generator: Generator) {
        val random = generator.random

        for (leaf in generator.leavesPositions) {
            if (random.nextFloat() > PROBABILITY) {
                continue
            }

            val length = random.nextBetween(1, 4)
            var currentPos = leaf.down()

            for (i in 1..length) {
                if (generator.isAir(currentPos)) {
                    if (i == length) {
                        generator.replace(currentPos, FloraBlocks.WISTERIA_FLOWERS.defaultState)
                    } else {
                        generator.replace(currentPos, FloraBlocks.WISTERIA_FLOWERS_PLANT.defaultState)
                    }

                    currentPos = currentPos.down()
                } else {
                    break
                }
            }
        }
    }

    override fun getType(): TreeDecoratorType<*>? {
        return FloraWorldGen.WISTERIA_TREE_DECORATOR
    }

}