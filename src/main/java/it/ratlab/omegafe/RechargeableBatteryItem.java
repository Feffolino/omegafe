// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import com.omega.flashlight.item.BatteryItem;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * An Omega Flashlight battery that also exposes an FE capability (see {@link BatteryEnergyStorage}).
 * Extends Omega's BatteryItem so the flashlight's battery slots and drain logic accept it unchanged:
 * charge is stored as durability, exactly like the original batteries.
 */
public class RechargeableBatteryItem extends BatteryItem {
    private final Supplier<Integer> capacitySupplier;

    public RechargeableBatteryItem(Supplier<Integer> capacitySupplier) {
        super(capacitySupplier.get());
        this.capacitySupplier = capacitySupplier;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return capacitySupplier.get();
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        // No anvil / crafting-grid repair: that would merge charge for free.
        return false;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int fe = OmegaFEConfig.fePerCharge();
        tooltip.add(Component.translatable("tooltip.omegafe.rechargeable").withStyle(ChatFormatting.GREEN));
        tooltip.add(Component.translatable("tooltip.omegafe.charge",
                        BatteryItem.charge(stack) * fe, BatteryItem.capacityOf(stack) * fe)
                .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BatteryCapabilityProvider(stack);
    }

    private static final class BatteryCapabilityProvider implements ICapabilityProvider {
        private final LazyOptional<IEnergyStorage> energyOptional;

        public BatteryCapabilityProvider(ItemStack stack) {
            this.energyOptional = LazyOptional.of(() -> new BatteryEnergyStorage(stack));
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
            if (cap == ForgeCapabilities.ENERGY) {
                return energyOptional.cast();
            }
            return LazyOptional.empty();
        }
    }
}
