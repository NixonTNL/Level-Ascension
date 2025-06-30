package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.logic.enchanting.EnchantingSkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @Inject(method = "onTakeOutput", at = @At("TAIL"))
    private void onTakeEnchantOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        // Get total XP cost of the anvil operation
        int cost = ((AnvilScreenHandler)(Object)this).getLevelCost();

        // Send to skill manager
        EnchantingSkillManager.handleEnchantingXp(serverPlayer, stack.copy(), cost);
    }
}