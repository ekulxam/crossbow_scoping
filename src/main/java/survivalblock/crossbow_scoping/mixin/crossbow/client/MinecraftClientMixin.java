package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import survivalblock.crossbow_scoping.client.networking.ScopedCrossbowC2SPayload;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Final public GameOptions options;

    @Shadow @Nullable public Screen currentScreen;

    @Shadow @Final public Mouse mouse;

    @Shadow @Nullable public ClientPlayerEntity player;

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V"))
    private void onScopedCrossbowAttack(MinecraftClient instance, boolean breaking, Operation<Void> original) {
        if (!this.options.attackKey.isPressed() || this.currentScreen != null || !this.mouse.isCursorLocked()) {
            original.call(instance, breaking);
            return;
        }
        if (this.player == null) {
            original.call(instance, breaking);
            return;
        }
        Pair<ItemStack, Hand> pair = CrossbowScoping.getCrossbowWithScope(this.player, true, true);
        ItemStack stack = pair.getFirst();
        if (stack.isEmpty()) {
            original.call(instance, breaking);
            return;
        }
        ClientPlayNetworking.send(ScopedCrossbowC2SPayload.fromHand(pair.getSecond()));
        original.call(instance, false);
    }

    @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
    private void cancelAttackWithCrossbow(CallbackInfoReturnable<Boolean> cir) {
        if (this.player == null) {
            return;
        }
        Pair<ItemStack, Hand> pair = CrossbowScoping.getCrossbowWithScope(this.player, true, true);
        if (pair.getFirst().isEmpty()) {
            return;
        }
        cir.setReturnValue(false);
    }
}
