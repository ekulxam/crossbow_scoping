package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public abstract class PlayerHeldItemFeatureRendererMixin<T extends PlayerEntity, M extends EntityModel<T> & ModelWithArms & ModelWithHead>
        extends HeldItemFeatureRenderer<T, M> {

    public PlayerHeldItemFeatureRendererMixin(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        super(context, heldItemRenderer);
    }

    @ModifyVariable(method = "renderSpyglass", at = @At("HEAD"), index = 2, argsOnly = true)
    private ItemStack renderCrossbowToo(ItemStack scope, @Local(argsOnly = true)LivingEntity living, @Share("crossbow")LocalBooleanRef localBooleanRef) {
        localBooleanRef.set(false);
        if (!(living instanceof PlayerEntity player)) {
            return scope;
        }
        if (scope.isEmpty()) {
            return scope;
        }
        player.crossbow_scoping$setAttacking(true);
        ItemStack crossbow = player.getStackInHand(player.getActiveHand());
        player.crossbow_scoping$setAttacking(false);
        if (ItemStack.areItemsAndComponentsEqual(scope, crossbow.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY))) {
            localBooleanRef.set(true);
            return crossbow;
        }
        return scope;
    }

    @WrapOperation(method = "renderSpyglass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void fixRenderMode(HeldItemRenderer instance, LivingEntity entity, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original, @Local(argsOnly = true) Arm arm, @Share("crossbow") LocalBooleanRef localBooleanRef) {
        if (localBooleanRef.get()) {
            if (arm == Arm.RIGHT) {
                renderMode = ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
            } else {
                renderMode = ModelTransformationMode.THIRD_PERSON_LEFT_HAND;
            }
        }
        original.call(instance, entity, stack, renderMode, leftHanded, matrices, vertexConsumers, light);
    }

    @Inject(method = "renderSpyglass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", shift = At.Shift.AFTER))
    private void fixCrossbowTransforms(LivingEntity entity, ItemStack stack, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci, @Share("crossbow") LocalBooleanRef localBooleanRef) {
        // 1.6 / 0.9 = 1.7777...
        if (localBooleanRef.get()) {
            matrices.scale(1.778f, 1.778f, 1.778f);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(15));
            if (arm == Arm.LEFT) {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
            }
            matrices.translate(0, -0.1, 1);
        }
    }
}
