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

import com.github.glasspane.sewntotems.api.TotemHolder;
import com.github.glasspane.sewntotems.util.TotemHelper;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.TagHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @Shadow protected boolean dead;

    @Inject(method = "method_6095", at = @At(value = "RETURN"), cancellable = true) //TODO make configurable? injecting at the TAIL -> dies from void damage (also /kill)
    private void method_6095(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) {
            LivingEntity entity = LivingEntity.class.cast(this);
            //noinspection ConstantConditions
            if(!cir.getReturnValue() && entity instanceof MobEntity) {
                CompoundTag tag = ((TotemHolder) entity).getTotemData();
                if(tag.containsKey(TotemHelper.VANILLA_TOTEM_NAME.get(), NbtType.COMPOUND)) {
                    CompoundTag data = tag.getCompound(TotemHelper.VANILLA_TOTEM_NAME.get());
                    BlockPos spawn = TagHelper.deserializeBlockPos(data.getCompound("spawn"));
                    entity.setHealth(1.0F);
                    this.dead = false;
                    entity.clearPotionEffects();
                    entity.addPotionEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                    entity.addPotionEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    entity.world.summonParticle(entity, TotemHelper.TOTEM_PARTICLE_ID);
                    int spawnDim = data.getInt("dimension");
                    if(spawnDim != entity.dimension.getRawId()) {

                        entity.changeDimension(Registry.DIMENSION.getInt(spawnDim)); //FIXME doesn't work
                    }
                    entity.setPositionAnglesAndUpdate(spawn.getX() + 0.5D, spawn.getY(), spawn.getZ() + 0.5D, entity.yaw, entity.pitch);
                    tag.remove(TotemHelper.VANILLA_TOTEM_NAME.get());
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
