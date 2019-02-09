/*
 * Sewn-Totems
 * Copyright (C) 2019-2019 GlassPane
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses>.
 */
package com.github.glasspane.sewntotems.crafting;

import com.github.glasspane.sewntotems.init.TotemsRecipes;
import com.github.glasspane.sewntotems.item.ItemSewnTotem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.crafting.SpecialCraftingRecipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TotemRecipe extends SpecialCraftingRecipe {

    private final String group;
    private final DefaultedList<Ingredient> input;
    private final ItemStack output;

    TotemRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input, ItemStack totem) {
        super(id);
        this.group = group;
        this.output = output;
        this.input = input;
        if(!output.hasTag()) {
            output.setTag(new CompoundTag());
        }
        this.output.getOrCreateTag().putString(ItemSewnTotem.KEY_TOTEM_ID, Registry.ITEM.getId(totem.getItem()).toString());
        this.input.add(0, Ingredient.ofStacks(totem));
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        RecipeFinder recipeFinder = new RecipeFinder();
        int foundItems = 0;
        for(int i = 0; i < inv.getInvSize(); ++i) {
            ItemStack itemStack_1 = inv.getInvStack(i);
            if(!itemStack_1.isEmpty()) {
                ++foundItems;
                recipeFinder.addItem(itemStack_1);
            }
        }
        return foundItems == this.input.size() && recipeFinder.findRecipe(this, null);
    }

    @Override
    public ItemStack craft(CraftingInventory var1) {
        return this.getOutput().copy();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean fits(int xSize, int ySize) {
        return xSize * ySize >= 3;
    }

    @Override
    public DefaultedList<Ingredient> getPreviewInputs() {
        return this.input;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TotemsRecipes.CRAFTING_SPECIAL_SEWN_TOTEM;
    }
}
