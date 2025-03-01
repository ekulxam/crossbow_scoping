package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

import java.util.List;

import static survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE;

@Mixin(value = CrossbowItem.class, priority = 1500)
public class CrossbowItemMixin extends ItemMixin {

    @Override
    protected void insertOrExtractSpyglass(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (!ClickType.RIGHT.equals(clickType)) {
            return;
        }
        if (!otherStack.isEmpty() && !(otherStack.getItem() instanceof SpyglassItem)) {
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
        ItemStack stack = CrossbowScoping.getCrossbowWithScope(miner).getFirst();
        if (stack.isEmpty()) {
            return;
        }
        cir.setReturnValue(false);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;shootAll(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;FFLnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void scopeInsteadOfShooting(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local ItemStack stack) {
        if (user.crossbow_scoping$isAttacking()) {
            user.crossbow_scoping$setAttacking(false);
            return;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            cir.setReturnValue(stackInComponents.use(world, user, hand));
        }
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendScopeInTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, CallbackInfo ci) {
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty()) {
            Text text = stackInComponents.getName();
            tooltip.add(Text.translatable("item.crossbow_scoping.crossbow.scope", text).setStyle(text.getStyle()));
        }
    }

    @ModifyReturnValue(method = "getMaxUseTime", at = @At("RETURN"))
    private int spyglassUseItem(int original, ItemStack stack, LivingEntity user) {
        if (!CrossbowScoping.isLoaded(stack)) {
            return original;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (stackInComponents.isEmpty()) {
            return original;
        }
        return stackInComponents.getMaxUseTime(user);
    }

    @ModifyReturnValue(method = "getUseAction", at = @At(value = "RETURN"))
    private UseAction spyglassZoom(UseAction original, ItemStack stack) {
        if (!CrossbowScoping.isLoaded(stack)) {
            return original;
        }
        ItemStack stackInComponents = stack.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (stackInComponents.isEmpty()) {
            return original;
        }
        return stackInComponents.getUseAction();
    }
}
