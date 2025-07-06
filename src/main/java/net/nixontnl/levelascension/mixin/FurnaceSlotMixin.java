package net.nixontnl.levelascension.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.logic.cooking.CookingSkillManager;
import net.nixontnl.levelascension.skills.logic.smithing.SmithingSkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceOutputSlot.class)
public abstract class FurnaceSlotMixin extends Slot {

    private PlayerEntity player;

    public FurnaceSlotMixin() {
        super(null, 0, 0, 0);
    }

    @Unique
    private ItemStack cachedResult = null;

    @Unique
    private int cachedAmount = 0;

    // ✅ Catches shift-clicking stacks out of the furnace
    @Inject(method = "onCrafted", at = @At("TAIL"))
    private void onCraftedInject(ItemStack stack, int amount, CallbackInfo ci) {
        this.cachedResult = stack.copy();
        this.cachedAmount = amount;
    }

    @Inject(method = "onTakeItem", at = @At("TAIL"))
    private void onTakeItemInject(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (cachedResult == null || cachedAmount <= 0) return;

        Inventory inventory = this.inventory;
        if (!(inventory instanceof AbstractFurnaceBlockEntity furnaceEntity)) return;

        World world = furnaceEntity.getWorld();
        BlockPos pos = furnaceEntity.getPos();
        Block block = world.getBlockState(pos).getBlock();
        String blockId = Registries.BLOCK.getId(block).toString();

        int smithingXp = SmithingSkillManager.getXpForSmelting(cachedResult);
        int cookingXp = CookingSkillManager.getXpForCookedItem(cachedResult.getItem(), blockId);

        if ((block == Blocks.BLAST_FURNACE || block == Blocks.FURNACE) && smithingXp > 0) {
            SkillEventHandler.handleSmithingSmeltXp(serverPlayer, cachedResult, cachedAmount);
        } else if ((block == Blocks.SMOKER || block == Blocks.FURNACE) && cookingXp > 0) {
            SkillEventHandler.handleCookingXp(serverPlayer, cachedResult, blockId);
        }

        // Reset cache
        cachedResult = null;
        cachedAmount = 0;
    }

    // ✅ Catches normal clicking to take cooked items
    @Inject(method = "takeStack", at = @At("TAIL"))
    private void onTakeStackInject(int amount, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity player = this.player;
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        ItemStack resultStack = cir.getReturnValue();
        if (resultStack.isEmpty()) return;

        Inventory inventory = this.inventory;
        if (!(inventory instanceof AbstractFurnaceBlockEntity furnaceEntity)) return;

        World world = furnaceEntity.getWorld();
        BlockPos pos = furnaceEntity.getPos();
        Block block = world.getBlockState(pos).getBlock();
        String blockId = Registries.BLOCK.getId(block).toString();

        int smithingXp = SmithingSkillManager.getXpForSmelting(resultStack);
        int cookingXp = CookingSkillManager.getXpForCookedItem(resultStack.getItem(), blockId);

        if ((block == Blocks.BLAST_FURNACE || block == Blocks.FURNACE) && smithingXp > 0) {
            SkillEventHandler.handleSmithingSmeltXp(serverPlayer, resultStack, amount);
        } else if ((block == Blocks.SMOKER || block == Blocks.FURNACE) && cookingXp > 0) {
            SkillEventHandler.handleCookingXp(serverPlayer, resultStack, blockId);
        }
    }
}
