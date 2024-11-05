package com.sakalti.aoe.entities;

import com.sakalti.aoe.AOE;
import com.sakalti.aoe.items.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;

@Mod.EventBusSubscriber(modid = AOE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Shelran extends Mob {

    public static final DeferredRegister<EntityType<Shelran>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, AOE.MODID);
    public static final RegistryObject<EntityType<Shelran>> SHELRAN_TYPE = ENTITY_TYPES.register("shelran", 
        () -> EntityType.Builder.of(Shelran::new, MobType.WATER).sized(0.6F, 0.6F).build(new ResourceLocation(AOE.MODID, "shelran").toString()));

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
            .add(Attributes.MAX_HEALTH, 42.0D)
            .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        float damage;
        switch (this.level.getDifficulty()) {
            case EASY:
                damage = 3.0F;
                break;
            case NORMAL:
                damage = 5.0F;
                break;
            case HARD:
                damage = 6.0F;
                break;
            default:
                damage = 5.0F;
        }
        return target.hurt(DamageSource.mobAttack(this), damage);
    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        if (this.level.random.nextFloat() < 0.013) {
            ItemStack coralSword = new ItemStack(ItemInit.CORAL_SWORD.get());
            ItemEntity drop = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), coralSword);
            this.level.addFreshEntity(drop);
        }
    }

    public static class ShelranRenderer extends MobRenderer<Shelran, ShelranModel> {
        public ShelranRenderer(EntityRendererProvider.Context context) {
            super(context, new ShelranModel(), 0.5F);
        }

        @Override
        public ResourceLocation getTextureLocation(Shelran entity) {
            return new ResourceLocation(AOE.MODID, "textures/entity/shelran.png");
        }
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(Shelran::registerEntityRenderers);
    }

    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SHELRAN_TYPE.get(), ShelranRenderer::new);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SHELRAN_TYPE.get(), Shelran.createAttributes().build());
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
