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
import com.github.glasspane.sewntotems.item.ItemSewnTotem;
import net.minecraft.item.Item;

@AutoRegistry(value = Item.class, modid = SewnTotems.MODID, registry = "item")
public class TotemsItems implements AutoRegistryHook {

    public static final Item BONE_NEEDLE = new Item(new Item.Settings().itemGroup(SewnTotems.CREATIVE_TAB).stackSize(16));
    public static final Item SEWN_TOTEM = new ItemSewnTotem();
}
