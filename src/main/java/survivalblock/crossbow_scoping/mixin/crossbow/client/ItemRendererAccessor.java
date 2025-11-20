//? if >1.21.1 {
package survivalblock.crossbow_scoping.mixin.crossbow.client;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("UnusedMixin")
@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {

    @Accessor("resolver")
    ItemModelResolver crossbow_scoping$getItemModelManager();
}
//?}