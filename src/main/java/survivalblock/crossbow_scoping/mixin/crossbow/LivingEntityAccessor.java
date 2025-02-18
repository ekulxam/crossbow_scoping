package survivalblock.crossbow_scoping.mixin.crossbow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Accessor("activeItemStack")
    void crossbow_scoping$setActiveItem(ItemStack stack);
}
