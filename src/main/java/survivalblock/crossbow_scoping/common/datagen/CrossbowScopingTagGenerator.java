package survivalblock.crossbow_scoping.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

    public CrossbowScopingTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        FabricTagBuilder allowNoGravityBuilder = getOrCreateTagBuilder(CrossbowScopingTags.ALLOW_NO_GRAVITY);
        allowNoGravityBuilder.add(EntityType.ARROW);
        allowNoGravityBuilder.add(EntityType.SPECTRAL_ARROW);
        allowNoGravityBuilder.add(EntityType.FIREWORK_ROCKET);
    }
}
