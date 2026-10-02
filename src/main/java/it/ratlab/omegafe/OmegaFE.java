// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(OmegaFE.MOD_ID)
public final class OmegaFE {
    public static final String MOD_ID = "omegafe";

    private static final ResourceKey<CreativeModeTab> OMEGA_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            new ResourceLocation("omegaflashlight", "flashlight_tab"));

    public OmegaFE() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OmegaFEConfig.SPEC);

        OmegaFEItems.ITEMS.register(modEventBus);
        EmptyBatteryRecipe.SERIALIZERS.register(modEventBus);

        modEventBus.addListener(OmegaFE::addToTabs);

        MinecraftForge.EVENT_BUS.addListener(OmegaFEChargeHandler::onPlayerTick);

        if (FMLEnvironment.dist.isClient()) {
            MinecraftForge.EVENT_BUS.addListener(ClientTooltips::onTooltip);
        }
    }

    private static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == OMEGA_TAB || event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(OmegaFEItems.SMALL.get());
            event.accept(OmegaFEItems.MEDIUM.get());
            event.accept(OmegaFEItems.LARGE.get());
        }
    }
}
