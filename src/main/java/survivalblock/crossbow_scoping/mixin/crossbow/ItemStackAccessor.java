//? if >=1.21.4 {
/*package survivalblock.crossbow_scoping.mixin.crossbow;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@SuppressWarnings("UnusedMixin")
@Mixin(ItemStack.class)
public interface ItemStackAccessor {
    @Accessor("OP_NBT_WARNING")
    static List<Component> crossbow_scoping$getOpNbtWarning() {
        throw new UnsupportedOperationException();
    }
}
*///?}