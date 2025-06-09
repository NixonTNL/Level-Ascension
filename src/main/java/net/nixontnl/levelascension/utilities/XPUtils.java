package net.nixontnl.levelascension.utilities;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class XPUtils {

    public static int getMiningXpForBlock(Block block) {
        if (block == Blocks.COAL_ORE || block == Blocks.DEEPSLATE_COAL_ORE) return 5;
        if (block == Blocks.IRON_ORE || block == Blocks.DEEPSLATE_IRON_ORE) return 7;
        if (block == Blocks.COPPER_ORE || block == Blocks.DEEPSLATE_COPPER_ORE) return 6;
        if (block == Blocks.GOLD_ORE || block == Blocks.DEEPSLATE_GOLD_ORE) return 10;
        if (block == Blocks.DIAMOND_ORE || block == Blocks.DEEPSLATE_DIAMOND_ORE) return 20;
        if (block == Blocks.STONE || block == Blocks.DEEPSLATE) return 1;
        return 0;
    }

    public static int getWoodcuttingXpForBlock(Block block) {
        if (block == Blocks.OAK_LOG || block == Blocks.SPRUCE_LOG || block == Blocks.BIRCH_LOG
                || block == Blocks.JUNGLE_LOG || block == Blocks.ACACIA_LOG || block == Blocks.DARK_OAK_LOG
                || block == Blocks.MANGROVE_LOG || block == Blocks.CHERRY_LOG || block == Blocks.BAMBOO_BLOCK) {
            return 5;
        }
        return 0;
    }

    public static void sendLevelUpMessage(ServerPlayerEntity player, String skill, int level) {
        player.sendMessage(Text.literal("§aYou leveled up " + skill + "! New Level: " + level), false);
    }

    public static void sendXPMessage(ServerPlayerEntity player, String skill, int amount) {
        player.sendMessage(Text.literal("§7+" + amount + " " + skill + " XP"), false);
    }
}
