package survivalblock.crossbow_scoping.common.injected_interface;

public interface CrossbowAttackingPlayer {

    default void crossbow_scoping$setAttacking(boolean attacking) {

    }

    default boolean crossbow_scoping$isAttacking() {
        return false;
    }
}
