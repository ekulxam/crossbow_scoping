package survivalblock.crossbow_scoping.mixin.compat.apoli;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Cancellable;
import io.github.apace100.apoli.util.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiPredicate;

import static survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE;

@Mixin(value = InventoryUtil.class, remap = false)
public class InventoryUtilMixin {

    @ModifyExpressionValue(method = "getStackReferenceFromStack(Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;Ljava/util/function/BiPredicate;)Lnet/minecraft/inventory/StackReference;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getStackReference(I)Lnet/minecraft/inventory/StackReference;"), remap = true)
    private static StackReference getScopeInstead(StackReference original, Entity entity, ItemStack stack, BiPredicate<ItemStack, ItemStack> equalityPredicate, @Cancellable CallbackInfoReturnable<StackReference> cir) {
        if (original == StackReference.EMPTY) {
            return original;
        }
        ItemStack crossbow = original.get();
        if (equalityPredicate.test(stack, crossbow.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY))) {
            return new StackReference() {
                @Override
                public ItemStack get() {
                    return crossbow.getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY);
                }

                @Override
                public boolean set(ItemStack stack) {
                    crossbow.set(CROSSBOW_SCOPE, stack);
                    return true;
                }
            };
        }
        return original;
    }
}
