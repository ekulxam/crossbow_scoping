package survivalblock.crossbow_scoping.mixin.compat.apoli;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE;

@Mixin(value = ItemStack.class, priority = 1500)
public class ItemStackMixinMixin {

    @TargetHandler(
            mixin = "io.github.apace100.apoli.mixin.ItemStackMixin",
            name = "apoli$onItemUse"
    )
    @ModifyExpressionValue(
            method = "@MixinSquared:Handler",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/inventory/StackReference;of(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/inventory/StackReference;"
            )
    )
    private StackReference whyDoINeedANewStackReference(StackReference original, Item item, World world, PlayerEntity user, Hand hand, Operation<TypedActionResult<ItemStack>> operation) {
        if (!user.getStackInHand(hand).getOrDefault(CROSSBOW_SCOPE, ItemStack.EMPTY).isEmpty()) {
            return new StackReference() {
                @Override
                public ItemStack get() {
                    return original.get();
                }

                @Override
                public boolean set(ItemStack stack) {
                    return false;
                }
            };
        }
        return original;
    }
}
