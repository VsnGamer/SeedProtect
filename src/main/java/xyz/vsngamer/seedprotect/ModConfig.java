package xyz.vsngamer.seedprotect;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
@Config(modid = Constants.MOD_ID)
public class ModConfig {

    @Config.Comment("Only protect when there is a crop on top ?")
    public static boolean check_crop = false;

    @Config.Comment("Can players trample farmland?")
    public static boolean players = false;

    @Config.Comment("Can mobs trample farmland?")
    public static boolean mobs = false;

    @Config.Comment("Prints protected farmland coordinates to console")
    public static boolean debug = false;

    @SubscribeEvent
    static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Constants.MOD_ID)) {
            ConfigManager.sync(Constants.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
