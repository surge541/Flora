package me.surge.flora.client

import me.surge.flora.blocks.FloraBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

class BootstrapClient : ClientModInitializer {

    override fun onInitializeClient() {
        for (block in FloraBlocks.flowers) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout())
        }
    }

}
