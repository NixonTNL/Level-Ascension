package net.nixontnl.levelascension.skills.logic.enchanting;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.minecraft.enchantment.Enchantment;
import net.nixontnl.levelascension.events.SkillEventHandler;

import java.util.Map;

public class EnchantingSkillManager {

    public static void handleEnchantingXp(ServerPlayerEntity player, ItemStack enchantedItem, int enchantmentCost) {
        if (player == null || enchantedItem == null) return;

        int totalXp = 0;

        ItemEnchantmentsComponent component = EnchantmentHelper.getEnchantments(enchantedItem);
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : component.getEnchantmentEntries()) {
            Enchantment enchantment = entry.getKey().value();
            int level = entry.getIntValue();

            int xp = level * 5; // base XP per level
            totalXp += xp;
        }

        totalXp += enchantmentCost * 2;

        SkillEventHandler.handleEnchantingXp(player, totalXp);
    }



}