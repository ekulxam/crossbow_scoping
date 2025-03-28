package survivalblock.crossbow_scoping.common.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingTags {

    public static final TagKey<EntityType<?>> ALLOW_NO_GRAVITY = TagKey.of(RegistryKeys.ENTITY_TYPE, CrossbowScoping.id("allow_no_gravity"));

    public static final TagKey<Enchantment> USES_EXTENDED_COOLDOWN = TagKey.of(RegistryKeys.ENCHANTMENT, CrossbowScoping.id("uses_extended_cooldown"));

    public static final TagKey<Item> INCOMPATIBLE_ITEMS = TagKey.of(RegistryKeys.ITEM, CrossbowScoping.id("incompatible_items"));
}
