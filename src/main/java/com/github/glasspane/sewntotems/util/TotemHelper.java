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
package com.github.glasspane.sewntotems.util;

import com.github.glasspane.mesh.util.objects.LazyReference;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class TotemHelper {

    public static LazyReference<String> VANILLA_TOTEM_NAME = new LazyReference<>(() -> String.valueOf(Registry.ITEM.getId(Items.TOTEM_OF_UNDYING)));
    public static byte TOTEM_PARTICLE_ID = (byte) 35;
}
