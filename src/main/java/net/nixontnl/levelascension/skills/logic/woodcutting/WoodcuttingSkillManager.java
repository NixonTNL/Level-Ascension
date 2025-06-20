package net.nixontnl.levelascension.skills.logic.woodcutting;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class WoodcuttingSkillManager {
    public static final Map<Block, Integer> WOODCUTTING_XP_VALUES = new HashMap<>();

    static {
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:oak_log")), 10);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:spruce_log")), 12);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:birch_log")), 12);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:jungle_log")), 15);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:acacia_log")), 15);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:dark_oak_log")), 20);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:mangrove_log")), 20);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:cherry_log")), 20);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:crimson_stem")), 25);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:warped_stem")), 25);

        // Stripped logs
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_oak_log")), 5);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_spruce_log")), 6);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_birch_log")), 6);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_jungle_log")), 8);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_acacia_log")), 8);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_dark_oak_log")), 10);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_mangrove_log")), 10);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_cherry_log")), 10);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_crimson_stem")), 12);
        WOODCUTTING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:stripped_warped_stem")), 12);
    }

    public static int getXpForBlock(Block block) {
        return WOODCUTTING_XP_VALUES.getOrDefault(block, 0);
    }
}
