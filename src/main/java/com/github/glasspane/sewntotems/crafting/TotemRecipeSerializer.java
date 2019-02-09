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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.crafting.ShapedRecipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TotemRecipeSerializer implements RecipeSerializer<TotemRecipe> {

    @Override
    public TotemRecipe read(Identifier id, JsonObject json) {
        String group = JsonHelper.getString(json, "group", "");
        JsonArray array = JsonHelper.getArray(json, "ingredients");
        DefaultedList<Ingredient> ingredients = StreamSupport.stream(array.spliterator(), false).map(Ingredient::fromJson).filter(i -> !i.isEmpty()).collect(Collectors.toCollection(DefaultedList::create));
        if(ingredients.isEmpty()) {
            throw new JsonParseException("No ingredients for totem shapeless recipe");
        }
        else if(ingredients.size() > 8) {
            throw new JsonParseException("Too many ingredients for totem shapeless recipe, max is 8");
        }
        else {
            ItemStack totem = ShapedRecipe.deserializeItemStack(JsonHelper.getObject(json, "totem"));
            ItemStack output = ShapedRecipe.deserializeItemStack(JsonHelper.getObject(json, "result"));
            return new TotemRecipe(id, group, output, ingredients, totem);
        }
    }

    @Override
    public TotemRecipe read(Identifier id, PacketByteBuf buf) {
        return null;
    }

    @Override
    public void write(PacketByteBuf buf, TotemRecipe recipe) {
    }
}
