package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

import java.util.HashMap;
import java.util.UUID;

/**
 * Listens for player actions and awards XP.
 */
public class SkillEventHandler {

    // TEMPORARY player skill storage (in-memory, not saved!)
    public static final HashMap<UUID, PlayerSkillData> playerSkillDataMap = new HashMap<>();

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(player instanceof ServerPlayerEntity)) return;

            UUID id = player.getUuid();
            playerSkillDataMap.putIfAbsent(id, new PlayerSkillData());

            PlayerSkillData data = playerSkillDataMap.get(id);

            if (state.getBlock() == Blocks.STONE) {
                data.addXP(SkillType.MINING, (ServerPlayerEntity) player, 15);
                int level = data.getSkill(SkillType.MINING).getLevel();
                /* player.sendMessage(Text.literal("Mining XP: " + data.getSkill(SkillType.MINING).getXp() +
                        " | Level: " + level), true); */
            }
        });
    }

    public static PlayerSkillData getSkillData(UUID playerId) {
        return playerSkillDataMap.get(playerId);
    }



}
