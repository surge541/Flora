package me.surge.flora.blocks

import me.surge.flora.misc.Cutout
import net.minecraft.block.AbstractPlantBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.block.BlockState
import net.minecraft.block.LeavesBlock
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.WorldView

class WisteriaFlowersPlant(settings: Settings) : AbstractPlantBlock(settings, Direction.DOWN, SHAPE, false), Cutout {

    companion object {
        val CODEC = createCodec {  WisteriaFlowersPlant(it) }
        private val SHAPE: VoxelShape = createColumnShape(14.0, 0.0, 16.0)
    }

    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val blockPos = pos.offset(this.growthDirection.opposite)
        val blockState = world.getBlockState(blockPos)

        return blockState.isOf(this.stem) ||
                    blockState.isOf(this.plant) ||
                    blockState.isSideSolidFullSquare(world, blockPos, growthDirection) ||
                    blockState.block is LeavesBlock

    }

    public override fun getCodec() = CODEC

    override fun getStem(): AbstractPlantStemBlock {
        return FloraBlocks.WISTERIA_FLOWERS as AbstractPlantStemBlock
    }

}