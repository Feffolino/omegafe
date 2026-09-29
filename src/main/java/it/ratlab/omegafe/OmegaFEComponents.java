// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import com.mojang.serialization.Codec;
import com.omega.flashlight.item.BatteryItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * One-shot "set charge" components for /give, e.g.
 * {@code /give @s omegafe:rechargeable_small_battery[omegafe:charge=3000]} or
 * {@code /give @s omegaflashlight:large_battery[omegafe:charge_percent=50]}.
 * On the next server tick in a player's inventory the value becomes the real charge and the component is removed.
 */
public final class OmegaFEComponents {
    public static final DeferredRegister.DataComponents COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, OmegaFE.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE =
            COMPONENTS.registerComponentType("charge", builder -> builder
                    .persistent(ExtraCodecs.NON_NEGATIVE_INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CHARGE_PERCENT =
            COMPONENTS.registerComponentType("charge_percent", builder -> builder
                    .persistent(Codec.intRange(0, 100))
                    .networkSynchronized(ByteBufCodecs.VAR_INT));

    private OmegaFEComponents() {}

    static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            apply(inventory.getItem(i));
        }
    }

    /** Converts a pending charge component into the battery's real charge. */
    static void apply(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof BatteryItem)) {
            return;
        }
        Integer points = stack.remove(CHARGE.get());
        Integer percent = stack.remove(CHARGE_PERCENT.get());
        if (points == null && percent == null) {
            return;
        }
        int capacity = BatteryItem.capacityOf(stack);
        int charge = points != null ? points : (int) Math.round(capacity * percent / 100.0);
        BatteryItem.setCharge(stack, Math.min(charge, capacity));
    }
}
