package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.List;

import static survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE;
import static survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes.LOADING_PHASE;

@Mixin(value = CrossbowItem.class, priority = 1500)
public class CrossbowItemMixin extends ItemMixin {

    @Override
    protected void insertOrExtractSpyglass(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (!ClickAction.SECONDARY.equals(clickType)) {
            return;
        }
        if (!CrossbowScoping.canInsertSpyglass(stack, otherStack)) {
            return;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (ItemStack.matches(stackInComponents, otherStack)) {
            return;
        }
        stack.set(CROSSBOW_SCOPE, otherStack.copy());
        cursorStackReference.set(stackInComponents.copy());
        cir.setReturnValue(true);
    }

    @Override
    protected void preventMining(BlockState state, Level world, BlockPos pos, Player miner, CallbackInfoReturnable<Boolean> cir) {
        miner.crossbow_scoping$setAttacking(true);
        ItemStack stack = CrossbowScoping.getCrossbowWithScope(miner).getFirst();
        miner.crossbow_scoping$setAttacking(false);
        if (stack.isEmpty()) {
            return;
        }
        cir.setReturnValue(false);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CrossbowItem;performShooting(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/item/ItemStack;FFLnet/minecraft/world/entity/LivingEntity;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void scopeInsteadOfShooting(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local ItemStack stack) {
        if (stack.has(LOADING_PHASE)) {
            stack.remove(LOADING_PHASE);
        }
        if (user.crossbow_scoping$isAttacking()) {
            user.crossbow_scoping$setAttacking(false);
            if (user.isUsingItem()) {
                user.releaseUsingItem();
            }
            if (EnchantmentHelper.hasTag(stack, CrossbowScopingTags.USES_EXTENDED_COOLDOWN)) {
                user.getCooldowns().addCooldown(stack.getItem(), 11);
            } else {
                user.getCooldowns().addCooldown(stack.getItem(), 5);
            }
            return;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            user.crossbow_scoping$setStartingToScope(stackInComponents);
            InteractionResultHolder<ItemStack> result = stackInComponents.use(world, user, hand);
            user.crossbow_scoping$setStartingToScope(ItemStack.EMPTY);
            ItemStack value = result.getObject();
            if (!ItemStack.matches(stackInComponents, value)) {
                if (value.isEmpty()) {
                    stack.remove(CROSSBOW_SCOPE);
                } else {
                    stack.set(CROSSBOW_SCOPE, value);
                }
            }
            //noinspection unchecked
            ((TypedActionResultAccessor<ItemStack>) result).crossbow_scoping$setValue(stack);
            cir.setReturnValue(result);
        }
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startUsingItem(Lnet/minecraft/world/InteractionHand;)V"))
    private void setWasLoading(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir, @Local ItemStack stack) {
        stack.set(LOADING_PHASE, Unit.INSTANCE);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    private void appendScopeInTooltip(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type, CallbackInfo ci) {
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty()) {
            Component text = stackInComponents.getHoverName();
            tooltip.add(Component.translatable("item.crossbow_scoping.crossbow.scope", text).setStyle(text.getStyle()));
        }
    }
}
