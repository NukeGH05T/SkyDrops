package me.nukeghost.skydrops.handlers;

import com.itsradiix.DiscordWebHookMessage;
import com.itsradiix.embed.Embed;
import me.nukeghost.skydrops.data.LootSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

import static me.nukeghost.skydrops.SkyDrops.isWebhookOn;
import static me.nukeghost.skydrops.SkyDrops.webhookURL;
import static me.nukeghost.skydrops.handlers.GenerationHandler.getRandomNumber;
import static me.nukeghost.skydrops.utils.PlaceholderUtils.parsePlaceholders;
import static me.nukeghost.skydrops.webhook.DiscordWebhook.sendDiscordWebhook;

public class ClusterSpawnHandler {
    public static void spawnCluster(LootSet lootSet, Material outerMaterial, boolean carve) {
        Location spawnLocation = chestLocation(lootSet.getSpawnRegions().get(getRandomNumber(0, lootSet.getSpawnRegions().size() - 1)), carve);

        spawnLocation.getBlock().setType(Material.CHEST);
        System.out.println(spawnLocation.getBlock().getType());
        Chest chest = (Chest) spawnLocation.getBlock().getState();
        setOuterLayer(outerMaterial, spawnLocation);

        for (int i = 0; i < lootSet.getLoots().size(); i++) {
            chest.getInventory().setItem(i, lootSet.getLoots().get(i));
        }

        //Sending Notice
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("sd.notice")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', parsePlaceholders(lootSet.getNotice(),
                        spawnLocation.getBlockX() + "/" + spawnLocation.getBlockY() + "/" + spawnLocation.getBlockZ())));
            }
        }

        if (isWebhookOn) {
            sendDiscordWebhook(lootSet.getId(), "Spawn location: `" + spawnLocation.getBlockX() + "/" + spawnLocation.getBlockY() + "/" + spawnLocation.getBlockZ() + "` in `" + spawnLocation.getWorld().getName() + "`");
        }

    }

    private static Location chestLocation(String spawnString, boolean carve) {
        //      3/60/-55~15/60/-40@world
        String[] corners = spawnString.split("@")[0].split("~");
        String worldName = spawnString.split("@")[1];

        String[] c1 = corners[0].split("/");
        String[] c2 = corners[1].split("/");

        int[] spawnCoords = {getRandomNumber(Math.min(Integer.parseInt(c1[0]), Integer.parseInt(c2[0])), Math.max(Integer.parseInt(c1[0]), Integer.parseInt(c2[0]))),
                            getRandomNumber(Math.min(Integer.parseInt(c1[1]), Integer.parseInt(c2[1])), Math.max(Integer.parseInt(c1[1]), Integer.parseInt(c2[1]))),
                            getRandomNumber(Math.min(Integer.parseInt(c1[2]), Integer.parseInt(c2[2])), Math.max(Integer.parseInt(c1[2]), Integer.parseInt(c2[2])))};

        System.out.println("Spawn Coords: " + Arrays.toString(spawnCoords));

        //Carve
        if (!carve){
            Location location = new Location(Bukkit.getWorld(worldName), spawnCoords[0], spawnCoords[1], spawnCoords[2]);
            return location;

        } else {
            //Top
            Location topLocation = new Location(Bukkit.getWorld(worldName), spawnCoords[0], spawnCoords[1], spawnCoords[2]);

            while (!topLocation.getBlock().isLiquid() && topLocation.getBlock().getType() != Material.AIR
                    && topLocation.getBlock().getType() != Material.CAVE_AIR  && topLocation.getBlock().getType() != Material.VOID_AIR) {
                topLocation.setY(topLocation.getY() + 1);
            }

            return topLocation;
        }
    }

    private static void setOuterLayer(Material material, Location location) {
        int radiusSquared = (int) Math.pow(2, 2);

        for (int xOffset = -2; xOffset <= 2; xOffset++) {
            for (int yOffset = -2; yOffset <= 2; yOffset++) {
                for (int zOffset = -2; zOffset <= 2; zOffset++) {
                    int distanceSquared = xOffset * xOffset + yOffset * yOffset + zOffset * zOffset;

                    if (distanceSquared <= radiusSquared) {
                        Location offsetLocation = location.clone().add(xOffset, yOffset, zOffset);
                        if (location == offsetLocation || (Math.abs(xOffset) == Math.abs(yOffset) && Math.abs(yOffset) == Math.abs(zOffset))) continue;
                        offsetLocation.getBlock().setType(material);
                    }
                }
            }
        }
    }
}
