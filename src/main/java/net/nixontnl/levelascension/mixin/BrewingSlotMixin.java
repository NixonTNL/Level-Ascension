package net.nixontnl.levelascension.mixin;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.alchemy.AlchemySkillManager;
import net.nixontnl.levelascension.skills.logic.alchemy.FreshBrewTracker;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slot.class)
public abstract class BrewingSlotMixin {

    @Inject(method = "onTakeItem", at = @At("HEAD"))
    private void onTakeBrewedPotion(PlayerEntity player, ItemStack stack, CallbackInfo ci) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;
        if (!(stack.getItem() instanceof PotionItem)) return;

        Slot self = (Slot)(Object)this;
        Inventory inventory = self.inventory;

        if (!(inventory instanceof BrewingStandBlockEntity stand)) return;

        int slotIndex = self.getIndex();
        if (slotIndex < 0 || slotIndex > 2) return;

        BlockPos pos = stand.getPos();
        World world = stand.getWorld();

        FreshBrewTracker.UpgradeFlags flags = FreshBrewTracker.consumeBrewInfo(world, pos, slotIndex);
        if (flags != null) {
            SkillEventHandler.handleAlchemyXp(serverPlayer, stack, 1, flags.isStrong, flags.isExtended);
        }
    }
}
