# Crossbow Scoping

Crossbow Scoping is a mod that allows you to attach spyglasses to crossbows!

Right-click on a crossbow while holding a spyglass with the mouse cursor to attach the spyglass to the crossbow.

Crossbows with scopes load projectiles normally; however, when using a loaded crossbow with a scope, the use action will be delegated to the scope.
Instead, to shoot the projectile(s), you now have to press your attack key/button.

This mod (should have) out-of-the-box compatibility with mods that add custom spyglasses and custom crossbow projectiles. Feel free to open an issue on GitHub if you find an exception!

## Gamerules
1. crossbow_scoping:higherPrecision - significantly increases scoped crossbow precision
2. crossbow_scoping:higherVelocity - toggles velocity change of projectiles fired by scoped crossbow
3. crossbow_scoping:velocityMultiplier - controls the velocity multiplier for projectiles fired by scoped crossbows
4. crossbow_scoping:noGravityProjectiles - entities in the `crossbow_scoping:allow_no_gravity` tag will have no gravity when fired from a scoped crossbow. By default, the tag contains arrows, spectral arrows, and firework rockets.