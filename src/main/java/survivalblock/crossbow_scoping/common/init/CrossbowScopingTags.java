package survivalblock.crossbow_scoping.common.init;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import survivalblock.crossbow_scoping.common.CrossbowScoping;

public class CrossbowScopingTags {

    public static final TagKey<EntityType<?>> ALLOW_NO_GRAVITY = TagKey.of(RegistryKeys.ENTITY_TYPE, CrossbowScoping.id("allow_no_gravity"));

}
