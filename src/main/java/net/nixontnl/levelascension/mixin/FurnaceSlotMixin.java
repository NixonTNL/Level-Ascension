package net.nixontnl.levelascension.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.server.network.ServerPlayerEntity;


@Mixin(FurnaceOutputSlot.class)
public abstract class FurnaceSlotMixin extends Slot {

    public FurnaceSlotMixin() {
        super(null, 0, 0, 0); // dummy constructor for Mixin compatibility
    }

    @Inject(method = "onTakeItem", at = @At("TAIL"))
    private void onTakeCookedItem(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        World world = player.getWorld();
        BlockPos playerPos = player.getBlockPos();

        // Get the furnace/smoker/blast furnace block under or near the player
        BlockPos blockUnder = playerPos.down();
        BlockState state = world.getBlockState(blockUnder);
        String block = String.valueOf(state.getBlock());

        if (player instanceof ServerPlayerEntity serverPlayer) {
            SkillEventHandler.handleCookingXp(serverPlayer, stack, block); // ✅ Good
        }

    }
}
