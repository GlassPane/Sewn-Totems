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
package com.github.glasspane.sewntotems.init;

import com.github.glasspane.mesh.registry.AutoRegistry;
import com.github.glasspane.mesh.registry.AutoRegistryHook;
import com.github.glasspane.sewntotems.SewnTotems;
import com.github.glasspane.sewntotems.crafting.TotemRecipe;
import com.github.glasspane.sewntotems.crafting.TotemRecipeSerializer;
import net.minecraft.recipe.RecipeSerializer;

@AutoRegistry(value = RecipeSerializer.class, modid = SewnTotems.MODID, registry = "recipe_serializer")
public class TotemsRecipes implements AutoRegistryHook {

    public static final RecipeSerializer<TotemRecipe> CRAFTING_SPECIAL_SEWN_TOTEM = new TotemRecipeSerializer();
}
