package com.aoe.adventofsakalti;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;

public class AoeMod implements ModInitializer {
    public static final ItemGroup CORAL_GROUP = ItemGroup.build(new Identifier("aoe", "coral_group"));

    @Override
    public void onInitialize() {
        registerCoralArmor();
    }

    private void registerCoralArmor() {
        Registry.register(Registry.ITEM, new Identifier("aoe", "coral_helmet"), new CoralHelmet());
        Registry.register(Registry.ITEM, new Identifier("aoe", "coral_chestplate"), new CoralChestplate());
        Registry.register(Registry.ITEM, new Identifier("aoe", "coral_leggings"), new CoralLeggings());
        Registry.register(Registry.ITEM, new Identifier("aoe", "coral_boots"), new CoralBoots());
    }

    // コーラルヘルメット
    public static class CoralHelmet extends ArmorItem {
        public CoralHelmet() {
            super(new CoralArmorMaterial(), EquipmentSlot.HEAD, new Settings().group(CORAL_GROUP).maxDamage(647));
        }

        @Override
        public void onArmorTick(World world, PlayerEntity player) {
            if (!world.isClient) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 220, 0, true, false));
            }
        }
    }

    // コーラルチェストプレート
    public static class CoralChestplate extends ArmorItem {
        public CoralChestplate() {
            super(new CoralArmorMaterial(), EquipmentSlot.CHEST, new Settings().group(CORAL_GROUP).maxDamage(891));
        }
    }

    // コーラルレギンス
    public static class CoralLeggings extends ArmorItem {
        public CoralLeggings() {
            super(new CoralArmorMaterial(), EquipmentSlot.LEGS, new Settings().group(CORAL_GROUP).maxDamage(777));
        }
    }

    // コーラルブーツ
    public static class CoralBoots extends ArmorItem {
        public CoralBoots() {
            super(new CoralArmorMaterial(), EquipmentSlot.FEET, new Settings().group(CORAL_GROUP).maxDamage(632));
        }

        @Override
        public void onArmorTick(World world, PlayerEntity player) {
            if (!world.isClient) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 220, 0, true, false));
            }
        }
    }

    // コーラルアーマー素材の定義
    public static class CoralArmorMaterial implements ArmorMaterial {
        @Override
        public int getDurability(EquipmentSlot slot) {
            return switch (slot) {
                case HEAD -> 647;
                case CHEST -> 891;
                case LEGS -> 777;
                case FEET -> 632;
            };
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            return switch (slot) {
                case HEAD -> 4;    // コーラルヘルメットの防御力
                case CHEST -> 8;   // コーラルチェストプレートの防御力
                case LEGS -> 6;    // コーラルレギンスの防御力
                case FEET -> 4;    // コーラルブーツの防御力
            };
        }

        @Override
        public int getEnchantability() {
            return 15; // エンチャントの効果
        }

        @Override
        public String getName() {
            return "coral"; // アーマーの名前
        }

        @Override
        public float getToughness() {
            return 5; // タフネス値
        }

        @Override
        public float getKnockbackResistance() {
            return 0; // ノックバック抵抗
        }
    }
}
