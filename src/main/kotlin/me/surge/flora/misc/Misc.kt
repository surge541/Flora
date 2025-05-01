package me.surge.flora.misc

import me.surge.flora.Bootstrap
import net.minecraft.util.Identifier

fun id(path: String): Identifier = Identifier.of(Bootstrap.MOD_ID, path)
