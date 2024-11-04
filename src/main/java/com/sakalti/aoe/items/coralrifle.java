package com.sakalti.aoe;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class AoeMod implements ModInitializer {
    public static final CoralRifle CORAL_RIFLE = new CoralRifle();

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("aoe", "coral_rifle"), CORAL_RIFLE);
    }

    public static class CoralRifle extends Item {
        public CoralRifle() {
            super(new Item.Settings().group(ItemGroup.COMBAT).maxCount(1));
        }

        @Override
        public void use(World world, PlayerEntity player, Hand hand) {
            ItemStack stoneStack = new ItemStack(net.minecraft.item.Items.STONE);

            if (!world.isClient && player.getInventory().contains(stoneStack)) {
                Vec3d playerPos = player.getCameraPosVec(1.0F);
                Vec3d viewVec = player.getRotationVec(1.0F).multiply(50);
                EntityHitResult hitResult = world.raycast(new RaycastContext(playerPos, playerPos.add(viewVec),
                        RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
                
                if (hitResult != null && hitResult.getEntity() instanceof Entity target) {
                    world.playSound(null, player.getBlockPos(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    target.damage(player, 2.88F); // ダメージ計算
                    player.getInventory().removeOne(stoneStack); // 弾として石を消費
                }
            }
        }
    }
}
