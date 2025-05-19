package me.surge.flora.items

import me.surge.flora.misc.id
import net.minecraft.item.Item
import net.minecraft.item.equipment.ArmorMaterial
import net.minecraft.item.equipment.EquipmentAsset
import net.minecraft.item.equipment.EquipmentType
import net.minecraft.item.equipment.EquipmentAssetKeys
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.sound.SoundEvents


object CherryArmourMaterial {

    val REPAIRS_CHERRY_FLOWER_CROWN_TAG: TagKey<Item> = TagKey.of<Item>(RegistryKeys.ITEM, id("repairs_cherry_flower_crown"))
    val CHERRY_CROWN_MATERIAL_KEY: RegistryKey<EquipmentAsset> = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, id("cherry_flower_crown"))

    val INSTANCE = ArmorMaterial(
        25,
        mapOf(
            EquipmentType.HELMET to 1,
            EquipmentType.CHESTPLATE to 0,
            EquipmentType.LEGGINGS to 0,
            EquipmentType.BOOTS to 0
        ),
        5,
        RegistryEntry.Direct(SoundEvents.BLOCK_CHERRY_LEAVES_STEP),
        0f,
        0f,
        REPAIRS_CHERRY_FLOWER_CROWN_TAG,
        CHERRY_CROWN_MATERIAL_KEY
    )

}