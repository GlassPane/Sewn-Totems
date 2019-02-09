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
package com.github.glasspane.sewntotems.handler;

import com.github.glasspane.sewntotems.api.TotemHolder;
import com.github.glasspane.sewntotems.item.ItemSewnTotem;
import nerdhub.textilelib.events.entity.player.PlayerInteractEntityEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class EntityHandler {

    public static void onRightclickEntity(PlayerInteractEntityEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack stack = player.getStackInHand(event.getHand());
        if(!event.getPlayer().world.isClient && event.getTarget() instanceof TotemHolder && stack.getItem() instanceof ItemSewnTotem) {
            TotemHolder target = (TotemHolder) event.getTarget();
            if(target.canApplyTotem() && ((ItemSewnTotem) stack.getItem()).canApplyAt(stack, target)) {
                ItemSewnTotem totem = (ItemSewnTotem) stack.getItem();
                totem.applyTotem(player, target, event.getHand(), stack);
                event.cancelEvent();
            }
        }
    }
}
