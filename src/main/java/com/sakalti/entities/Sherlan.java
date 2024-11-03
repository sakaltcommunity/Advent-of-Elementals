package com.sakalti.aoe.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.difficulty.Difficulty;

public class Shelran extends Mob {
    public static final DeferredRegister<EntityType<Shelran>> SHELRAN = DeferredRegister.create(ForgeRegistries.ENTITIES, AOE.MODID);
    public static final RegistryObject<EntityType<Shelran>> SHELRAN_TYPE = SHELRAN.register("shelran", 
        () -> EntityType.Builder.of(Shelran::new, MobType.WATER).sized(0.6F, 0.6F).build(AOE.MODID + ":shelran")
    );

    public Shelran(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 42.0D) // HPを42に設定
            .add(Attributes.ATTACK_DAMAGE, 5.0D); // デフォルトの攻撃力を設定
    }

    @Override
    public float getAttackDamage() {
        // 難易度に応じたダメージを設定
        switch (this.level.getDifficulty()) {
            case EASY:
                return 2.0F; // イージーのダメージ
            case NORMAL:
                return 4.0F; // ノーマルのダメージ
            case HARD:
                return 6.0F; // ハードのダメージ
            default:
                return super.getAttackDamage(); // デフォルトの攻撃力
        }
    }
}
