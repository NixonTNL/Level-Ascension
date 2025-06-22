package net.nixontnl.levelascension.skills.logic.cooking;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class CookingSkillManager {

    private static final Map<Item, Integer> BASE_XP = new HashMap<>();
    private static final Map<Item, Integer> CRAFTED_XP = new HashMap<>();

    static {
        // Smelted XP
        BASE_XP.put(Items.COOKED_BEEF, 10);
        BASE_XP.put(Items.COOKED_PORKCHOP, 9);
        BASE_XP.put(Items.COOKED_CHICKEN, 8);
        BASE_XP.put(Items.COOKED_COD, 6);
        BASE_XP.put(Items.COOKED_SALMON, 6);
        BASE_XP.put(Items.BAKED_POTATO, 4);
        BASE_XP.put(Items.COOKED_MUTTON, 7);
        BASE_XP.put(Items.COOKED_RABBIT, 7);
        BASE_XP.put(Items.BREAD, 5); // optional

        // Crafted XP
        CRAFTED_XP.put(Items.BREAD, 5);
        CRAFTED_XP.put(Items.CAKE, 15);
        CRAFTED_XP.put(Items.MUSHROOM_STEW, 6);
        CRAFTED_XP.put(Items.RABBIT_STEW, 10);
        CRAFTED_XP.put(Items.BEETROOT_SOUP, 5);
        CRAFTED_XP.put(Items.PUMPKIN_PIE, 8);
        CRAFTED_XP.put(Items.SUSPICIOUS_STEW, 7);
        CRAFTED_XP.put(Items.COOKIE, 3);
    }

    public static int getXpForCookedItem(Item item, String sourceId) {
        int base = BASE_XP.getOrDefault(item, 3); // fallback
        return switch (sourceId) {
            case "minecraft:campfire" -> (int) (base * 0.6f);
            case "minecraft:smoker" -> (int) (base * 1.4f);
            case "minecraft:furnace" -> base;
            default -> base;
        };
    }

    public static int getXpForCraftedItem(Item item) {
        if (item == Items.BREAD) return 5;
        if (item == Items.CAKE) return 12;
        if (item == Items.PUMPKIN_PIE) return 12;
        if (item == Items.MUSHROOM_STEW) return 6;
        if (item == Items.RABBIT_STEW) return 14;
        if (item == Items.BEETROOT_SOUP) return 10;

        return 0; // ✅ fallback value
    }

}
