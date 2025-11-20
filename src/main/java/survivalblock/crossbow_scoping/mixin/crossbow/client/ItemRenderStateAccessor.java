//? if >1.21.1 {
package survivalblock.crossbow_scoping.mixin.crossbow.client;

import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("UnusedMixin")
@Mixin(ItemStackRenderState.class)
public interface ItemRenderStateAccessor {

    @Mixin(ItemStackRenderState.LayerRenderState.class)
    interface LayerRenderStateAccessor {

        @Accessor("transform")
        ItemTransform crossbow_posing$getTransform();
    }
}
//?}