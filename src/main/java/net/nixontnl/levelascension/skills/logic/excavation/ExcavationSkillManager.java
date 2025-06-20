package net.nixontnl.levelascension.skills.logic.excavation;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ExcavationSkillManager {
    public static final Map<Block, Integer> EXCAVATION_XP_VALUES = new HashMap<>();

    static {
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:dirt")),3);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:grass_block")),3);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:coarse_dirt")),4);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:rooted_dirt")),5);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:podzol")),5);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:mycelium")),5);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:sand")),4);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:red_sand")),5);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:gravel")),6);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:clay")),8);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:mud")),7);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:muddy_mangrove_roots")),9);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:snow")),2);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:snow_block")),3);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:soul_sand")),7);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:soul_soil")),8);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:farmland")),2);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:dirt_path")),2);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:powder_snow")),4);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:suspicious_sand")),10);
        EXCAVATION_XP_VALUES.put(Registries.BLOCK.get(Identifier.of("minecraft:suspicious_gravel")),10);

    }

    public static int getXpForBlock(Block block) {
        return EXCAVATION_XP_VALUES.getOrDefault(block, 0);
    }
}
