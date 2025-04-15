package me.surge.bloom.client

import me.surge.bloom.blocks.BloomBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

class BootstrapClient : ClientModInitializer {

    override fun onInitializeClient() {
        for (block in BloomBlocks.flowers) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout())
        }
    }

}
