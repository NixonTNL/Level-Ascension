package net.nixontnl.levelascension.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nixontnl.levelascension.events.SkillEventHandler;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.alchemy.AlchemySkillManager;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin {

    @Inject(method = "onCollision", at = @At("TAIL"))
    private void onPotionSplash(HitResult hitResult, CallbackInfo ci) {
        PotionEntity potion = (PotionEntity)(Object)this;
        Entity thrower = potion.getOwner();

        if (!(thrower instanceof ServerPlayerEntity serverPlayer)) return;

        ItemStack stack = potion.getStack();
        if (!(stack.getItem() instanceof PotionItem)) return;

        World world = potion.getWorld();
        Vec3d pos = potion.getPos();

        // Get nearby entities within splash radius (default is 4.0)
        List<Entity> nearby = world.getOtherEntities(potion, new Box(pos, pos).expand(4.0));

        boolean affected = nearby.stream().anyMatch(e -> e instanceof LivingEntity);

        if (!affected) return;

        int xp = AlchemySkillManager.getXpForPotion(stack);
        if (xp > 0) {
            PlayerSkillData data = SkillEventHandler.getSkillData(serverPlayer.getUuid());
            data.addXP(SkillType.ALCHEMY, serverPlayer, xp);
        }
    }
}
