package me.surge.bloom.items

import me.surge.bloom.Bootstrap
import me.surge.bloom.blocks.BloomBlocks
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier


object BloomItems {

    val BLOOM_GROUP_KEY: RegistryKey<ItemGroup?>? =
        RegistryKey.of<ItemGroup?>(RegistryKeys.ITEM_GROUP, Identifier.of(Bootstrap.MOD_ID, "bloom_group"))

    lateinit var GROUP: ItemGroup

    fun init() {
        GROUP = FabricItemGroup.builder()
            .icon { ItemStack(BloomBlocks.BLUEBELL) }
            .displayName(Text.translatable("itemGroup.bloom"))
            .entries { context, entries ->
                entries.add(BloomBlocks.BLUEBELL)
                entries.add(BloomBlocks.MOONSHADE_LILY)
                entries.add(BloomBlocks.SNOWDROP)
                entries.add(BloomBlocks.FORGET_ME_NOT)
            }
            .build()

        Registry.register(Registries.ITEM_GROUP, BLOOM_GROUP_KEY, GROUP)
    }

}