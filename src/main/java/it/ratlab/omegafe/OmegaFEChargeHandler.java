// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import com.omega.flashlight.item.BatteryItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;

/**
 * One-shot "set charge" handler for /give using NBT tags, e.g.
 * {@code /give @p omegafe:rechargeable_small_battery{omegafe_charge:3000}} or
 * {@code /give @p omegaflashlight:large_battery{omegafe_charge_percent:50}}.
 * On player tick on the server, the tag is converted to real battery charge and removed.
 */
public final class OmegaFEChargeHandler {
    private OmegaFEChargeHandler() {}

    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide()) {
            return;
        }
        Player player = event.player;
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            apply(inventory.getItem(i));
        }
    }

    /** Converts a pending charge NBT tag into the battery's real charge. */
    public static void apply(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BatteryItem) || !stack.hasTag()) {
            return;
        }
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }
        Integer points = null;
        if (tag.contains("omegafe_charge", Tag.TAG_INT)) {
            points = tag.getInt("omegafe_charge");
            tag.remove("omegafe_charge");
        } else if (tag.contains("omegafe:charge", Tag.TAG_INT)) {
            points = tag.getInt("omegafe:charge");
            tag.remove("omegafe:charge");
        }

        Integer percent = null;
        if (tag.contains("omegafe_charge_percent", Tag.TAG_INT)) {
            percent = tag.getInt("omegafe_charge_percent");
            tag.remove("omegafe_charge_percent");
        } else if (tag.contains("omegafe:charge_percent", Tag.TAG_INT)) {
            percent = tag.getInt("omegafe:charge_percent");
            tag.remove("omegafe:charge_percent");
        }

        if (points == null && percent == null) {
            return;
        }

        int capacity = BatteryItem.capacityOf(stack);
        int charge = points != null ? points : (int) Math.round(capacity * Math.max(0, Math.min(100, percent)) / 100.0);
        BatteryItem.setCharge(stack, Math.max(0, Math.min(charge, capacity)));
        if (tag.isEmpty()) {
            stack.setTag(null);
        }
    }
}
