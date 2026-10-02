// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/** Client-only: hides the vanilla "Durability: X / Y" line, since durability is the charge shown in FE. */
final class ClientTooltips {
    private ClientTooltips() {}

    static void onTooltip(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof RechargeableBatteryItem)) {
            return;
        }
        event.getToolTip().removeIf(line -> line.getContents() instanceof TranslatableContents t
                && "item.durability".equals(t.getKey()));
    }
}
