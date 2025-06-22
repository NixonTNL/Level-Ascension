package net.nixontnl.levelascension.skills.logic.beastmastery;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.events.SkillEventHandler;

import java.util.Map;
import java.util.Set;

public class BeastmasterySkillManager {

    // List of passive mob IDs
    private static final Set<String> PASSIVE_MOBS = Set.of(
            "minecraft:cow", "minecraft:pig", "minecraft:sheep", "minecraft:chicken",
            "minecraft:horse", "minecraft:donkey", "minecraft:llama", "minecraft:mooshroom",
            "minecraft:rabbit", "minecraft:goat", "minecraft:parrot", "minecraft:camel",
            "minecraft:sniffer", "minecraft:turtle", "minecraft:strider", "minecraft:fox",
            "minecraft:ocelot", "minecraft:cat", "minecraft:panda", "minecraft:axolotl"
    );

    // Basic XP values for killing passive mobs
    private static final Map<String, Integer> KILL_XP = Map.ofEntries(
            Map.entry("minecraft:chicken", 2),
            Map.entry("minecraft:rabbit", 2),
            Map.entry("minecraft:sheep", 3),
            Map.entry("minecraft:pig", 3),
            Map.entry("minecraft:cow", 4),
            Map.entry("minecraft:goat", 4),
            Map.entry("minecraft:mooshroom", 4),
            Map.entry("minecraft:horse", 5),
            Map.entry("minecraft:donkey", 5),
            Map.entry("minecraft:llama", 6),
            Map.entry("minecraft:camel", 6),
            Map.entry("minecraft:fox", 6),
            Map.entry("minecraft:ocelot", 6),
            Map.entry("minecraft:cat", 4),
            Map.entry("minecraft:parrot", 5),
            Map.entry("minecraft:turtle", 4),
            Map.entry("minecraft:sniffer", 8),
            Map.entry("minecraft:panda", 8),
            Map.entry("minecraft:strider", 5),
            Map.entry("minecraft:axolotl", 3),
            Map.entry("minecraft:wolf", 5)

    );

    public static void handlePassiveKillXp(ServerPlayerEntity player, LivingEntity entity) {
        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        String key = id.toString();

        if (!PASSIVE_MOBS.contains(key)) return;

        PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
        if (data == null) return;

        int xp = KILL_XP.getOrDefault(key, 2); // Default to 2 XP if not listed
        data.addXP(SkillType.BEAST_MASTERY, player, xp);
    }

    public static void handleTameXp(ServerPlayerEntity player, LivingEntity entity) {
        if (player == null || entity == null) return;

        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        String key = id.toString();

        int xp = switch (key) {
            case "minecraft:wolf", "minecraft:cat" -> 20;
            case "minecraft:parrot" -> 25;
            case "minecraft:horse", "minecraft:donkey", "minecraft:mule" -> 30;
            case "minecraft:llama" -> 35;
            default -> 0;
        };

        if (xp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
            data.addXP(SkillType.BEAST_MASTERY, player, xp);
        }
    }

    public static void handleBreedXp(ServerPlayerEntity player, LivingEntity entity) {
        if (player == null || entity == null) return;

        Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
        String key = id.toString();

        // XP values by animal type (customize as needed)
        int xp = switch (key) {
            case "minecraft:chicken", "minecraft:rabbit" -> 6;
            case "minecraft:pig", "minecraft:sheep", "minecraft:cow", "minecraft:goat" -> 8;
            case "minecraft:horse", "minecraft:donkey", "minecraft:mule" -> 12;
            case "minecraft:llama", "minecraft:camel" -> 15;
            case "minecraft:fox", "minecraft:cat", "minecraft:wolf", "minecraft:panda" -> 10;
            case "minecraft:parrot", "minecraft:axolotl", "minecraft:turtle", "minecraft:sniffer" -> 10;
            default -> 5;
        };

        PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
        data.addXP(SkillType.BEAST_MASTERY, player, xp);
    }


}
