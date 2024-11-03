package com.sakalti.aoe;

import com.sakalti.aoe.entities.Shelran;
import com.sakalti.aoe.items.Amethyst;
import com.sakalti.aoe.world.RuboreomWorld;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;

@Mod(AOE.MODID)
public class AOE {
    public static final String MODID = "aoe";

    public AOE() {
        // イベントリスナーの登録
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // アメジストアイテムの登録
        Amethyst.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // ルボレオムのワールドをセットアップ
        RuboreomWorld.setup();
        // シェランエンティティの登録
        Shelran.SHELRAN.register(FMLJavaModLoadingContext.get().getModEventBus());
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
    }

    @SubscribeEvent
    public static void onCreativeTabEvent(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.COMBAT) {
            event.accept(Amethyst.AMETHYST_SWORD);
            event.accept(Amethyst.AMETHYST_AXE);
        }
    }
}
