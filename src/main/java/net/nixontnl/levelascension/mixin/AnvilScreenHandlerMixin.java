package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.logic.enchanting.EnchantingSkillManager;
import net.nixontnl.levelascension.skills.logic.smithing.SmithingSkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Inject(method = "onTakeOutput", at = @At("TAIL"))
    private void onTakeEnchantOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;

        ItemStack input1 = handler.getSlot(0).getStack();
        ItemStack input2 = handler.getSlot(1).getStack();
        ItemStack result = stack.copy();

        // Check if the result is ore-based gear
        int smithingXp = SmithingSkillManager.getXpForRepair(input1, input2, result);
        if (smithingXp > 0) {
            SkillEventHandler.handleSmithingRepairXp(serverPlayer, input1, input2, result);
        } else {
            int cost = handler.getLevelCost();
            EnchantingSkillManager.handleEnchantingXp(serverPlayer, result, cost);
        }
    }
}
