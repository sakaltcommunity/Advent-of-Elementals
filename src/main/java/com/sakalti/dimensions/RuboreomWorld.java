package com.sakalti.aoe.world;

import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionOptions;
import net.minecraft.world.level.dimension.GeneratorSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Features;
import net.minecraftforge.event.world.PotentialSpawnsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RuboreomWorld {
    public static final DeferredRegister<DimensionType> DIMENSIONS = DeferredRegister.create(ForgeRegistries.DIMENSIONS, AOE.MODID);
    public static final RegistryObject<DimensionType> RUBOREOM = DIMENSIONS.register("ruboreom", 
        () -> new DimensionType(GeneratorSettings.water(), true, false, 0, 0, false, false, false, false, false)
    );

    public static void setup() {
        Registry.register(Registry.DIMENSION_TYPE, AOE.MODID + ":ruboreom", RUBOREOM.get());
    }

    @SubscribeEvent
    public static void onPotentialSpawns(PotentialSpawnsEvent event) {
        if (event.getWorld().dimension() == RUBOREOM.get()) {
            // シェランを水中でスポーンさせる設定
            event.getPotentialSpawns().add(new PotentialSpawnInfo(Shelran.SHELRAN_TYPE.get(), 100, 1, 3));
        }
    }
}
