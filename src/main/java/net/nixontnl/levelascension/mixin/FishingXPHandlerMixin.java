package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.nixontnl.levelascension.util.SkillXPHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingXPHandlerMixin {

    @Inject(method = "use", at = @At("RETURN"))
    private void onReelIn(ItemStack usedItem, CallbackInfoReturnable<Integer> cir) {
        if (cir.getReturnValue() != 1) return;

        FishingBobberEntity bobber = (FishingBobberEntity)(Object) this;

        if (bobber.getWorld().isClient()) return;
        if (!(bobber.getOwner() instanceof ServerPlayerEntity player)) return;

        ItemStack caught = usedItem;
        int xp;
        String reason;

        if (caught.isOf(Items.COD) || caught.isOf(Items.SALMON) ||
                caught.isOf(Items.PUFFERFISH) || caught.isOf(Items.TROPICAL_FISH)) {
            xp = 10;
            reason = "fish";
        } else if (caught.isOf(Items.ENCHANTED_BOOK)) {
            xp = 30;
            reason = "enchanted book";
        } else if ((caught.isOf(Items.BOW) || caught.isOf(Items.FISHING_ROD)) && caught.hasEnchantments()) {
            xp = 25;
            reason = "enchanted gear";
        } else if (caught.isOf(Items.BOW) || caught.isOf(Items.FISHING_ROD)) {
            xp = 15;
            reason = "regular gear";
        } else if (caught.isOf(Items.NAME_TAG) || caught.isOf(Items.NAUTILUS_SHELL) || caught.isOf(Items.SADDLE)) {
            xp = 20;
            reason = "treasure";
        } else {
            xp = 5;
            reason = "junk";
        }

        int oldLevel = SkillXPHelper.getLevel(player, "fishing");
        SkillXPHelper.addXP(player, "fishing", xp);

        if (SkillXPHelper.getLevel(player, "fishing") > oldLevel) {
            SkillXPHelper.sendLevelUpMessage(player, "fishing");
        }
    }
}
