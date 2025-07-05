package net.nixontnl.levelascension.skills.logic.crafting;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

public class CraftingSkillManager {

    private static final Set<Item> EXCLUDED_ITEMS = new HashSet<>();

    static {
        // Exclude items from other skills
        excludeSmithingItems();
        excludeCookingItems();
        excludeAlchemyItems();

        // Exclude creative-only or non-survival items
        excludeUncraftables();
    }

    private static void excludeSmithingItems() {
        Item[] smithing = {
                Items.IRON_SWORD, Items.IRON_PICKAXE, Items.IRON_AXE, Items.IRON_SHOVEL, Items.IRON_HOE,
                Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS,
                Items.GOLDEN_SWORD, Items.GOLDEN_PICKAXE, Items.GOLDEN_AXE,
                Items.DIAMOND_SWORD, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE,
                Items.NETHERITE_SWORD, Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE,
                Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS
        };
        for (Item item : smithing) EXCLUDED_ITEMS.add(item);
    }

    private static void excludeCookingItems() {
        Item[] cookedFoods = {
                Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_CHICKEN, Items.BAKED_POTATO,
                Items.COOKED_MUTTON, Items.COOKED_COD, Items.COOKED_SALMON, Items.COOKED_RABBIT,
                Items.BREAD, Items.MUSHROOM_STEW, Items.SUSPICIOUS_STEW
        };
        for (Item item : cookedFoods) EXCLUDED_ITEMS.add(item);
    }

    private static void excludeAlchemyItems() {
        Item[] potions = {
                Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION, Items.TIPPED_ARROW,
                Items.GLASS_BOTTLE, Items.BREWING_STAND, Items.CAULDRON
        };
        for (Item item : potions) EXCLUDED_ITEMS.add(item);
    }

    private static void excludeUncraftables() {
        Item[] uncraftables = {
                Items.BARRIER, Items.COMMAND_BLOCK, Items.STRUCTURE_BLOCK, Items.JIGSAW,
                Items.DEBUG_STICK, Items.SPAWNER, Items.KNOWLEDGE_BOOK
        };
        for (Item item : uncraftables) EXCLUDED_ITEMS.add(item);
    }

    public static int getXpForCraftedItem(Item item) {
        if (EXCLUDED_ITEMS.contains(item)) return 0;

        Identifier id = Registries.ITEM.getId(item);
        String name = id.getPath();

        // Easy Tier (1–3 XP)
        if (name.contains("stick") || name.contains("ladder") || name.contains("sign") || name.contains("button") ||
                name.contains("pressure_plate") || name.contains("trapdoor") || name.contains("fence") ||
                name.contains("fence_gate") || name.contains("paper") || name.contains("book") ||
                name.contains("carpet") || name.contains("wool") || name.contains("glass_pane") ||
                name.contains("snow_block") || name.contains("dye") || name.contains("item_frame") ||
                name.contains("painting") || name.contains("flower_pot") || name.contains("chest") ||
                name.contains("barrel") || name.contains("note_block") || name.contains("slab") ||
                name.contains("stairs") || name.contains("wall") || name.contains("brick")) {
            return 2;
        }

        // Medium Tier (4–7 XP)
        if (name.contains("map") || name.contains("quill") || name.contains("firework") || name.contains("banner") ||
                name.contains("lectern") || name.contains("rail") || name.contains("loom") ||
                name.contains("stonecutter") || name.contains("observer") || name.contains("piston") ||
                name.contains("dispenser") || name.contains("dropper") || name.contains("tripwire") ||
                name.contains("target") || name.contains("daylight") || name.contains("chiseled") ||
                name.contains("polished") || name.contains("glazed") || name.contains("concrete")) {
            return 6;
        }

        // Hard Tier (8–15 XP)
        if (name.contains("comparator") || name.contains("repeater") || name.contains("beacon") ||
                name.contains("trial_key") || name.contains("copper_bulb") || name.contains("waxed") ||
                name.contains("jukebox") || name.contains("lightning_rod") || name.contains("spyglass") ||
                name.contains("bundle") || name.contains("bookshelf") || name.contains("cartography") ||
                name.contains("composter") || name.contains("tinted_glass") || name.contains("sculk_sensor")) {
            return 15;
        }

        // Default fallback XP
        return 3;
    }
}
