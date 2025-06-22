package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.events.CraftingXpHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingResultSlot.class)
public abstract class CraftingResultSlotMixin {

    @Shadow
    protected PlayerEntity player;

    @Inject(method = "onCrafted", at = @At("TAIL"))
    private void onCraftedInject(ItemStack stack, int amount, CallbackInfo ci) {
        if (!player.getWorld().isClient && player instanceof ServerPlayerEntity serverPlayer) {
            CraftingXpHandler.handleCraftingXp(serverPlayer, stack, amount);
        }
    }

    @Inject(method = "onTakeItem", at = @At("TAIL"))
    private void onTakeItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!player.getWorld().isClient && player instanceof ServerPlayerEntity serverPlayer) {
            CraftingXpHandler.handleCraftingXp(serverPlayer, stack, stack.getCount());
        }
    }
}
