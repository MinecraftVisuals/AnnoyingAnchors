package com.anchorlock;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class LockedItems {
    private LockedItems() {}

    /** Items that are not allowed to leave their inventory slot. Add more here if you want. */
    public static boolean isLocked(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.is(Items.RESPAWN_ANCHOR) || stack.is(Items.GLOWSTONE));
    }
}
