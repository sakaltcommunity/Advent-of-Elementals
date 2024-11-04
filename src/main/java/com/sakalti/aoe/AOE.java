package com.sakalti.aoe;

import com.sakalti.aoe.items.Amethyst;
import com.sakalti.aoe.items.CoralSword;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;

@Mod(AOE.MODID)
public class AOE {
    public static final String MODID = "aoe";

    public AOE() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        Amethyst.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        CoralSword.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus()); // コーラルソードを登録
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // アイテムモデルの登録
        event.getModEventBus().addListener(this::onModelRegistry);
    }

    private void onModelRegistry(ModelRegistryEvent event) {
        // アイテムモデルの登録
        Minecraft.getInstance().getItemRenderer().getItemModelShaper().register(Amethyst.AMETHYST_SWORD.get(), 
            new ModelResourceLocation(AOE.MODID + ":amethyst_sword", "inventory"));
        Minecraft.getInstance().getItemRenderer().getItemModelShaper().register(Amethyst.AMETHYST_AXE.get(), 
            new ModelResourceLocation(AOE.MODID + ":amethyst_axe", "inventory"));
        Minecraft.getInstance().getItemRenderer().getItemModelShaper().register(CoralSword.CORAL_SWORD.get(), 
            new ModelResourceLocation(AOE.MODID + ":coral_sword", "inventory"));
    }

    @SubscribeEvent
    public static void onCreativeTabEvent(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(Amethyst.AMETHYST_SWORD);
            event.accept(Amethyst.AMETHYST_AXE);
            event.accept(CoralSword.CORAL_SWORD); // コーラルソードを戦闘タブに追加
        }
    }
}
