package survivalblock.crossbow_scoping.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingTags {

    public static final TagKey<EntityType<?>> ALLOW_NO_GRAVITY = TagKey.create(Registries.ENTITY_TYPE, CrossbowScoping.id("allow_no_gravity"));

    public static final TagKey<Enchantment> USES_EXTENDED_COOLDOWN = TagKey.create(Registries.ENCHANTMENT, CrossbowScoping.id("uses_extended_cooldown"));

    public static final TagKey<Item> INCOMPATIBLE_ITEMS = TagKey.create(Registries.ITEM, CrossbowScoping.id("incompatible_items"));
}
