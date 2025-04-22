package me.surge.flora

import me.surge.flora.blocks.FloraBlocks
import me.surge.flora.items.FloraItems
import me.surge.flora.worldgen.FloraWorldGen
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Bootstrap : ModInitializer {

    private val logger: Logger = LogManager.getLogger(MOD_ID)

    override fun onInitialize() {
        FloraBlocks.init()
        FloraWorldGen.init()
        FloraItems.init()

        logger.info("Flora initialisation complete :)")
    }

    companion object {
        const val MOD_ID = "flora"
    }

}
