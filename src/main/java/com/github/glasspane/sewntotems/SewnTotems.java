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
package com.github.glasspane.sewntotems;

import com.github.glasspane.mesh.crafting.RecipeFactory;
import com.github.glasspane.sewntotems.api.TotemRegistry;
import com.github.glasspane.sewntotems.handler.EntityHandler;
import com.github.glasspane.sewntotems.init.TotemsItems;
import com.github.glasspane.sewntotems.util.TotemHelper;
import nerdhub.textilelib.eventhandlers.EventRegistry;
import nerdhub.textilelib.events.entity.player.PlayerInteractEntityEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.TagHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SewnTotems implements ModInitializer {

    public static final String MODID = "sewn_totems";
    public static final String MOD_NAME = "Sewn Totems";
    public static final String VERSION = "${version}";
    public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(new Identifier(MODID, "items"), () -> new ItemStack(Items.TOTEM_OF_UNDYING));
    private static final Logger log = LogManager.getLogger(MODID);

    public static Logger getLogger() {
        return log;
    }

    @Override
    public void onInitialize() {
        log.info("Hello there!");
        EventRegistry.INSTANCE.registerEventHandler(PlayerInteractEntityEvent.class, EntityHandler::onRightclickEntity);
        TotemRegistry.registerTotem(TotemHelper.VANILLA_TOTEM_NAME.get(), (source, target, stack, hand) -> {
            CompoundTag nbt = new CompoundTag();
            nbt.put("spawn", TagHelper.serializeBlockPos(target.getPos()));
            nbt.putInt("dimension", target.dimension.getRawId());
            return nbt;
        });
        RecipeFactory.getInstance(MODID).addShaped(new ItemStack(TotemsItems.BONE_NEEDLE), new Identifier(MODID, "bone_needle"), MODID, "B", "B", 'B', Items.BONE);
    }
}
