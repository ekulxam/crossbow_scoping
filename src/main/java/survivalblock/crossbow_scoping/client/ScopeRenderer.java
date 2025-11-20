package survivalblock.crossbow_scoping.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//? if >1.21.1
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
//? if >1.21.1
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;
//? if >1.21.1
import survivalblock.crossbow_scoping.mixin.crossbow.client.ItemRendererAccessor;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public final class ScopeRenderer {

    //? if >1.21.1 {
    public static final RenderStateDataKey<ItemStack> SCOPE = RenderStateDataKey.create(CrossbowScoping.id("scope")::toString);
    public static final RenderStateDataKey<ItemStack> REVERSE_REFERENCE = RenderStateDataKey.create(CrossbowScoping.id("reverse_reference")::toString);

    private static final ItemStackRenderState SCOPE_RENDER_STATE = new ItemStackRenderState();
    //?}

    private ScopeRenderer() {
    }

    public static void renderScopeOnCrossbow(ItemStack /*? <=1.21.1 {*/ /*stack *//*?} else {*/ scope /*?}*/, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, ItemRenderer itemRenderer, Object... data) {
        //? if <=1.21.1 {
        /*if (!(CrossbowScoping.isValidCrossbow(stack))) {
            return;
        }
        ItemStack scope = stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (scope == null || scope.isEmpty()) {
            return;
        }
        *///?}
        matrices.pushPose();
        matrices.mulPose(Axis.ZP.rotationDegrees(-135));
        final ItemDisplayContext displayContext = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        ClientLevel world = Minecraft.getInstance().level;
        //? if <=1.21.1 {
        /*matrices.translate(-0.707, 0.1, 0.6);
        itemRenderer.renderStatic(scope, displayContext, light, overlay, matrices, vertexConsumers, world, 0);
        *///?} else {
        matrices.translate(0, 0.1, 0.1);
        ((ItemRendererAccessor) itemRenderer).crossbow_scoping$getItemModelManager().updateForTopItem(SCOPE_RENDER_STATE, scope, displayContext, world, null, 0);
        SCOPE_RENDER_STATE.setAnimated();
        SCOPE_RENDER_STATE.appendModelIdentityElement("crossbow_scoping:scope");
        SCOPE_RENDER_STATE.render(matrices, vertexConsumers, light, overlay);
        SCOPE_RENDER_STATE.clear();
        //?}
        matrices.popPose();
    }
}
