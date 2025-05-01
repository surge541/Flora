package me.surge.flora.blocks

import me.surge.flora.misc.Cutout
import net.minecraft.block.*
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

class WisteriaFlowers(settings: Settings) : AbstractPlantStemBlock(settings, Direction.DOWN, SHAPE, false, 0.1), Cutout {

    companion object {
        val CODEC = createCodec { WisteriaFlowers(it) }
        val SHAPE = createColumnShape(8.0, 9.0, 16.0)
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return SHAPE
    }

    override fun getPlant() = FloraBlocks.WISTERIA_FLOWERS_PLANT

    override fun getCodec() = CODEC

    override fun getGrowthLength(random: Random) = random.nextBetween(1, 3)

    override fun canAttachTo(state: BlockState): Boolean {
        return state.block is LeavesBlock || state.block is WisteriaFlowersPlant || state.block is WisteriaFlowers
    }

    override fun chooseStemState(state: BlockState) = state.isAir

}