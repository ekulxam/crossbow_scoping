package survivalblock.crossbow_scoping.common.injected_interface;

import net.minecraft.item.ItemStack;

public interface CrossbowAttackingPlayer {

    default void crossbow_scoping$setAttacking(boolean attacking) {

    }

    default boolean crossbow_scoping$isAttacking() {
        return false;
    }

    default void crossbow_scoping$setStartingToScope(ItemStack scope) {

    }

    default boolean crossbow_scoping$usingScope(ItemStack crossbow) {
        return false;
    }
}
