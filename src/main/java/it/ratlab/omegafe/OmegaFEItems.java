// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class OmegaFEItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(OmegaFE.MOD_ID);

    // Capacities come from the startup config, read when the items are constructed.
    public static final DeferredItem<RechargeableBatteryItem> SMALL = ITEMS.register("rechargeable_small_battery",
            () -> new RechargeableBatteryItem(OmegaFEConfig.SMALL_CAPACITY.getAsInt()));
    public static final DeferredItem<RechargeableBatteryItem> MEDIUM = ITEMS.register("rechargeable_medium_battery",
            () -> new RechargeableBatteryItem(OmegaFEConfig.MEDIUM_CAPACITY.getAsInt()));
    public static final DeferredItem<RechargeableBatteryItem> LARGE = ITEMS.register("rechargeable_large_battery",
            () -> new RechargeableBatteryItem(OmegaFEConfig.LARGE_CAPACITY.getAsInt()));

    private OmegaFEItems() {}
}
