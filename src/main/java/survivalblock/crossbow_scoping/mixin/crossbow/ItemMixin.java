package survivalblock.crossbow_scoping.mixin.crossbow;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
//? if >1.21.1
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@SuppressWarnings("CancellableInjectionUsage")
@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    protected void insertOrExtractSpyglass(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference, CallbackInfoReturnable<Boolean> cir) {

    }

    //? if <=1.21.1 {
    /*@Inject(method = "canAttackBlock", at = @At("HEAD"), cancellable = true)
    protected void preventMining(BlockState state, Level world, BlockPos pos, Player miner, CallbackInfoReturnable<Boolean> cir) {

    }
    *///?} else {
    @Inject(method = "canDestroyBlock", at = @At("HEAD"), cancellable = true)
    protected void preventMining(ItemStack stack, BlockState state, Level level, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {

    }

    @Inject(method = "appendHoverText", at = @At("HEAD"))
    protected void appendScopeInTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag, CallbackInfo ci) {
    }
    //?}
}
