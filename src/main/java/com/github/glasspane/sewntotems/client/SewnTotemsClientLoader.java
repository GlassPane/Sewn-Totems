package com.github.glasspane.sewntotems.client;

import com.github.glasspane.mesh.util.objects.LazyReference;
import com.github.glasspane.sewntotems.SewnTotems;
import com.github.glasspane.sewntotems.api.TotemRegistry;
import com.github.glasspane.sewntotems.init.TotemsItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SewnTotemsClientLoader implements ClientModInitializer {

    public static final LazyReference<Identifier> TOTEM_ID = new LazyReference<>(() -> Registry.ITEM.getId(TotemsItems.SEWN_TOTEM));
    @Override
    public void onInitializeClient() {
        //declare the additional models to load. the actual mapping is done in the mixin
        ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> TotemRegistry.getRegisteredTotems().forEach(totemID -> {
            Identifier totemName = new Identifier(totemID);
            //noinspection ConstantConditions
            out.accept(new ModelIdentifier(new Identifier(SewnTotems.MODID, TOTEM_ID.get().getPath() + "/" + totemName.getNamespace() + "/" + totemName.getPath()), "inventory"));
        }));
    }
}
