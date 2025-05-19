package me.surge.flora.items

import me.surge.flora.Bootstrap
import me.surge.flora.blocks.FloraBlocks
import me.surge.flora.misc.id
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.block.AbstractBlock.Settings
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier


object FloraItems {

    lateinit var CHERRY_FLOWER_CROWN: Item

    val FLORA_GROUP_KEY: RegistryKey<ItemGroup?>? =
        RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(Bootstrap.MOD_ID, "flora_group"))

    lateinit var GROUP: ItemGroup

    fun init() {
        CHERRY_FLOWER_CROWN = register("cherry_flower_crown") {
            Item(it.armor(CherryArmourMaterial.INSTANCE, EquipmentType.HELMET))
        }

        GROUP = FabricItemGroup.builder()
            .icon { ItemStack(FloraBlocks.MOONSHADE_LILY) }
            .displayName(Text.translatable("itemGroup.flora"))
            .entries { context, entries ->
                FloraBlocks.itemGroupBlocks.forEach {
                    entries.add(it)
                }

                entries.add(CHERRY_FLOWER_CROWN)
            }
            .build()

        Registry.register(Registries.ITEM_GROUP, FLORA_GROUP_KEY, GROUP)
    }

    private fun register(id: String, factory: (Item.Settings) -> Item): Item {
        val key = RegistryKey.of(RegistryKeys.ITEM, id(id))
        return Items.register(key, factory)
    }

}