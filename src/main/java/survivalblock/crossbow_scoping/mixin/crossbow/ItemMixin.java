package survivalblock.crossbow_scoping.mixin.crossbow;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("CancellableInjectionUsage")
@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
    protected void insertOrExtractSpyglass(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {

    }

    @Inject(method = "canMine", at = @At("HEAD"), cancellable = true)
    protected void preventMining(BlockState state, World world, BlockPos pos, PlayerEntity miner, CallbackInfoReturnable<Boolean> cir) {

    }
}
