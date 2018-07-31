package xyz.vsngamer.seedprotect;

import net.minecraftforge.common.config.Config;

@Config(modid = Constants.MOD_ID)
public class ModConfig {
    @Config.Comment("Can players trample farmland?")
    public static boolean players = false;

    @Config.Comment("Can mobs trample farmland?")
    public static boolean mobs = false;

    @Config.Comment("Prints protected farmland coordinates to console")
    public static boolean debug = false;
}
