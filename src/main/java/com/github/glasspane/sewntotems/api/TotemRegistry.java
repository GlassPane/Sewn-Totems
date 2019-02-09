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
package com.github.glasspane.sewntotems.api;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Hand;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TotemRegistry {

    private static final Map<String, TotemDataSupplier> TOTEM_TYPES = new HashMap<>();

    public static void registerTotem(String id, TotemDataSupplier tagSupplier) {
        TOTEM_TYPES.put(id, tagSupplier);
    }

    @Nullable
    public static CompoundTag getTagData(String totemID, PlayerEntity source, MobEntity target, ItemStack stack, Hand hand) {
        return TOTEM_TYPES.containsKey(totemID) ? TOTEM_TYPES.get(totemID).applyTo(source, target, stack, hand) : null;
    }

    public static Collection<String> getRegisteredTotems() {
        return TOTEM_TYPES.keySet();
    }

    @FunctionalInterface
    public interface TotemDataSupplier {

        CompoundTag applyTo(PlayerEntity source, MobEntity target, ItemStack stack, Hand hand);
    }
}
