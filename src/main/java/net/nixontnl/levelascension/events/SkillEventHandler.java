package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

import net.nixontnl.levelascension.skills.logic.mining.MiningSkillManager;
import net.nixontnl.levelascension.skills.logic.woodcutting.WoodcuttingSkillManager;
import net.nixontnl.levelascension.skills.logic.excavation.ExcavationSkillManager;

import java.util.HashMap;
import java.util.UUID;

public class SkillEventHandler {

    public static final HashMap<UUID, PlayerSkillData> playerSkillDataMap = new HashMap<>();

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

            UUID id = player.getUuid();
            playerSkillDataMap.putIfAbsent(id, new PlayerSkillData());
            PlayerSkillData data = playerSkillDataMap.get(id);

            Block brokenBlock = state.getBlock();

            // Mining XP
            int miningXp = MiningSkillManager.getXpForBlock(brokenBlock);
            if (miningXp > 0) {
                data.addXP(SkillType.MINING, serverPlayer, miningXp);
            }

            // Woodcutting XP
            int woodcuttingXp = WoodcuttingSkillManager.getXpForBlock(brokenBlock);
            if (woodcuttingXp > 0) {
                data.addXP(SkillType.WOODCUTTING, serverPlayer, woodcuttingXp);
            }

            // Excavation XP
            int excavationXp = ExcavationSkillManager.getXpForBlock(brokenBlock);
            if (excavationXp > 0) {
                data.addXP(SkillType.EXCAVATION, serverPlayer, excavationXp);
            }
        });
    }

    public static PlayerSkillData getSkillData(UUID playerId) {
        return playerSkillDataMap.get(playerId);
    }
}
