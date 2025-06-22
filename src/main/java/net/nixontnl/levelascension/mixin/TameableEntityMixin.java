package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.nixontnl.levelascension.skills.logic.beastmastery.BeastmasterySkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TameableEntity.class)
public abstract class TameableEntityMixin {

    @Inject(method = "setOwner", at = @At("TAIL"))
    private void onTame(PlayerEntity player, CallbackInfo ci) {
        if (player instanceof ServerPlayerEntity serverPlayer && (Object) this instanceof LivingEntity living) {
            System.out.println("DEBUG: Tame triggered for " + living.getName().getString());
            BeastmasterySkillManager.handleTameXp(serverPlayer, living);
        }
    }
}
