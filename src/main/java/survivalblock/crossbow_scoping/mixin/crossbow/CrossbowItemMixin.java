package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpyglassItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    protected void insertOrExtractSpyglass(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (!ClickType.RIGHT.equals(clickType)) {
            return;
        }
        if (!CrossbowScoping.canInsertSpyglass(stack, otherStack)) {
            return;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (ItemStack.areEqual(stackInComponents, otherStack)) {
            return;
        }
        stack.set(CROSSBOW_SCOPE, otherStack.copy());
        cursorStackReference.set(stackInComponents.copy());
        cir.setReturnValue(true);
    }

    @Override
    protected void preventMining(BlockState state, World world, BlockPos pos, PlayerEntity miner, CallbackInfoReturnable<Boolean> cir) {
        miner.crossbow_scoping$setAttacking(true);
        ItemStack stack = CrossbowScoping.getCrossbowWithScope(miner).getFirst();
        miner.crossbow_scoping$setAttacking(false);
        if (stack.isEmpty()) {
            return;
        }
        cir.setReturnValue(false);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;shootAll(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FFLnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void scopeInsteadOfShooting(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack stack) {
        if (stack.contains(LOADING_PHASE)) {
            stack.remove(LOADING_PHASE);
        }
        if (user.crossbow_scoping$isAttacking()) {
            user.crossbow_scoping$setAttacking(false);
            if (user.isUsingItem()) {
                user.stopUsingItem();
            }
            if (EnchantmentHelper.hasAnyEnchantmentsIn(stack, CrossbowScopingTags.USES_EXTENDED_COOLDOWN)) {
                user.getItemCooldownManager().set(stack.getItem(), 11);
            } else {
                user.getItemCooldownManager().set(stack.getItem(), 5);
            }
            return;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            user.crossbow_scoping$setStartingToScope(stackInComponents);
            TypedActionResult<ItemStack> result = stackInComponents.use(world, user, hand);
            user.crossbow_scoping$setStartingToScope(ItemStack.EMPTY);
            ItemStack value = result.getValue();
            if (!ItemStack.areEqual(stackInComponents, value)) {
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

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setCurrentHand(Lnet/minecraft/util/Hand;)V"))
    private void setWasLoading(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack stack) {
        stack.set(LOADING_PHASE, Unit.INSTANCE);
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendScopeInTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty()) {
            Text text = stackInComponents.getName();
            tooltip.add(Text.translatable("item.crossbow_scoping.crossbow.scope", text).setStyle(text.getStyle()));
        }
    }
}
