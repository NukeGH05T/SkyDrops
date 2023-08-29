package me.nukeghost.skydrops.utils;

import me.nukeghost.skydrops.SkyDrops;
import org.bukkit.entity.Player;

public class MessagingUtils {

    public static void sendMessage(Player p, String message) {
        p.sendMessage(SkyDrops.prefix + " " + message);
    }
}
