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
package com.github.glasspane.sewntotems.item;

import com.github.glasspane.sewntotems.SewnTotems;
import com.github.glasspane.sewntotems.api.TotemHolder;
import com.github.glasspane.sewntotems.api.TotemRegistry;
import com.github.glasspane.sewntotems.util.TotemHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.item.TooltipOptions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TextFormat;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSewnTotem extends Item {

    public static final String KEY_TOTEM_ID = "totem_id";

    public ItemSewnTotem() {
        super(new Item.Settings().itemGroup(SewnTotems.CREATIVE_TAB).stackSize(1).rarity(Rarity.RARE));
    }

    @Override
    public boolean interactWithEntity(ItemStack itemStack_1, PlayerEntity playerEntity_1, LivingEntity livingEntity_1, Hand hand_1) {
        return super.interactWithEntity(itemStack_1, playerEntity_1, livingEntity_1, hand_1);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void buildTooltip(ItemStack stack, @Nullable World world, List<TextComponent> tooltip, TooltipOptions options) {
        if(stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            //noinspection ConstantConditions
            if(tag.containsKey(KEY_TOTEM_ID, NbtType.STRING)) {
                tooltip.add(new TranslatableTextComponent(this.getTranslationKey() + ".desc", tag.getString(KEY_TOTEM_ID)).applyFormat(TextFormat.GRAY));
            }
        }
        super.buildTooltip(stack, world, tooltip, options);
    }

    @Override
    public void addStacksForDisplay(ItemGroup group, DefaultedList<ItemStack> itemStacks) {
        if(this.isInItemGroup(group)) {
            TotemRegistry.getRegisteredTotems().forEach(id -> {
                ItemStack stack = new ItemStack(this);
                stack.getOrCreateTag().putString(KEY_TOTEM_ID, id);
                itemStacks.add(stack);
            });
        }
    }

    public boolean canApplyAt(ItemStack stack, TotemHolder target) {
        return true;
    }

    public void applyTotem(PlayerEntity source, TotemHolder target, Hand hand, ItemStack stack) {
        if(target instanceof MobEntity) {
            MobEntity entity = (MobEntity) target;
            String totemID = getTotemID(stack);
            CompoundTag totemData = TotemRegistry.getTagData(totemID, source, entity, stack, hand);
            if(totemData != null) {
                target.getTotemData().put(totemID, totemData);
                if(!source.isCreative()) {
                    stack.subtractAmount(1);
                }
            }
            else {
                SewnTotems.getLogger().warn("retrieved NULL totem data for totem {}", totemID);
            }
        }
    }

    private static String getTotemID(ItemStack stack) {
        //noinspection ConstantConditions
        return stack.hasTag() ? stack.getTag().getString(KEY_TOTEM_ID) : TotemHelper.VANILLA_TOTEM_NAME.get();
    }
}
