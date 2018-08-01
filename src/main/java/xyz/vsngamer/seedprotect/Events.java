package xyz.vsngamer.seedprotect;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Events {

    @SubscribeEvent
    static void onTrample(BlockEvent.FarmlandTrampleEvent event) {

        if (isClientSide(event))
            return;

        Material materialOnTop = event.getWorld().getBlockState(event.getPos().up()).getMaterial();
        String entityType = null;

        if (!(ModConfig.check_crop && materialOnTop == Material.AIR)) {
            Entity entity = event.getEntity();
            if (entity instanceof EntityPlayer && !ModConfig.players) {
                event.setCanceled(true);
                entityType = "Player";
            } else if (entity instanceof EntityLiving && !ModConfig.mobs) {
                event.setCanceled(true);
                entityType = "Mob";
            }
        }

        debug(event, entityType);
    }

    private static boolean isClientSide(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getWorld().isRemote) {
            event.setCanceled(true);
            return true;
        }
        return false;
    }

    private static void debug(BlockEvent.FarmlandTrampleEvent event, String entityType) {
        if (ModConfig.debug && event.isCanceled()) {
            MinecraftServer server = event.getWorld().getMinecraftServer();
            String message = String.format("Protected against %s(%s) trampling at X:%d Y:%d Z:%d", entityType, event.getEntity().getName(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ());
            server.sendMessage(new TextComponentString(message));
        }
    }
}

