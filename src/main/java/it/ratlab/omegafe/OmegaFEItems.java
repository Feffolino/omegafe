// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class OmegaFEItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OmegaFE.MOD_ID);

    public static final RegistryObject<RechargeableBatteryItem> SMALL = ITEMS.register("rechargeable_small_battery",
            () -> new RechargeableBatteryItem(OmegaFEConfig::smallCapacity));
    public static final RegistryObject<RechargeableBatteryItem> MEDIUM = ITEMS.register("rechargeable_medium_battery",
            () -> new RechargeableBatteryItem(OmegaFEConfig::mediumCapacity));
    public static final RegistryObject<RechargeableBatteryItem> LARGE = ITEMS.register("rechargeable_large_battery",
            () -> new RechargeableBatteryItem(OmegaFEConfig::largeCapacity));

    private OmegaFEItems() {}
}
