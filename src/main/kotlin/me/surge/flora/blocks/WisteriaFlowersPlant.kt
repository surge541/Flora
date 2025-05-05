package me.surge.flora.blocks

import me.surge.flora.misc.Cutout
import net.minecraft.block.AbstractPlantBlock
import net.minecraft.block.AbstractPlantStemBlock
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape

class WisteriaFlowersPlant(settings: Settings) : AbstractPlantBlock(settings, Direction.DOWN, SHAPE, false), Cutout {

    companion object {
        val CODEC = createCodec {  WisteriaFlowersPlant(it) }
        private val SHAPE: VoxelShape = createColumnShape(14.0, 0.0, 16.0)
    }

    public override fun getCodec() = CODEC

    override fun getStem(): AbstractPlantStemBlock {
        return FloraBlocks.WISTERIA_FLOWERS as AbstractPlantStemBlock
    }

}