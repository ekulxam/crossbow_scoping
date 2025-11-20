package survivalblock.crossbow_scoping.mixin.crossbow.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
    private ItemDisplayContext displayContext;

    @Shadow
    public abstract boolean isEmpty();

    @Inject(method = "render", at = @At("RETURN"))
    private void renderScope(PoseStack matrices, MultiBufferSource bufferSource, int packedLight, int packedOverlay, CallbackInfo ci) {
        if (this.isEmpty()) {
            return;
        }

        ItemStack scope = this.getDataOrDefault(ScopeRenderer.SCOPE, ItemStack.EMPTY);

        if (scope.isEmpty()) {
            return;
        }

        matrices.pushPose();
        ItemStackRenderState.LayerRenderState layerRenderState = this.firstLayer();
        ItemTransform transformation = ((ItemRenderStateAccessor.LayerRenderStateAccessor) layerRenderState).crossbow_posing$getTransform();
        if (transformation == ItemTransform.NO_TRANSFORM) {
            matrices.popPose();
            return;
        }

        transformation = new ItemTransform(transformation.rotation(), transformation.translation().mul(1, new Vector3f()), transformation.scale());
        boolean leftHand = this.displayContext.leftHand();
        transformation.apply(leftHand, matrices.last());
        matrices.translate(0.5F, 0.5F, 0.5F);
        //ScopeRenderer.renderScopeOnCrossbow(scope, matrices, bufferSource, packedLight, packedOverlay, Minecraft.getInstance().getItemRenderer());
        matrices.popPose();
    }
}
