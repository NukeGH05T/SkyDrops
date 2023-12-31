package me.nukeghost.skydrops;

import me.nukeghost.skydrops.commands.CommandManager;
import me.nukeghost.skydrops.commands.TabComplete;
import me.nukeghost.skydrops.data.LootSet;
import me.nukeghost.skydrops.handlers.ClusterSpawnHandler;
import me.nukeghost.skydrops.tasks.LootUpdateTask;
import me.nukeghost.skydrops.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public final class SkyDrops extends JavaPlugin {

    public static SkyDrops plugin;
    public static String prefix;
    public static List<LootSet> lootSets = new ArrayList<>();

    public static String webhookURL;
    public static boolean isWebhookOn = false;
    public static String username;
    public static String avatarURL;
    public static String color;
    public static String format;

    @Override
    public void onEnable() {
        plugin = this;

        Metrics metrics = new Metrics(this, 19679);
        prefix = ChatColor.translateAlternateColorCodes('&', SkyDrops.plugin.getConfig().getString("prefix"));

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        if (getConfig().getBoolean("discord-webhook.enabled")) {
            webhookURL = getConfig().getString("discord-webhook.url");
            isWebhookOn = getConfig().getBoolean("discord-webhook.enabled");
            username = getConfig().getString("discord-webhook.username");
            avatarURL = getConfig().getString("discord-webhook.avatar-url");
            color = getConfig().getString("discord-webhook.color");
            format = getConfig().getString("discord-webhook.format");
        }

        getCommand("skydrops").setExecutor(new CommandManager());
        getCommand("skydrops").setTabCompleter(new TabComplete());

        loadLootSets();
        initializeLootSets();

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[SkyDrops] This plugin is licensed to " + ChatColor.GREEN + "%%__USERNAME__%%");
    }

    private void loadLootSets() {
        List<String> activeSetIDs = getConfig().getStringList("active-loot-sets");

        for (String setID : activeSetIDs) {
            System.out.println("Loaded " + setID);
            LootSet lootSet = new LootSet(setID);
            lootSets.add(lootSet);
        }
    }

    private void initializeLootSets() {
        for (LootSet lootSet : lootSets) {
            ClusterSpawnHandler.spawnCluster(lootSet, lootSet.getOuterLayer(), lootSet.isCarve());
        }

        new LootUpdateTask(plugin).runTaskTimer(plugin, 20, 20);
    }
}
