// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import com.omega.flashlight.item.BatteryItem;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

/**
 * An Omega Flashlight battery that also exposes an FE capability (see {@link BatteryEnergyStorage}).
 * Extends Omega's BatteryItem so the flashlight's battery slots and drain logic accept it unchanged:
 * charge is stored as durability, exactly like the original batteries.
 */
public class RechargeableBatteryItem extends BatteryItem {
    public RechargeableBatteryItem(int capacity) {
        super(capacity);
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        // No anvil / crafting-grid repair: that would merge charge for free.
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int fe = OmegaFEConfig.fePerCharge();
        tooltip.add(Component.translatable("tooltip.omegafe.rechargeable").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("tooltip.omegafe.charge",
                        BatteryItem.charge(stack) * fe, BatteryItem.capacityOf(stack) * fe)
                .withStyle(ChatFormatting.GRAY));
    }
}
