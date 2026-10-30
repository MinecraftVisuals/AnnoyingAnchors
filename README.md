# Anchor Lock

Client-side Fabric mod for Minecraft 1.21.11. Respawn Anchors and Glowstone are locked to their inventory slots: if you try to move them, nothing happens and they stay put.

Blocked actions (only for items in your own inventory): picking up, shift-clicking, number-key swaps, Q / Ctrl+Q drops, and F (swap with offhand).

Container slots (chests etc.) are not locked, and the creative inventory screen is not covered.

To lock more items, edit `LockedItems.java`.

## Build

Requires JDK 21. Run `gradle build` and take the jar from `build/libs/`. The included GitHub Actions workflow does this on every push.
