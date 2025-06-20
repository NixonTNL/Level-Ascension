package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import net.nixontnl.levelascension.skills.logic.mining.MiningSkillManager;

import java.util.HashMap;
import java.util.UUID;

public class SkillEventHandler {

    public static final HashMap<UUID, PlayerSkillData> playerSkillDataMap = new HashMap<>();

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

            UUID id = serverPlayer.getUuid();
            playerSkillDataMap.putIfAbsent(id, new PlayerSkillData());

            PlayerSkillData data = playerSkillDataMap.get(id);
            Block block = state.getBlock();

            int xp = MiningSkillManager.getXpForBlock(block);
            if (xp > 0) {
                data.addXP(SkillType.MINING, serverPlayer, xp);
            }
        });
    }

    public static PlayerSkillData getSkillData(UUID playerId) {
        return playerSkillDataMap.get(playerId);
    }
}
