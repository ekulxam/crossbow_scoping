package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.crossbow_scoping.client.networking.ScopedCrossbowC2SPayload;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Shadow @Final public Options options;

    @Shadow @Nullable public Screen screen;

    @Shadow @Final public MouseHandler mouseHandler;

    @Shadow @Nullable public LocalPlayer player;

    @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;continueAttack(Z)V"))
    private void onScopedCrossbowAttack(Minecraft instance, boolean breaking, Operation<Void> original) {
        if (!this.options.keyAttack.isDown() || this.screen != null || !this.mouseHandler.isMouseGrabbed()) {
            original.call(instance, breaking);
            return;
        }
        if (this.player == null) {
            original.call(instance, breaking);
            return;
        }
        this.player.crossbow_scoping$setAttacking(true);
        Pair<ItemStack, InteractionHand> pair = CrossbowScoping.getCrossbowWithScope(this.player, true, true);
        this.player.crossbow_scoping$setAttacking(false);
        ItemStack stack = pair.getFirst();
        if (stack.isEmpty()) {
            original.call(instance, breaking);
            return;
        }
        ClientPlayNetworking.send(ScopedCrossbowC2SPayload.fromHand(pair.getSecond()));
        original.call(instance, false);
    }

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void cancelAttackWithCrossbow(CallbackInfoReturnable<Boolean> cir) {
        if (this.player == null) {
            return;
        }
        Pair<ItemStack, InteractionHand> pair = CrossbowScoping.getCrossbowWithScope(this.player, true, true);
        if (pair.getFirst().isEmpty()) {
            return;
        }
        cir.setReturnValue(false);
    }
}
