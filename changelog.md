Crossbow Scoping 1.1.0+1.21.1
- Removed direct apoli compat (works without it now)
- Scoped Crossbow firing payload now tracks what hand the crossbow is in
- Fixed scope rendering in third person
- Fixed offhand hotbar rendering when using crossbow
- Internals rework
  - Minecraft now thinks the slot's ItemStack is a spyglass and not a crossbow
- Fixed not being able to see through scope unless on cooldown