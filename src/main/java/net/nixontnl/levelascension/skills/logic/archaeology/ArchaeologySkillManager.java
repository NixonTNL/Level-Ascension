package net.nixontnl.levelascension.skills.logic.archaeology;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Blocks;

public class ArchaeologySkillManager {

    public static int getXpForArtifact(ItemStack stack) {
        String itemKey = stack.getItem().getTranslationKey();

        if (itemKey.contains("pottery_shard")) return 25;
        if (itemKey.contains("suspicious_sand") || itemKey.contains("suspicious_gravel")) return 0;
        if (itemKey.contains("smithing_template")) return 150;
        if (itemKey.contains("sniffer") || itemKey.contains("egg")) return 200;
        if (itemKey.contains("emerald")) return 40;
        if (itemKey.contains("iron_ingot") || itemKey.contains("gold_ingot")) return 30;

        return 10; // Fallback XP
    }
}
