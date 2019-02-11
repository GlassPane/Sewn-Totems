package com.github.glasspane.sewntotems.mixin.client;

import com.github.glasspane.sewntotems.client.SewnTotemsClientLoader;
import com.github.glasspane.sewntotems.init.TotemsItems;
import com.github.glasspane.sewntotems.item.ItemSewnTotem;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModels.class)
public abstract class MixinItemModels {

    @Shadow public abstract BakedModelManager getModelManager();

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;", at = @At("HEAD"), cancellable = true)
    private void getModel(ItemStack stack, CallbackInfoReturnable<BakedModel> cir) {
        if(stack.getItem() == TotemsItems.SEWN_TOTEM && stack.hasTag()) {
            CompoundTag tag = stack.getTag();
            if(tag.containsKey(ItemSewnTotem.KEY_TOTEM_ID, NbtType.STRING)) {
                Identifier totemName = new Identifier(tag.getString(ItemSewnTotem.KEY_TOTEM_ID));
                BakedModel model = this.getModelManager().getModel(new ModelIdentifier(SewnTotemsClientLoader.TOTEM_ID.get().toString() + "/" + totemName.getNamespace() + "/" + totemName.getPath(), "inventory"));
                cir.setReturnValue(model);
            }
        }
    }
}
