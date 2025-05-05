package me.surge.flora.blocks

import me.surge.flora.misc.Cutout
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.BlockState
import net.minecraft.block.LeavesBlock
import net.minecraft.block.ShapeContext
import net.minecraft.registry.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.WorldView

class WisteriaFlowers(settings: Settings) : AbstractPlantStemBlock(settings, Direction.DOWN, SHAPE, false, 0.1), Cutout {

    companion object {
        val CODEC = createCodec { WisteriaFlowers(it) }
        val SHAPE = createColumnShape(8.0, 9.0, 16.0)
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.offset(this.growthDirection.opposite)
        val blockState = world.getBlockState(blockPos)

        return if (!this.canAttachTo(blockState)) {
            false
        } else {
            blockState.isOf(this.stem) ||
            blockState.isOf(this.plant) ||
            blockState.isSideSolidFullSquare(world, blockPos, growthDirection) ||
            blockState.block is LeavesBlock
        }
    }

    override fun getOutlineShape(state: BlockState, world: BlockView, pos: BlockPos, context: ShapeContext): VoxelShape {
        return SHAPE
    }

    override fun getPlant() = FloraBlocks.WISTERIA_FLOWERS_PLANT

    override fun getCodec() = CODEC

    override fun getGrowthLength(random: Random) = random.nextBetween(1, 3)

    override fun chooseStemState(state: BlockState) = state.isAir

}