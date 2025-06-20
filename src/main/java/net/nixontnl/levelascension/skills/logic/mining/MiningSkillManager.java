package net.nixontnl.levelascension.skills.logic.mining;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;

import java.util.HashMap;
import java.util.Map;

public class MiningSkillManager {
    private static final Map<Block, Integer> MINING_XP_VALUES = new HashMap<>();

    static {
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stone")), 5);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:granite")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:diorite")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:andesite")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:tuff")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:calcite")), 6);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:dripstone_block")), 6);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:white_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:orange_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:magenta_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:light_blue_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:yellow_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:lime_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:pink_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:gray_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:light_gray_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:cyan_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:purple_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:blue_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:brown_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:green_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:red_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:black_terracotta")), 4);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:glazed_terracotta")), 6);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:coal_ore")), 10);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:iron_ore")), 15);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:copper_ore")), 12);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:gold_ore")), 20);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:redstone_ore")), 25);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:lapis_ore")), 25);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:emerald_ore")), 40);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:diamond_ore")), 50);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:nether_gold_ore")), 18);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:nether_quartz_ore")), 16);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:ancient_debris")), 75);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate")), 5);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_coal_ore")), 12);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_iron_ore")), 18);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_copper_ore")), 15);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_gold_ore")), 25);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_redstone_ore")), 30);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_lapis_ore")), 30);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_emerald_ore")), 45);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:deepslate_diamond_ore")), 60);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:netherrack")), 2);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:end_stone")), 2);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:blackstone")), 5);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:basalt")), 5);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:mossy_cobblestone")), 3);
        MINING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:cobblestone")), 3);
    }

    public static int getXpForBlock(Block block) {
        return MINING_XP_VALUES.getOrDefault(block, 0);
    }
}
