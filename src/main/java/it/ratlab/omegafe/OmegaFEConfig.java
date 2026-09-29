// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import net.neoforged.neoforge.common.ModConfigSpec;

/** Startup config: loaded before item registration, so capacities can size the items. */
public final class OmegaFEConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue FE_PER_CHARGE = BUILDER
            .comment("FE needed for one charge point of a rechargeable battery (1 point = 1 tick of light).")
            .defineInRange("fePerCharge", 2, 1, 10_000);

    static {
        BUILDER.comment("Charge capacity of the rechargeable batteries, in charge points (ticks of light at drain 1).",
                "Defaults match Omega Flashlight's default single-use capacities. Restart the game after changing.");
        BUILDER.push("capacity");
    }

    public static final ModConfigSpec.IntValue SMALL_CAPACITY = BUILDER
            .defineInRange("small", 2400, 1, 1_000_000);
    public static final ModConfigSpec.IntValue MEDIUM_CAPACITY = BUILDER
            .defineInRange("medium", 7200, 1, 1_000_000);
    public static final ModConfigSpec.IntValue LARGE_CAPACITY = BUILDER
            .defineInRange("large", 24000, 1, 1_000_000);

    static {
        BUILDER.pop();
    }

    public static final ModConfigSpec SPEC = BUILDER.build();

    private OmegaFEConfig() {}

    /** Safe read: falls back to the default if the config is not loaded yet. */
    public static int fePerCharge() {
        try {
            return Math.max(1, FE_PER_CHARGE.getAsInt());
        } catch (IllegalStateException e) {
            return FE_PER_CHARGE.getDefault();
        }
    }
}
