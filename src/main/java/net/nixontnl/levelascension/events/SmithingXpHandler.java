package net.nixontnl.levelascension.events;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.smithing.SmithingSkillManager;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

public class SmithingXpHandler {

    public static void handleSmeltingXp(ServerPlayerEntity player, ItemStack resultStack, int amount) {
        int xpPerItem = SmithingSkillManager.getXpForSmelting(resultStack);
        int totalXp = xpPerItem * amount;

        if (totalXp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
            data.addXP(SkillType.SMITHING, player, totalXp);
        }
    }

    public static void handleCraftingXp(ServerPlayerEntity player, ItemStack resultStack, int amountCrafted) {
        int xpPerItem = SmithingSkillManager.getXpForCrafting(resultStack);
        int totalXp = xpPerItem * amountCrafted;

        if (totalXp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
            data.addXP(SkillType.SMITHING, player, totalXp);
        }
    }

    public static void handleRepairXp(ServerPlayerEntity player, ItemStack base, ItemStack addition, ItemStack result) {
        int xp = SmithingSkillManager.getXpForRepair(base, addition, result);

        if (xp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
            data.addXP(SkillType.SMITHING, player, xp);
        }
    }
}
