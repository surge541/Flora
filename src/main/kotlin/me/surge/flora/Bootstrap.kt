package me.surge.bloom

import me.surge.bloom.blocks.BloomBlocks
import me.surge.bloom.items.BloomItems
import me.surge.bloom.worldgen.BloomPlacedFeatures
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

class Bootstrap : ModInitializer {

    val logger = LogManager.getLogger(MOD_ID)

    override fun onInitialize() {
        BloomBlocks.init()
        BloomPlacedFeatures.init()
        BloomItems.init()

        logger.info("Bloom initialisation complete :)")
    }

    companion object {
        const val MOD_ID = "bloom"
    }

}
