package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;

@Mixin(PlayerItemInHandLayer.class)
public abstract class PlayerHeldItemFeatureRendererMixin<T extends /*? <=1.21.1 {*/ /*Player *//*?} else {*/ PlayerRenderState /*?}*/, M extends EntityModel<T> & ArmedModel & HeadedModel>
        extends ItemInHandLayer<T, M> {

    //? if <=1.21.1 {
    /*public PlayerHeldItemFeatureRendererMixin(RenderLayerParent<T, M> context, ItemInHandRenderer heldItemRenderer) {
        super(context, heldItemRenderer);
    }

    @ModifyVariable(method = "renderArmWithSpyglass", at = @At("HEAD"), index = 2, argsOnly = true)
    private ItemStack renderCrossbowToo(ItemStack scope, @Local(argsOnly = true)LivingEntity living, @Share("crossbow")LocalBooleanRef localBooleanRef) {
        localBooleanRef.set(false);
        if (!(living instanceof Player player)) {
            return scope;
        }
        if (scope.isEmpty()) {
            return scope;
        }
        player.crossbow_scoping$setAttacking(true);
        ItemStack crossbow = player.getItemInHand(player.getUsedItemHand());
        player.crossbow_scoping$setAttacking(false);
        if (ItemStack.matches(scope, crossbow.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY))) {
            localBooleanRef.set(true);
            return crossbow;
        }
        return scope;
    }

    @WrapOperation(method = "renderArmWithSpyglass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void fixRenderMode(ItemInHandRenderer instance, LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, Operation<Void> original, @Local(argsOnly = true) HumanoidArm arm, @Share("crossbow") LocalBooleanRef localBooleanRef) {
        if (localBooleanRef.get()) {
            if (arm == HumanoidArm.RIGHT) {
                renderMode = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            } else {
                renderMode = ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
            }
        }
        original.call(instance, entity, stack, renderMode, leftHanded, matrices, vertexConsumers, light);
    }

    @Inject(method = "renderArmWithSpyglass", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER))
    private void fixCrossbowTransforms(LivingEntity entity, ItemStack stack, HumanoidArm arm, PoseStack matrices, MultiBufferSource vertexConsumers, int light, CallbackInfo ci, @Share("crossbow") LocalBooleanRef localBooleanRef) {
        // 1.6 / 0.9 = 1.7777...
        if (localBooleanRef.get()) {
            matrices.scale(1.778f, 1.778f, 1.778f);
            matrices.mulPose(Axis.YP.rotationDegrees(15));
            if (arm == HumanoidArm.LEFT) {
                matrices.mulPose(Axis.YP.rotationDegrees(-90));
            }
            matrices.translate(0, -0.1, 1);
        }
    }
    *///?} else {
    @Unique
    private final ItemStackRenderState crossbow_scoping$crossbowRenderState = new ItemStackRenderState();

    public PlayerHeldItemFeatureRendererMixin(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @WrapOperation(method = "renderArmWithItem(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/PlayerItemInHandLayer;renderItemHeldToEye(Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void changeTheRenderState(PlayerItemInHandLayer<T, M> instance, ItemStackRenderState itemStackRenderState, HumanoidArm arm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Operation<Void> original, @Local(argsOnly = true) PlayerRenderState playerRenderState) {
        ItemStack crossbow = itemStackRenderState.getDataOrDefault(ScopeRenderer.REVERSE_REFERENCE, ItemStack.EMPTY);

        if (crossbow.isEmpty()) {
            this.crossbow_scoping$crossbowRenderState.clear();
        } else {
            Minecraft client = Minecraft.getInstance();

            client.getItemModelResolver().updateForTopItem(
                    this.crossbow_scoping$crossbowRenderState,
                    crossbow,
                    arm == HumanoidArm.RIGHT ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    client.level,
                    null,
                    0
            );

            itemStackRenderState = this.crossbow_scoping$crossbowRenderState;
        }

        original.call(instance, itemStackRenderState, arm, poseStack, multiBufferSource, i);
    }

    @Inject(method = "renderItemHeldToEye", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER))
    private void fixCrossbowTransforms(ItemStackRenderState renderState, HumanoidArm arm, PoseStack matrices, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
        // 1.6 / 0.9 = 1.7777...
        if (renderState == this.crossbow_scoping$crossbowRenderState) {
            matrices.scale(1.778f, 1.778f, 1.778f);
            matrices.mulPose(Axis.YP.rotationDegrees(15));
            if (arm == HumanoidArm.LEFT) {
                matrices.mulPose(Axis.YP.rotationDegrees(-90));
            }
            //? if <=1.21.1 {
            matrices.translate(0, -0.1, 1);
            //?} else {
            matrices.translate(0.1, -0.1, 0);
            //?}
        }
    }
    //?}
}
