package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.player.PlayerEntity;
import net.nixontnl.levelascension.events.PlayerBreedAnimalsCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin {

    @Inject(method = "breed", at = @At("TAIL"))
    private void onBreed(ServerWorld world, AnimalEntity mate, CallbackInfo ci) {
        AnimalEntity self = (AnimalEntity) (Object) this;

        // Only fire if player is set (the parent that caused breeding)
        PlayerEntity player = self.getLovingPlayer();
        if (player instanceof ServerPlayerEntity serverPlayer) {
            PassiveEntity child = self.createChild(world, mate);
            if (child != null) {
                PlayerBreedAnimalsCallback.EVENT.invoker().onBreed(serverPlayer, self, mate, child);
            }
        }
    }
}
