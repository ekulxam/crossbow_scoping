package survivalblock.crossbow_scoping.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingDataComponentTypes;

@Environment(EnvType.CLIENT)
public final class ScopeRenderer {

    private ScopeRenderer() {
    }

    public static void renderScopeOnCrossbow(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ItemRenderer itemRenderer) {
        if (!(stack.getItem() instanceof CrossbowItem)) {
            return;
        }
        ItemStack otherStack = stack.getOrDefault(CrossbowScopingDataComponentTypes.CROSSBOW_SCOPE, ItemStack.EMPTY);
        if (otherStack == null || otherStack.isEmpty()) {
            return;
        }
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-135));
        matrices.translate(-0.707, 0.1, 0.6);
        itemRenderer.renderItem(otherStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, light, overlay, matrices, vertexConsumers, MinecraftClient.getInstance().world, 0);
        matrices.pop();
    }
}
