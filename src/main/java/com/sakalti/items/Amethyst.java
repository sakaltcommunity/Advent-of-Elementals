package com.sakalti.aoe.items;

import com.sakalti.aoe.AOE;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Amethyst {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AOE.MODID);

    // アメジストの剣（攻撃力8.5）
    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword", 
        () -> new SwordItem(Tiers.DIAMOND, 8, -2.4F, new Properties().durability(1200))
    );

    // アメジストの斧（攻撃力11）
    public static final RegistryObject<Item> AMETHYST_AXE = ITEMS.register("amethyst_axe", 
        () -> new AxeItem(Tiers.DIAMOND, 11, -3.0F, new Properties().durability(1200))
    );
}
