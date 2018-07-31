package xyz.vsngamer.seedprotect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Events {

    @SubscribeEvent
    static void onTrample(BlockEvent.FarmlandTrampleEvent event) {

        if (event.getWorld().isRemote) {
            event.setCanceled(true);
            return;
        }

        Entity entity = event.getEntity();
        MinecraftServer server = event.getWorld().getMinecraftServer();
        String entityType = null;

        if (entity instanceof EntityPlayer && !ModConfig.players) {
            event.setCanceled(true);
            entityType = "player";
        } else if (entity instanceof EntityLiving && !ModConfig.mobs) {
            event.setCanceled(true);
            entityType = "mob";
        }

        if (ModConfig.debug && event.isCanceled()) {
            BlockPos pos = event.getPos();
            server.sendMessage(new TextComponentString("Protected against " + entityType + "(" + entity.getName() + ")" + " trampling at X:" + pos.getX() + " Y:" + pos.getY() + " Z:" + pos.getZ()));
        }
    }


    @SubscribeEvent
    static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Constants.MOD_ID)) {
            ConfigManager.sync(Constants.MOD_ID, Config.Type.INSTANCE);
        }
    }
}

