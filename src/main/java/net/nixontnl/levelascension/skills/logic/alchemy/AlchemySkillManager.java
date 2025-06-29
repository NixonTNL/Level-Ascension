package net.nixontnl.levelascension.skills.logic.alchemy;

import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;

public class AlchemySkillManager {

    public static int getXpForPotion(ItemStack stack) {
        if (!(stack.getItem() instanceof PotionItem)) return 0;

        String key = stack.getTranslationKey().toLowerCase();

        // Base / No-effect potions
        if (key.contains("awkward")) return 2;
        if (key.contains("mundane")) return 1;
        if (key.contains("thick")) return 1;

        // Basic / Tier 1 potions
        if (key.contains("potion.effect.swiftness")) return 6;
        if (key.contains("potion.effect.slowness")) return 6;
        if (key.contains("potion.effect.leaping")) return 6;
        if (key.contains("potion.effect.night_vision")) return 6;
        if (key.contains("potion.effect.water_breathing")) return 6;
        if (key.contains("potion.effect.weakness")) return 6;

        // Mid-tier potions
        if (key.contains("potion.effect.healing")) return 8;
        if (key.contains("potion.effect.poison")) return 8;
        if (key.contains("potion.effect.fire_resistance")) return 8;
        if (key.contains("potion.effect.invisibility")) return 8;
        if (key.contains("potion.effect.slow_falling")) return 8;

        // Advanced potions
        if (key.contains("potion.effect.regeneration")) return 10;
        if (key.contains("potion.effect.strength")) return 10;
        if (key.contains("potion.effect.harming")) return 10;
        if (key.contains("potion.effect.turtle_master")) return 12;

        // Rare / 1.21-exclusive potions
        if (key.contains("potion.effect.infestation")) return 15;
        if (key.contains("potion.effect.oozing")) return 15;
        if (key.contains("potion.effect.weaving")) return 15;
        if (key.contains("potion.effect.wind_charging")) return 15;

        // Fallback default for unknowns
        return 5;
    }
}
