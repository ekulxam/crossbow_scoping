package survivalblock.crossbow_scoping.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingTagGenerator {

    public static class CrossbowScopingEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

        public CrossbowScopingEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            /*? <=1.21.1 {*/ /*getOrCreateTagBuilder *//*?} else {*/ valueLookupBuilder /*?}*/(CrossbowScopingTags.ALLOW_NO_GRAVITY)
                    .add(EntityType.ARROW, EntityType.SPECTRAL_ARROW, EntityType.FIREWORK_ROCKET);
        }
    }

    public static class CrossbowScopingEnchantmentTagGenerator extends FabricTagProvider/*? <=1.21.1 {*/ /*.EnchantmentTagProvider *//*?} else {*/ <Enchantment> /*?}*/ {

        private static final String OMNICROSSBOW = "omnicrossbow";

        public CrossbowScopingEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, /*? >1.21.1 {*/ Registries.ENCHANTMENT, /*?}*/ completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            /*? <=1.21.1 {*/ /*getOrCreateTagBuilder *//*?} else {*/ getOrCreateRawBuilder /*?}*/(CrossbowScopingTags.USES_EXTENDED_COOLDOWN)
                    ./*? <=1.21.1 {*/ /*addOptional *//*?} else {*/ addOptionalElement /*?}*/(ResourceLocation.fromNamespaceAndPath(OMNICROSSBOW, "multichambered"));
        }
    }

    public static class CrossbowScopingItemTagGenerator extends FabricTagProvider/*? <=1.21.1 {*/ /*.ItemTagProvider *//*?} else {*/ <Item> /*?}*/ {

        private static final String PIERCED = "pierced";

        public CrossbowScopingItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, /*? >1.21.1 {*/ Registries.ITEM, /*?}*/ completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            /*? <=1.21.1 {*/ /*getOrCreateTagBuilder *//*?} else {*/ getOrCreateRawBuilder /*?}*/(CrossbowScopingTags.INCOMPATIBLE_ITEMS)
                    ./*? <=1.21.1 {*/ /*addOptional *//*?} else {*/ addOptionalElement /*?}*/(ResourceLocation.fromNamespaceAndPath(PIERCED, "long_crossbow"));
        }
    }
}
