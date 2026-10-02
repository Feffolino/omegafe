// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import com.omega.flashlight.item.BatteryItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * Receive-only FE view over an Omega Flashlight battery's internal charge.
 * Stacks larger than one are refused: the charge data is shared by the whole stack,
 * so charging it would duplicate energy.
 */
public final class BatteryEnergyStorage implements IEnergyStorage {
    private final ItemStack stack;

    public BatteryEnergyStorage(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive() || maxReceive <= 0) {
            return 0;
        }
        int fe = OmegaFEConfig.fePerCharge();
        int charge = BatteryItem.charge(stack);
        int room = Math.max(0, BatteryItem.capacityOf(stack) - charge);
        int points = Math.min(maxReceive / fe, room);
        if (points <= 0) {
            return 0;
        }
        if (!simulate) {
            BatteryItem.setCharge(stack, charge + points);
        }
        return points * fe;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return saturatedMul(BatteryItem.charge(stack), OmegaFEConfig.fePerCharge());
    }

    @Override
    public int getMaxEnergyStored() {
        return saturatedMul(BatteryItem.capacityOf(stack), OmegaFEConfig.fePerCharge());
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return stack.getCount() == 1;
    }

    private static int saturatedMul(int a, int b) {
        long r = (long) a * b;
        return r > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) Math.max(0, r);
    }
}
