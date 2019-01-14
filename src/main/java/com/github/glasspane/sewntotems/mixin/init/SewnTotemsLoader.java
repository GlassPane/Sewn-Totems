package com.github.glasspane.sewntotems.mixin.init;

import com.github.glasspane.mesh.util.CalledByReflection;
import com.github.glasspane.sewntotems.SewnTotems;
import net.fabricmc.api.ModInitializer;

@CalledByReflection
public class SewnTotemsLoader implements ModInitializer {

    @Override
    public void onInitialize() {
        SewnTotems.onLoad();
    }
}
