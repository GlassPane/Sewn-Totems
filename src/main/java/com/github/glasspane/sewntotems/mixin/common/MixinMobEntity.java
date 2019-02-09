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
package com.github.glasspane.sewntotems.mixin.common;

import com.github.glasspane.sewntotems.SewnTotems;
import com.github.glasspane.sewntotems.api.TotemHolder;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity implements TotemHolder {

    private static final String DATA_ID = SewnTotems.MODID + "_tags";
    private CompoundTag totemData = new CompoundTag();

    @Override
    public CompoundTag getTotemData() {
        return this.totemData;
    }

    @Override
    public boolean hasTotem(String totemID) {
        return this.totemData.containsKey(totemID, NbtType.COMPOUND);
    }

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    private void writeNBT(CompoundTag compoundTag_1, CallbackInfo ci) {
        compoundTag_1.put(DATA_ID, this.totemData);
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    private void readNBT(CompoundTag compoundTag_1, CallbackInfo ci) {
        if(compoundTag_1.containsKey(DATA_ID, NbtType.COMPOUND)) {
            this.totemData = compoundTag_1.getCompound(DATA_ID);
        }
    }
}
