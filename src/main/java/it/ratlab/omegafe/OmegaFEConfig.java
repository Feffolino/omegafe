// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.minecraftforge.common.ForgeConfigSpec;

/** Common config for Forge 1.20.1. Capacities are read dynamically at runtime. */
public final class OmegaFEConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue FE_PER_CHARGE = BUILDER
            .comment("FE needed for one charge point of a rechargeable battery (1 point = 1 tick of light).")
            .defineInRange("fePerCharge", 2, 1, 10_000);

    static {
        BUILDER.comment("Charge capacity of the rechargeable batteries, in charge points (ticks of light at drain 1).",
                "Rat Lab uses 6000 / 18000 / 54000.");
        BUILDER.push("capacity");
    }

    public static final ForgeConfigSpec.IntValue SMALL_CAPACITY = BUILDER
            .defineInRange("small", 6000, 1, 1_000_000);
    public static final ForgeConfigSpec.IntValue MEDIUM_CAPACITY = BUILDER
            .defineInRange("medium", 18000, 1, 1_000_000);
    public static final ForgeConfigSpec.IntValue LARGE_CAPACITY = BUILDER
            .defineInRange("large", 54000, 1, 1_000_000);

    static {
        BUILDER.pop();
    }

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private OmegaFEConfig() {}

    /** Safe read: falls back to default if config is not loaded yet. */
    public static int fePerCharge() {
        try {
            return Math.max(1, FE_PER_CHARGE.get());
        } catch (IllegalStateException e) {
            return FE_PER_CHARGE.getDefault();
        }
    }

    public static int smallCapacity() {
        try {
            return SMALL_CAPACITY.get();
        } catch (IllegalStateException e) {
            return SMALL_CAPACITY.getDefault();
        }
    }

    public static int mediumCapacity() {
        try {
            return MEDIUM_CAPACITY.get();
        } catch (IllegalStateException e) {
            return MEDIUM_CAPACITY.getDefault();
        }
    }

    public static int largeCapacity() {
        try {
            return LARGE_CAPACITY.get();
        } catch (IllegalStateException e) {
            return LARGE_CAPACITY.getDefault();
        }
    }
}
