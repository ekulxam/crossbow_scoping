package survivalblock.crossbow_scoping.common.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingDataComponentTypes {

    public static final DataComponentType<ItemStack> CROSSBOW_SCOPE = DataComponentType.<ItemStack>builder().persistent(ItemStack.OPTIONAL_CODEC).networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC).build();
    public static final DataComponentType<Unit> LOADING_PHASE = DataComponentType.<Unit>builder().persistent(/*? <1.21.11 {*/ Codec.unit /*?} else {*/ /*MapCodec.unitCodec *//*?}*/(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)).build();

    public static void init() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CrossbowScoping.id("crossbow_scope"), CROSSBOW_SCOPE);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CrossbowScoping.id("loading_phase"), LOADING_PHASE);
    }
}
