package net.nixontnl.levelascension.mixin;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nixontnl.levelascension.skills.logic.alchemy.FreshBrewTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingStandBlockEntity.class)
public class BrewingStandBlockEntityMixin {

    @Inject(
            method = "craft(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/collection/DefaultedList;)V",
            at = @At("TAIL")
    )
    private static void onBrewComplete(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci) {
        if (world.isClient()) return;

        // Ingredient slot is at index 3
        ItemStack ingredientStack = slots.get(3);
        boolean isStrong = false;
        boolean isExtended = false;

        if (ingredientStack.getItem() == Items.GLOWSTONE_DUST) {
            isStrong = true;
        } else if (ingredientStack.getItem() == Items.REDSTONE) {
            isExtended = true;
        }

        // Potion result slots are 0, 1, 2
        for (int i = 0; i < 3; i++) {
            ItemStack potionStack = slots.get(i);
            if (potionStack.getItem() instanceof PotionItem) {
                FreshBrewTracker.markBrewedWithUpgrade(world, pos, i, isStrong, isExtended);
            }
        }
    }
}

