//? if >1.21.1 {
package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("UnusedMixin")
@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    // lambda useItem
    @WrapOperation(method = {"method_41929", "method_62152"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack offhandWoes(Player instance, InteractionHand interactionHand, Operation<ItemStack> original) {
        instance.crossbow_scoping$setAttacking(true);
        ItemStack stack = original.call(instance, interactionHand);
        instance.crossbow_scoping$setAttacking(false);
        return stack;
    }
}
//?}