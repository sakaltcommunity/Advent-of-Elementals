package com.sakalti.aoe.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemTier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.sakalti.aoe.AOE;

public class CoralSword {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AOE.MODID);

    // コーラルソードの登録
    public static final RegistryObject<SwordItem> CORAL_SWORD = ITEMS.register("coral_sword", 
        () -> new SwordItem(ItemTier.DIAMOND, 13, -2.4F, 
            new Item.Properties().tab(CreativeModeTabs.COMBAT).durability(2001)
        )
    );
}
