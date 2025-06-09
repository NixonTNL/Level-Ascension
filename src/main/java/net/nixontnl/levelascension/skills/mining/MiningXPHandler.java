package net.nixontnl.levelascension.skills.mining;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.nixontnl.levelascension.skills.base.PlayerSkillDataManager;
import net.nixontnl.levelascension.utilities.XPUtils;

public class MiningXPHandler {

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            System.out.println("[LevelAscension] Block broken: " + state.getBlock().getName().getString());

            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

            if (isMineableBlock(state.getBlock().asItem().toString())) {
                int xp = XPUtils.getMiningXpForBlock(state.getBlock());
                if (xp > 0) {
                    PlayerSkillDataManager.addXP(serverPlayer, "mining", xp);
                }
            }
        });
    }

    private static boolean isMineableBlock(String blockName) {
        return blockName.contains("ore") || blockName.contains("stone") || blockName.contains("deepslate");
    }
}
