package net.nixontnl.levelascension.skills.logic.smithing;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SmithingSkillManager {

    // Smelting ores → XP
    public static int getXpForSmelting(ItemStack result) {
        System.out.println("🔍 getXpForSmelting: " + result.getItem());

        if (result.getItem() == Items.IRON_INGOT) return 8;
        if (result.getItem() == Items.GOLD_INGOT) return 10;
        if (result.getItem() == Items.COPPER_INGOT) return 6;
        if (result.getItem() == Items.NETHERITE_SCRAP) return 20;

        return 0;
    }

    // Crafting ore-based gear → XP
    public static int getXpForCrafting(ItemStack result) {
        Item item = result.getItem();

        if (item == Items.IRON_SHOVEL) return 10;
        else if (item == Items.IRON_PICKAXE) return 16;
        else if (item == Items.IRON_AXE) return 14;
        else if (item == Items.IRON_HOE) return 10;
        else if (item == Items.IRON_SWORD) return 12;
        else if (item == Items.IRON_HELMET) return 16;
        else if (item == Items.IRON_CHESTPLATE) return 24;
        else if (item == Items.IRON_LEGGINGS) return 20;
        else if (item == Items.IRON_BOOTS) return 14;

        else if (item == Items.GOLDEN_SHOVEL) return 12;
        else if (item == Items.GOLDEN_PICKAXE) return 18;
        else if (item == Items.GOLDEN_AXE) return 16;
        else if (item == Items.GOLDEN_HOE) return 12;
        else if (item == Items.GOLDEN_SWORD) return 14;
        else if (item == Items.GOLDEN_HELMET) return 18;
        else if (item == Items.GOLDEN_CHESTPLATE) return 26;
        else if (item == Items.GOLDEN_LEGGINGS) return 22;
        else if (item == Items.GOLDEN_BOOTS) return 16;

        else if (item == Items.DIAMOND_SHOVEL) return 16;
        else if (item == Items.DIAMOND_PICKAXE) return 24;
        else if (item == Items.DIAMOND_AXE) return 22;
        else if (item == Items.DIAMOND_HOE) return 18;
        else if (item == Items.DIAMOND_SWORD) return 20;
        else if (item == Items.DIAMOND_HELMET) return 26;
        else if (item == Items.DIAMOND_CHESTPLATE) return 34;
        else if (item == Items.DIAMOND_LEGGINGS) return 30;
        else if (item == Items.DIAMOND_BOOTS) return 22;

        else if (item == Items.NETHERITE_SHOVEL) return 20;
        else if (item == Items.NETHERITE_PICKAXE) return 30;
        else if (item == Items.NETHERITE_AXE) return 28;
        else if (item == Items.NETHERITE_HOE) return 22;
        else if (item == Items.NETHERITE_SWORD) return 26;
        else if (item == Items.NETHERITE_HELMET) return 30;
        else if (item == Items.NETHERITE_CHESTPLATE) return 40;
        else if (item == Items.NETHERITE_LEGGINGS) return 35;
        else if (item == Items.NETHERITE_BOOTS) return 28;

        return 0;
    }

    public static int getXpForRepair(ItemStack base, ItemStack addition, ItemStack result) {
        Item item = result.getItem();

        if (item == Items.IRON_SHOVEL) return 5;
        else if (item == Items.IRON_PICKAXE) return 8;
        else if (item == Items.IRON_AXE) return 7;
        else if (item == Items.IRON_HOE) return 5;
        else if (item == Items.IRON_SWORD) return 6;
        else if (item == Items.IRON_HELMET) return 8;
        else if (item == Items.IRON_CHESTPLATE) return 12;
        else if (item == Items.IRON_LEGGINGS) return 10;
        else if (item == Items.IRON_BOOTS) return 7;

        else if (item == Items.GOLDEN_SHOVEL) return 6;
        else if (item == Items.GOLDEN_PICKAXE) return 9;
        else if (item == Items.GOLDEN_AXE) return 8;
        else if (item == Items.GOLDEN_HOE) return 6;
        else if (item == Items.GOLDEN_SWORD) return 7;
        else if (item == Items.GOLDEN_HELMET) return 9;
        else if (item == Items.GOLDEN_CHESTPLATE) return 13;
        else if (item == Items.GOLDEN_LEGGINGS) return 11;
        else if (item == Items.GOLDEN_BOOTS) return 8;

        else if (item == Items.DIAMOND_SHOVEL) return 8;
        else if (item == Items.DIAMOND_PICKAXE) return 12;
        else if (item == Items.DIAMOND_AXE) return 11;
        else if (item == Items.DIAMOND_HOE) return 9;
        else if (item == Items.DIAMOND_SWORD) return 10;
        else if (item == Items.DIAMOND_HELMET) return 13;
        else if (item == Items.DIAMOND_CHESTPLATE) return 17;
        else if (item == Items.DIAMOND_LEGGINGS) return 15;
        else if (item == Items.DIAMOND_BOOTS) return 11;

        else if (item == Items.NETHERITE_SHOVEL) return 10;
        else if (item == Items.NETHERITE_PICKAXE) return 15;
        else if (item == Items.NETHERITE_AXE) return 14;
        else if (item == Items.NETHERITE_HOE) return 11;
        else if (item == Items.NETHERITE_SWORD) return 13;
        else if (item == Items.NETHERITE_HELMET) return 15;
        else if (item == Items.NETHERITE_CHESTPLATE) return 20;
        else if (item == Items.NETHERITE_LEGGINGS) return 18;
        else if (item == Items.NETHERITE_BOOTS) return 14;

        return 0;
    }
}
