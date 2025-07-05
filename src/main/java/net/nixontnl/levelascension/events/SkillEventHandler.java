package net.nixontnl.levelascension.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.nixontnl.levelascension.skills.SkillType;
import net.nixontnl.levelascension.skills.logic.alchemy.AlchemySkillManager;
import net.nixontnl.levelascension.skills.logic.archaeology.ArchaeologySkillManager;
import net.nixontnl.levelascension.skills.logic.beastmastery.BeastmasterySkillManager;
import net.nixontnl.levelascension.skills.logic.cooking.CookingSkillManager;
import net.nixontnl.levelascension.skills.logic.excavation.ExcavationSkillManager;
import net.nixontnl.levelascension.skills.logic.farming.FarmingSkillManager;
import net.nixontnl.levelascension.skills.logic.melee.MeleeSkillManager;
import net.nixontnl.levelascension.skills.logic.mining.MiningSkillManager;
import net.nixontnl.levelascension.skills.logic.ranged.RangedSkillManager;
import net.nixontnl.levelascension.skills.logic.smithing.SmithingSkillManager;
import net.nixontnl.levelascension.skills.logic.woodcutting.WoodcuttingSkillManager;
import net.nixontnl.levelascension.skills.player.PlayerSkillData;

import java.util.HashMap;
import java.util.UUID;

public class SkillEventHandler {

    public static final HashMap<UUID, PlayerSkillData> playerSkillDataMap = new HashMap<>();

    public static void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

            UUID id = player.getUuid();
            playerSkillDataMap.putIfAbsent(id, new PlayerSkillData());
            PlayerSkillData data = playerSkillDataMap.get(id);

            Block brokenBlock = state.getBlock();

            int miningXp = MiningSkillManager.getXpForBlock(brokenBlock);
            if (miningXp > 0) data.addXP(SkillType.MINING, serverPlayer, miningXp);

            int woodcuttingXp = WoodcuttingSkillManager.getXpForBlock(brokenBlock);
            if (woodcuttingXp > 0) data.addXP(SkillType.WOODCUTTING, serverPlayer, woodcuttingXp);

            int excavationXp = ExcavationSkillManager.getXpForBlock(brokenBlock);
            if (excavationXp > 0) data.addXP(SkillType.EXCAVATION, serverPlayer, excavationXp);

            if (brokenBlock instanceof CropBlock crop && crop.isMature(state)) {
                int farmingXp = FarmingSkillManager.getXpForBlock(brokenBlock);
                if (farmingXp > 0) data.addXP(SkillType.FARMING, serverPlayer, farmingXp);
            }
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;

            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);
            ItemStack stack = player.getStackInHand(hand);

            if (stack.getItem() instanceof HoeItem &&
                    (state.isOf(Blocks.DIRT) || state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.COARSE_DIRT))) {
                PlayerSkillData data = getSkillData(player.getUuid());
                data.addXP(SkillType.FARMING, serverPlayer, 1);
            }

            if (stack.getItem() instanceof SplashPotionItem) {
                handleAlchemyXp(serverPlayer, stack, 1, false, false); // default call
            }

            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer)) return ActionResult.PASS;

            ItemStack stack = player.getStackInHand(hand);
            Identifier itemId = Registries.ITEM.getId(stack.getItem());

            if (itemId.getNamespace().equals("minecraft") && (
                    itemId.getPath().contains("seeds") ||
                            itemId.getPath().contains("carrot") ||
                            itemId.getPath().contains("potato") ||
                            itemId.getPath().contains("beetroot") ||
                            itemId.getPath().contains("nether_wart")
            )) {
                PlayerSkillData data = getSkillData(player.getUuid());
                data.addXP(SkillType.FARMING, serverPlayer, 1);
            }

            return ActionResult.PASS;
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity) -> {
            if (!(entity instanceof ServerPlayerEntity player)) return;
            if (!(killedEntity instanceof LivingEntity livingTarget)) return;

            Item heldItem = player.getMainHandStack().getItem();

            boolean isMelee = heldItem instanceof SwordItem || heldItem instanceof AxeItem || heldItem.getTranslationKey().contains("mace");
            boolean isRanged = heldItem instanceof BowItem || heldItem instanceof CrossbowItem || heldItem instanceof TridentItem;

            if (isMelee) {
                MeleeSkillManager.handleMeleeXp(player, livingTarget);
            } else if (isRanged) {
                RangedSkillManager.handleRangedXp(player, livingTarget);
            }

            BeastmasterySkillManager.handlePassiveKillXp(player, livingTarget);
        });

        PlayerBreedAnimalsCallback.EVENT.register((player, parent1, parent2, child) -> {
            if (player instanceof ServerPlayerEntity serverPlayer && child instanceof LivingEntity living) {
                BeastmasterySkillManager.handleBreedXp(serverPlayer, living);
            }
            return ActionResult.PASS;
        });
    }

    public static void handleCookingXp(ServerPlayerEntity player, ItemStack stack, String sourceId) {
        PlayerSkillData data = getSkillData(player.getUuid());
        int xp = CookingSkillManager.getXpForCookedItem(stack.getItem(), sourceId);
        if (xp > 0) {
            data.addXP(SkillType.COOKING, player, xp);
            System.out.println("🍳 Cooking XP: " + xp + " from " + stack.getItem());
        }
    }


    public static void handleAlchemyXp(ServerPlayerEntity player, ItemStack stack, int amount, boolean isStrong, boolean isExtended) {
        PlayerSkillData data = getSkillData(player.getUuid());
        int baseXp = AlchemySkillManager.getXpForPotion(stack);
        int bonusXp = 0;
        if (isStrong) bonusXp += 100;
        if (isExtended) bonusXp += 100;

        int totalXp = (baseXp + bonusXp) * amount;

        if (totalXp > 0) {
            data.addXP(SkillType.ALCHEMY, player, totalXp);
            System.out.println("🧪 Alchemy XP: " + totalXp + " from " + stack.getItem());
        }
    }

    public static void handleAlchemyXp(ServerPlayerEntity player, ItemStack stack, int amount) {
        handleAlchemyXp(player, stack, amount, false, false);
    }

    public static void handleArchaeologyXp(ServerPlayerEntity player, ItemStack stack, int amount) {
        PlayerSkillData data = getSkillData(player.getUuid());
        int xp = ArchaeologySkillManager.getXpForArtifact(stack);
        if (xp > 0) {
            data.addXP(SkillType.ARCHAEOLOGY, player, xp * amount);
        }
    }

    public static void handleEnchantingXp(ServerPlayerEntity player, int xp) {
        PlayerSkillData data = getSkillData(player.getUuid());
        if (xp > 0) {
            data.addXP(SkillType.ENCHANTING, player, xp);
        }
    }

    public static int getCookingXpPreview(ItemStack stack) {
        return CookingSkillManager.getXpForCookedItem(stack.getItem(), "minecraft:campfire");
    }

    public static void handleSmithingSmeltXp(ServerPlayerEntity player, ItemStack stack, int amount) {
        PlayerSkillData data = getSkillData(player.getUuid());
        int xp = SmithingSkillManager.getXpForSmelting(stack) * amount;
        if (xp > 0) {
            data.addXP(SkillType.SMITHING, player, xp);
        }
    }

    public static void handleSmithingCraftXp(ServerPlayerEntity player, ItemStack resultStack, int amountCrafted) {
        PlayerSkillData data = getSkillData(player.getUuid());
        int xp = SmithingSkillManager.getXpForCrafting(resultStack) * amountCrafted;
        if (xp > 0) {
            data.addXP(SkillType.SMITHING, player, xp);
        }
    }

    public static void handleSmithingRepairXp(ServerPlayerEntity player, ItemStack base, ItemStack addition, ItemStack result) {
        PlayerSkillData data = getSkillData(player.getUuid());
        int xp = SmithingSkillManager.getXpForRepair(base, addition, result);
        if (xp > 0) {
            data.addXP(SkillType.SMITHING, player, xp);
        }
    }



    public static PlayerSkillData getSkillData(UUID playerId) {
        playerSkillDataMap.putIfAbsent(playerId, new PlayerSkillData());
        return playerSkillDataMap.get(playerId);
    }
}
