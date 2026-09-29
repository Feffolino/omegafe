// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(OmegaFE.MOD_ID)
public final class OmegaFE {
    public static final String MOD_ID = "omegafe";

    private static final ResourceKey<CreativeModeTab> OMEGA_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath("omegaflashlight", "flashlight_tab"));

    public OmegaFE(IEventBus modEventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.STARTUP, OmegaFEConfig.SPEC);
        OmegaFEItems.ITEMS.register(modEventBus);
        OmegaFEComponents.COMPONENTS.register(modEventBus);
        modEventBus.addListener(OmegaFE::registerCapabilities);
        modEventBus.addListener(OmegaFE::addToTabs);
        NeoForge.EVENT_BUS.addListener(OmegaFEComponents::onPlayerTick);
        if (FMLEnvironment.dist.isClient()) {
            NeoForge.EVENT_BUS.addListener(ClientTooltips::onTooltip);
        }
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Only our rechargeable batteries get FE; Omega's own batteries stay single-use.
        event.registerItem(Capabilities.EnergyStorage.ITEM,
                (stack, context) -> new BatteryEnergyStorage(stack),
                OmegaFEItems.SMALL.get(), OmegaFEItems.MEDIUM.get(), OmegaFEItems.LARGE.get());
    }

    private static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == OMEGA_TAB || event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(OmegaFEItems.SMALL.get());
            event.accept(OmegaFEItems.MEDIUM.get());
            event.accept(OmegaFEItems.LARGE.get());
        }
    }
}
