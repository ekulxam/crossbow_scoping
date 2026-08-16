package survivalblock.crossbow_scoping.common.datagen;

//~ if >=26 'FabricTagProvider' -> 'FabricTagsProvider' {
//~ if >=26 'EntityTypeTagProvider' -> 'EntityTypeTagsProvider' {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
//? if >=26.2 {
/*import net.minecraft.world.entity.EntityTypeIds;
*///?} else {
import net.minecraft.world.entity.EntityType;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import survivalblock.crossbow_scoping.common.init.CrossbowScopingTags;

import java.util.concurrent.CompletableFuture;

public class CrossbowScopingTagGenerator {

    public static class CrossbowScopingEntityTypeTagGenerator extends FabricTagProvider.EntityTypeTagProvider {

        public CrossbowScopingEntityTypeTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        //? if >=26.2
        //@SuppressWarnings("unchecked")
        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            //? if >=26.2 {
            /*builder
            *///?} else if >1.21.1 {
            /*valueLookupBuilder
            *///?} else {
            getOrCreateTagBuilder
            //?}
                    (CrossbowScopingTags.ALLOW_NO_GRAVITY)
                    //~ if >=26.2 'EntityType' -> 'EntityTypeIds'
                    .add(EntityType.ARROW, EntityType.SPECTRAL_ARROW, EntityType.FIREWORK_ROCKET);
        }
    }

    public static class CrossbowScopingEnchantmentTagGenerator extends FabricTagProvider/*? <=1.21.1 {*/ .EnchantmentTagProvider /*?} else {*/ /*<Enchantment> *//*?}*/ {
        private static final String OMNICROSSBOW = "omnicrossbow";

        public CrossbowScopingEnchantmentTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, /*? >1.21.1 {*/ /*Registries.ENCHANTMENT, *//*?}*/ completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            //~ if >1.21.1 'getOrCreateTagBuilder' -> 'getOrCreateRawBuilder'
            getOrCreateTagBuilder(CrossbowScopingTags.USES_EXTENDED_COOLDOWN)
                    //~ if >1.21.1 'addOptional' -> 'addOptionalElement'
                    .addOptional(ResourceLocation.fromNamespaceAndPath(OMNICROSSBOW, "multichambered"));
        }
    }

    public static class CrossbowScopingItemTagGenerator extends FabricTagProvider/*? <=1.21.1 {*/ .ItemTagProvider /*?} else {*/ /*<Item> *//*?}*/ {
        private static final String PIERCED = "pierced";

        public CrossbowScopingItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, /*? >1.21.1 {*/ /*Registries.ITEM, *//*?}*/ completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            //~ if >1.21.1 'getOrCreateTagBuilder' -> 'getOrCreateRawBuilder'
            getOrCreateTagBuilder(CrossbowScopingTags.INCOMPATIBLE_ITEMS)
            //~ if >1.21.1 'addOptional' -> 'addOptionalElement'
                    .addOptional(ResourceLocation.fromNamespaceAndPath(PIERCED, "long_crossbow"));
        }
    }
}
//~}
//~}