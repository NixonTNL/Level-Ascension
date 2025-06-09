package net.nixontnl.levelascension.skills.woodcutting;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;
import net.nixontnl.levelascension.utilities.XPUtils;

public class WoodcuttingXPHandler {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

            Block block = state.getBlock();
            if (isLogBlock(block)) {
                int xp = XPUtils.getWoodcuttingXpForBlock(block);
                if (xp > 0) {
                    PlayerSkillDataManager.addXP(serverPlayer, "woodcutting", xp);
                }
            }
        });
    }

    private static boolean isLogBlock(Block block) {
        return block.getTranslationKey().toLowerCase().contains("log");
    }
}
