// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import java.util.Locale;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

/** Client-only: hides any durability lines, since durability is the charge shown in FE. */
final class ClientTooltips {
    private ClientTooltips() {}

    static void onTooltip(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof RechargeableBatteryItem)) {
            return;
        }
        event.getToolTip().removeIf(ClientTooltips::isDurabilityLine);
    }

    private static boolean isDurabilityLine(Component component) {
        if (component.getContents() instanceof TranslatableContents translatable) {
            String key = translatable.getKey().toLowerCase(Locale.ROOT);
            if (key.equals("item.durability") || key.startsWith("durabilitytooltip.") || key.contains("durability")) {
                return true;
            }
        }

        for (Component sibling : component.getSiblings()) {
            if (isDurabilityLine(sibling)) {
                return true;
            }
        }

        String raw = component.getString().toLowerCase(Locale.ROOT);
        return raw.contains("durability") || raw.contains("durabilit");
    }
}
