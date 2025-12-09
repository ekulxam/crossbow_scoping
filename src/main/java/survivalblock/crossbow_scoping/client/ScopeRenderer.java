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
//? if >1.21.8
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemRenderer;
//? if >1.21.1
import net.minecraft.client.renderer.item.ItemStackRenderState;
//? if >1.21.8
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import survivalblock.crossbow_scoping.common.CrossbowScoping;
//? if >1.21.1 && <1.21.9
/*import survivalblock.crossbow_scoping.mixin.crossbow.client.ItemRendererAccessor;*/

@Environment(EnvType.CLIENT)
public final class ScopeRenderer {

    //? if >1.21.1 {
    public static final RenderStateDataKey<ItemStack> SCOPE = RenderStateDataKey.create(CrossbowScoping.id("scope")::toString);
    public static final RenderStateDataKey<ItemStack> REVERSE_REFERENCE = RenderStateDataKey.create(CrossbowScoping.id("reverse_reference")::toString);
    //?}

    //? if <1.21.9
    /*private static final ItemStackRenderState SCOPE_RENDER_STATE = new ItemStackRenderState();*/

    //? if >=1.21.10
    public static final RenderStateDataKey<ItemStackRenderState> CROSSBOW_TO_HEAD = RenderStateDataKey.create(CrossbowScoping.id("crossbow_to_head")::toString);

    private ScopeRenderer() {
    }

    public static void renderScopeOnCrossbow(ItemStack /*? <=1.21.1 {*/ /*stack *//*?} else {*/ scope /*?}*/, PoseStack matrices, /*? <=1.21.8 {*/ /*MultiBufferSource vertexConsumers *//*?} else {*/ SubmitNodeCollector renderQueue /*?}*/, int light, int overlay, ItemRenderer itemRenderer /*? >1.21.8 {*/, int outlineColor /*?}*/) {
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
        //? if >1.21.8
        final ItemStackRenderState SCOPE_RENDER_STATE = new ItemStackRenderState();
        /*? <=1.21.8 {*/ /*((ItemRendererAccessor) itemRenderer).crossbow_scoping$getItemModelManager() *//*?} else {*/ Minecraft.getInstance().getItemModelResolver() /*?}*/
                .updateForTopItem(SCOPE_RENDER_STATE, scope, displayContext, world, null, 0);
        SCOPE_RENDER_STATE.setAnimated();
        SCOPE_RENDER_STATE.appendModelIdentityElement("crossbow_scoping:scope");
        SCOPE_RENDER_STATE./*? <=1.21.8 {*/ /*render *//*?} else {*/ submit /*?}*/(matrices, /*? <=1.21.8 {*/ /*vertexConsumers *//*?} else {*/ renderQueue /*?}*/, light, overlay /*? >1.21.8 {*/, outlineColor /*?}*/);
        //? if <1.21.9
        /*SCOPE_RENDER_STATE.clear();*/
        //?}
        matrices.popPose();
    }
}
