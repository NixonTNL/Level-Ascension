package net.nixontnl.levelascension.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nixontnl.levelascension.skills.logic.farming.FarmingSkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerPlayerEntity player;

    @Inject(method = "tryBreakBlock", at = @At("HEAD"))
    private void levelascension$onCropBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        World world = player.getWorld();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof CropBlock crop && crop.isMature(state)) {
            int xp = FarmingSkillManager.getXpForBlock(block);
            if (xp > 0) {
            }
        }
    }
}
