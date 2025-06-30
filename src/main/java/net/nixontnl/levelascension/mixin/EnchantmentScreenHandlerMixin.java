package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.skills.logic.enchanting.EnchantingSkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreenHandler.class)
public abstract class EnchantmentScreenHandlerMixin {

    @Inject(method = "onButtonClick", at = @At(value = "RETURN"), cancellable = false)
    private void onEnchantingClick(PlayerEntity player, int id, CallbackInfoReturnable<Boolean> cir) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (!cir.getReturnValue()) return;

        // Slot 0 is the item being enchanted
        ItemStack enchantedItem = ((EnchantmentScreenHandler) (Object) this).getSlot(0).getStack();

        // The cost of the enchantment (level 1 = 1, 2, 3)
        int enchantmentCost = id + 1;

        // Grant XP
        EnchantingSkillManager.handleEnchantingXp(serverPlayer, enchantedItem.copy(), enchantmentCost);
    }
}