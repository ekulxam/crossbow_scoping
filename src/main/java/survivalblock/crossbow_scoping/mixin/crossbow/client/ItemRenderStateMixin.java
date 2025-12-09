//? if >1.21.1 {
package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
//? if >1.21.8
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import survivalblock.crossbow_scoping.client.ScopeRenderer;

@SuppressWarnings({"NonExtendableApiUsage", "UnusedMixin"})
@Mixin(ItemStackRenderState.class)
public abstract class ItemRenderStateMixin implements FabricRenderState {

    @Shadow
    protected abstract ItemStackRenderState.LayerRenderState firstLayer();

    @Shadow
    ItemDisplayContext displayContext;

    @Shadow
    public abstract boolean isEmpty();

    @Inject(method = /*? <=1.21.8 {*/ /*"render" *//*?} else {*/ "submit" /*?}*/, at = @At("RETURN"))
    private void renderScope(PoseStack matrices, /*? <=1.21.8 {*/ /*MultiBufferSource vertexConsumers *//*?} else {*/ SubmitNodeCollector renderQueue /*?}*/, int light, int overlay, /*? >1.21.8 {*/ int outlineColor, /*?}*/ CallbackInfo ci) {
        if (this.isEmpty()) {
            //? if >=1.21.9
            return;
        }

        ItemStack scope = this.getDataOrDefault(ScopeRenderer.SCOPE, ItemStack.EMPTY);

        if (scope.isEmpty()) {
            //? if >=1.21.9
            return;
        }

        matrices.pushPose();
        this.crossbow_scoping$applyScopeTransforms(matrices);
        ScopeRenderer.renderScopeOnCrossbow(scope, matrices, /*? <=1.21.8 {*/ /*vertexConsumers *//*?} else {*/ renderQueue /*?}*/, light, overlay, Minecraft.getInstance().getItemRenderer() /*? >1.21.8 {*/, outlineColor /*?}*/);
        matrices.popPose();
    }

    @Unique
    private void crossbow_scoping$applyScopeTransforms(PoseStack matrices) {
        ItemStackRenderState.LayerRenderState layerRenderState = this.firstLayer();
        ItemTransform transformation = ((ItemRenderStateAccessor.LayerRenderStateAccessor) layerRenderState).crossbow_posing$getTransform();
        if (transformation == ItemTransform.NO_TRANSFORM) {
            return;
        }

        transformation = new ItemTransform(transformation.rotation(), transformation.translation().mul(1, new Vector3f()), transformation.scale());
        boolean leftHand = this.displayContext.leftHand();
        transformation.apply(leftHand, matrices.last());
        matrices.translate(0.5F, 0.5F, 0.5F);
    }
}
//?}