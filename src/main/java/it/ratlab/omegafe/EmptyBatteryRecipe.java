// SPDX-License-Identifier: MIT
package it.ratlab.omegafe;

import com.google.gson.JsonObject;
import com.omega.flashlight.item.BatteryItem;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Custom shaped recipe for rechargeable batteries ensuring the result is completely empty
 * (charge = 0, durability = 0) dynamically according to the configured capacity.
 */
public class EmptyBatteryRecipe extends ShapedRecipe {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, OmegaFE.MOD_ID);

    public static final RegistryObject<RecipeSerializer<EmptyBatteryRecipe>> SERIALIZER =
            SERIALIZERS.register("empty_battery", Serializer::new);

    public EmptyBatteryRecipe(ShapedRecipe compose) {
        super(compose.getId(), compose.getGroup(), compose.category(),
                compose.getWidth(), compose.getHeight(), compose.getIngredients(),
                emptyResult(compose.getResultItem(RegistryAccess.EMPTY)));
    }

    private static ItemStack emptyResult(ItemStack stack) {
        ItemStack copy = stack.copy();
        BatteryItem.setCharge(copy, 0);
        return copy;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        ItemStack result = super.assemble(container, access);
        BatteryItem.setCharge(result, 0);
        return result;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        ItemStack result = super.getResultItem(access);
        BatteryItem.setCharge(result, 0);
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER.get();
    }

    public static final class Serializer implements RecipeSerializer<EmptyBatteryRecipe> {
        @Override
        public EmptyBatteryRecipe fromJson(ResourceLocation id, JsonObject json) {
            ShapedRecipe shaped = RecipeSerializer.SHAPED_RECIPE.fromJson(id, json);
            return new EmptyBatteryRecipe(shaped);
        }

        @Override
        public @Nullable EmptyBatteryRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            ShapedRecipe shaped = RecipeSerializer.SHAPED_RECIPE.fromNetwork(id, buffer);
            return shaped != null ? new EmptyBatteryRecipe(shaped) : null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, EmptyBatteryRecipe recipe) {
            RecipeSerializer.SHAPED_RECIPE.toNetwork(buffer, recipe);
        }
    }
}
