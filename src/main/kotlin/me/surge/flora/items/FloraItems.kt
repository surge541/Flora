package me.surge.flora.items

import me.surge.flora.Bootstrap
import me.surge.flora.blocks.FloraBlocks
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier


object FloraItems {

    val FLORA_GROUP_KEY: RegistryKey<ItemGroup?>? =
        RegistryKey.of<ItemGroup?>(RegistryKeys.ITEM_GROUP, Identifier.of(Bootstrap.MOD_ID, "flora_group"))

    lateinit var GROUP: ItemGroup

    fun init() {
        GROUP = FabricItemGroup.builder()
            .icon { ItemStack(FloraBlocks.BLUEBELL) }
            .displayName(Text.translatable("itemGroup.flora"))
            .entries { context, entries ->
                entries.add(FloraBlocks.BLUEBELL)
                entries.add(FloraBlocks.MOONSHADE_LILY)
                entries.add(FloraBlocks.SNOWDROP)
                entries.add(FloraBlocks.FORGET_ME_NOT)
            }
            .build()

        Registry.register(Registries.ITEM_GROUP, FLORA_GROUP_KEY, GROUP)
    }

}