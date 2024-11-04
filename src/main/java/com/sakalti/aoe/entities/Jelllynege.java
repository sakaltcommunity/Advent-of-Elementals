package com.sakalti.aoe.entities;

import com.sakalti.aoe.AOE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;

@Mod.EventBusSubscriber(modid = AOE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Jellynege extends Mob {

    public static final DeferredRegister<EntityType<Jellynege>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, AOE.MODID);
    public static final RegistryObject<EntityType<Jellynege>> JELLYNEGE_TYPE = ENTITY_TYPES.register("jellynege",
        () -> EntityType.Builder.of(Jellynege::new, MobType.WATER).sized(0.8F, 0.8F).build(new ResourceLocation(AOE.MODID, "jellynege").toString()));

    public Jellynege(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 0.3D, 10));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    // エンティティの属性を設定
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 51.0D)
            .add(Attributes.ATTACK_DAMAGE, 5.0D) // デフォルトはノーマルのダメージ
            .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    // ダメージの処理（難易度によるダメージ設定）
    @Override
    public boolean doHurtTarget(Entity target) {
        float damage;
        switch (this.level.getDifficulty()) {
            case EASY:
                damage = 3.5F;
                break;
            case NORMAL:
                damage = 5.0F;
                break;
            case HARD:
                damage = 7.5F;
                break;
            default:
                damage = 5.0F; // デフォルトでNORMALのダメージ
        }
        return target.hurt(DamageSource.mobAttack(this), damage);
    }

    // クライアント側のモデルとレンダラー設定
    public static class JellynegeRenderer extends MobRenderer<Jellynege, JellynegeModel> {
        public JellynegeRenderer(EntityRendererProvider.Context renderManager) {
            super(renderManager, new JellynegeModel(), 0.5F);
        }

        @Override
        public ResourceLocation getTextureLocation(Jellynege entity) {
            return new ResourceLocation(AOE.MODID, "textures/entity/jellynege.png");
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(Jellynege::registerEntityRenderers);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(JELLYNEGE_TYPE.get(), JellynegeRenderer::new);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(JELLYNEGE_TYPE.get(), Jellynege.createAttributes().build());
    }

    // レジストリに登録
    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
