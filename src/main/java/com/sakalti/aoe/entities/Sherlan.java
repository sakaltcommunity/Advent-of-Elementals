package com.sakalti.aoe.entities;

import com.sakalti.aoe.AOE;
import com.sakalti.aoe.items.ItemInit;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModelLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
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

@Mod.EventBusSubscriber(modid = AOE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Shelran extends Mob {

    public static final DeferredRegister<EntityType<Shelran>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, AOE.MODID);
    public static final RegistryObject<EntityType<Shelran>> SHELRAN_TYPE = ENTITY_TYPES.register("shelran",
        () -> EntityType.Builder.of(Shelran::new, MobType.WATER).sized(1.0F, 0.5F).build(new ResourceLocation(AOE.MODID, "shelran").toString()));

    private final ModelPart shellBody;

    public Shelran(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.shellBody = createShellBody().bakeRoot();
    }

    // 貝の形状設定
    private static LayerDefinition createShellBody() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        part.addOrReplaceChild("shellBody", CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8), // 貝の形状、サイズ
            PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 32);
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
        float damage = switch (this.level.getDifficulty()) {
            case EASY -> 3.0F;
            case NORMAL -> 5.0F;
            case HARD -> 6.0F;
            default -> 5.0F;
        };
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

    @SubscribeEvent
    public static void onEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(SHELRAN_TYPE.get(), createAttributes().build());
    }

    // Shelran用のレンダラー
    public static class ShelranRenderer extends MobRenderer<Shelran, ShelranModel> {
        public ShelranRenderer(EntityRendererProvider.Context context) {
            super(context, new ShelranModel(), 0.5F);
        }

        @Override
        public ResourceLocation getTextureLocation(Shelran entity) {
            return new ResourceLocation(AOE.MODID, "textures/entity/shelran.png");
        }
    }

    // モデル
    public static class ShelranModel extends EntityModel<Shelran> {
        private final ModelPart shellBody;

        public ShelranModel() {
            super(RenderType::entityTranslucent);
            ModelPart root = createShellBody().bakeRoot();
            this.shellBody = root.getChild("shellBody");
        }

        @Override
        public void setupAnim(Shelran entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            // 必要に応じてアニメーションのロジックを設定
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            this.shellBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    // クライアント側のレンダラー登録
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SHELRAN_TYPE.get(), ShelranRenderer::new);
    }

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
