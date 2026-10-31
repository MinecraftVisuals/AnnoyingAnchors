package com.anchorlock;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class LockedItems {
    private LockedItems() {}

    /** Master switch. Not saved anywhere, so it is ON every time the game launches. */
    public static boolean enabled = true;

    /** Items that are not allowed to leave their inventory slot. Add more here if you want. */
    public static boolean isLocked(ItemStack stack) {
        return enabled
                && !stack.isEmpty()
                && (stack.is(Items.RESPAWN_ANCHOR) || stack.is(Items.GLOWSTONE));
    }
}
