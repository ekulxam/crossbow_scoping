package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Mixin(value = LivingEntity.class, priority = 10000)
public abstract class LivingEntityMixin {

    @Shadow
    public abstract ItemStack getUseItem();

    @ModifyExpressionValue(method = {"updatingUsingItem", "completeUsingItem"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    protected ItemStack replaceWithScope(ItemStack original) {
        if (!(original.getItem() instanceof CrossbowItem)) {
            return original;
        }
        if (!CrossbowScoping.isLoaded(original, true)) {
            return original;
        }
        ItemStack stackInComponents = original.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (!stackInComponents.isEmpty() && stackInComponents.getItem() instanceof SpyglassItem) {
            return stackInComponents;
        }
        return original;
    }

    //? if >1.21.1 {
    @ModifyReturnValue(method = "getItemBySlot", at = @At("RETURN"))
    protected ItemStack returnSpyglassStack(ItemStack original) {
        return original;
    }
    //?}

    @WrapMethod(method = "swapHandItems")
    protected void swapCorrectly(Operation<Void> original) {
        original.call();
    }
}
