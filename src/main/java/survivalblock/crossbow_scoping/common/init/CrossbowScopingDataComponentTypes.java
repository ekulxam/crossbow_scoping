package survivalblock.crossbow_scoping.common.init;

//? if <1.21.11 {
import com.mojang.serialization.Codec;
//?} else {
/*import com.mojang.serialization.MapCodec;
*///?}
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import survivalblock.atmosphere.registrar.delayed.DelayedDataComponentTypeRegistrant;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public final class CrossbowScopingDataComponentTypes {
    private CrossbowScopingDataComponentTypes() {
    }

    private static final DelayedDataComponentTypeRegistrant REGISTRANT = new DelayedDataComponentTypeRegistrant(CrossbowScoping.MOD_ID);

    public static final DataComponentType<ItemStack> CROSSBOW_SCOPE = REGISTRANT.create("crossbow_scope",
            builder ->
                    builder.persistent(ItemStack.OPTIONAL_CODEC)
                            .networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC)
    );
    public static final DataComponentType<Unit> LOADING_PHASE = REGISTRANT.create("loading_phase",
            builder ->
                    builder.persistent(/*? <1.21.11 {*/ Codec.unit /*?} else {*/ /*MapCodec.unitCodec *//*?}*/(Unit.INSTANCE))
                            .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
    );

    public static void init() {
        REGISTRANT.consumeAll();
    }
}
