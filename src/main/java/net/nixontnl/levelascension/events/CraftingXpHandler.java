package net.nixontnl.levelascension.events;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.crafting.CraftingSkillManager;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

public class CraftingXpHandler {

    public static void handleCraftingXp(ServerPlayerEntity player, ItemStack resultStack, int amountCrafted) {
        Item item = resultStack.getItem();
        int xpPerItem = CraftingSkillManager.getXpForCraftedItem(item);
        int totalXp = xpPerItem * amountCrafted;

        if (totalXp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
            data.addXP(SkillType.CRAFTING, player, totalXp);
        }
    }
}
