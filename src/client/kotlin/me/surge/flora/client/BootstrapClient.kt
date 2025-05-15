package me.surge.flora.client

import me.surge.flora.blocks.FloraBlocks
import me.surge.flora.misc.Cutout
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.FlowerBlock
import net.minecraft.block.TallFlowerBlock
import net.minecraft.client.render.RenderLayer

class BootstrapClient : ClientModInitializer {

    override fun onInitializeClient() {
        for (block in FloraBlocks.blocks) {
            if (block is FlowerBlock  || block is TallFlowerBlock || block is Cutout) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout())
            }
        }
    }

}
