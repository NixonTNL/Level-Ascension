package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.logic.smithing.SmithingSkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Unique
    private int levelAscension$cachedAnvilCost = 0;

    @Inject(method = "onTakeOutput", at = @At("HEAD"))
    private void cacheAnvilCost(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        this.levelAscension$cachedAnvilCost = ((AnvilScreenHandler)(Object)this).getLevelCost();
    }

    @Inject(method = "onTakeOutput", at = @At("TAIL"))
    private void onTakeAnvilOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        AnvilScreenHandler handler = (AnvilScreenHandler)(Object)this;

        ItemStack input1 = handler.getSlot(0).getStack();
        ItemStack input2 = handler.getSlot(1).getStack();
        ItemStack result = stack.copy();

        boolean isEnchantedBookUsed = input1.getItem() == Items.ENCHANTED_BOOK || input2.getItem() == Items.ENCHANTED_BOOK;
        boolean resultHasEnchantments = !stack.getEnchantments().isEmpty();

        if (isEnchantedBookUsed || resultHasEnchantments) {
            SkillEventHandler.handleEnchantingXp(serverPlayer, levelAscension$cachedAnvilCost);
            System.out.println("✨ Enchanting XP granted from Anvil. Cached Cost: " + levelAscension$cachedAnvilCost);
        } else {
            int smithingXp = SmithingSkillManager.getXpForRepair(input1, input2, result);
            if (smithingXp > 0) {
                SkillEventHandler.handleSmithingRepairXp(serverPlayer, input1, input2, result);
                System.out.println("🛠️ Smithing XP granted from Anvil.");
            }
        }
    }
}
