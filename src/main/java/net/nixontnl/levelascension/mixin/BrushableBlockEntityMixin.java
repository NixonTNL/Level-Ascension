package net.nixontnl.levelascension.mixin;

import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.util.FreshBrushTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin {

    @Inject(method = "finishBrushing", at = @At("TAIL"))
    private void onFinishBrushing(PlayerEntity user, CallbackInfo ci) {
        BrushableBlockEntity self = (BrushableBlockEntity)(Object)this;
        World world = self.getWorld();
        BlockPos pos = self.getPos();

        if (!world.isClient && FreshBrushTracker.wasFreshlyBrushed(world, pos)) {
            if (user instanceof ServerPlayerEntity serverPlayer) {
                // Placeholder ItemStack — ideally replace with the actual dropped item if needed
                SkillEventHandler.handleArchaeologyXp(serverPlayer, new ItemStack(Items.BRUSH), 1);
            }
            FreshBrushTracker.clear(world, pos);
        }
    }
}
