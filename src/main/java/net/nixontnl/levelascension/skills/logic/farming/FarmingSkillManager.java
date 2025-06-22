package net.nixontnl.levelascension.skills.logic.farming;

import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.util.XPUtils;
import net.nixontnl.levelascension.events.SkillEventHandler;

import java.util.HashMap;
import java.util.Map;

public class FarmingSkillManager {
    public static final Map<Block, Integer> FARMING_XP_VALUES = new HashMap<>();

    static {
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:wheat")), 10);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:carrots")), 8);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:potatoes")), 8);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:beetroots")), 9);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:nether_wart")), 12);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:cocoa")), 5);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:melon_stem")), 3);
        FARMING_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:pumpkin_stem")), 3);
    }

    public static int getXpForBlock(Block block) {
        return FARMING_XP_VALUES.getOrDefault(block, 0);
    }
}

