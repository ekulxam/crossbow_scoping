//? if >1.21.1 {
package survivalblock.crossbow_scoping.mixin.crossbow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("UnusedMixin")
@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerInteractionManagerMixin {

    @WrapOperation(method = "useItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack offhandWoes(ServerPlayer instance, InteractionHand interactionHand, Operation<ItemStack> original) {
        instance.crossbow_scoping$setAttacking(true);
        ItemStack stack = original.call(instance, interactionHand);
        instance.crossbow_scoping$setAttacking(false);
        return stack;
    }
}
//?}