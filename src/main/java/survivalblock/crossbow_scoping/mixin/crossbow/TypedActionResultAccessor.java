package survivalblock.crossbow_scoping.mixin.crossbow;

import net.minecraft.world.InteractionResultHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InteractionResultHolder.class)
public interface TypedActionResultAccessor<T> {

    @Mutable
    @Accessor("object")
    void crossbow_scoping$setValue(T value);
}
