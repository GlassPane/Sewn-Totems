package com.github.glasspane.sewntotems;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SewnTotems {

    public static final String MODID = "sewn_totems";
    public static final String MOD_NAME = "Sewn Totems";
    public static final String VERSION = "${version}";

    private static final Logger log = LogManager.getLogger(MODID);

    public static final ItemGroup CREATIVE_TAB = FabricItemGroupBuilder.build(new Identifier(MODID, "Items"), () -> new ItemStack(Items.DIAMOND));

    public static Logger getLogger() {
        return log;
    }

    public static void onLoad() {
        log.info("Hello there!");
    }
}
