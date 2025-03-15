package survivalblock.crossbow_scoping.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingDataComponentTypes {

    public static final ComponentType<ItemStack> CROSSBOW_SCOPE = ComponentType.<ItemStack>builder().codec(ItemStack.OPTIONAL_CODEC).packetCodec(ItemStack.OPTIONAL_PACKET_CODEC).build();
    public static final ComponentType<Unit> LOADING_PHASE = ComponentType.<Unit>builder().codec(Codec.unit(Unit.INSTANCE)).packetCodec(PacketCodec.unit(Unit.INSTANCE)).build();

    public static void init() {
        Registry.register(Registries.DATA_COMPONENT_TYPE, CrossbowScoping.id("crossbow_scope"), CROSSBOW_SCOPE);
        Registry.register(Registries.DATA_COMPONENT_TYPE, CrossbowScoping.id("loading_phase"), LOADING_PHASE);
    }
}
