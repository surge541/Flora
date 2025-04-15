package me.surge.flora.blocks

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.FlowerBlock
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random

class Snowdrop(settings: Settings) : FlowerBlock(StatusEffects.INVISIBILITY, 9.0F, settings) {

    override fun randomTick(state: BlockState?, world: ServerWorld?, pos: BlockPos?, random: Random?) {
        val blocks = arrayListOf<BlockPos>()

        for (x in -3..3) {
            for (z in -3..3) {
                if (x == 0 && z == 0) {
                    continue
                }

                blocks.add(pos!!.add(x, -1, z))
            }
        }

        while (blocks.isNotEmpty()) {
            val pos = blocks[random!!.nextInt(blocks.size)]
            val block = world!!.getBlockState(pos).block

            if (block == Blocks.WATER) {
                world.setBlockState(pos, Blocks.ICE.defaultState)
                break
            }

            blocks.remove(pos)
        }
    }

}