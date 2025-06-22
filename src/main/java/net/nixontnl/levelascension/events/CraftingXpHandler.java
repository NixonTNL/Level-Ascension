package net.nixontnl.levelascension.events;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.cooking.CookingSkillManager;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

public class CraftingXpHandler {

    public static void handleCraftingXp(ServerPlayerEntity player, ItemStack resultStack, int amountCrafted)
{
        Identifier resultId = Registries.ITEM.getId(resultStack.getItem());

        int xpPerItem = CookingSkillManager.getXpForCraftedItem(resultStack.getItem());
        int totalXp = xpPerItem * amountCrafted;

        if (totalXp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(player.getUuid());
            data.addXP(SkillType.COOKING, player, totalXp);
        }
    }
}
