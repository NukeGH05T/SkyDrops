package me.nukeghost.skydrops.data;

import me.nukeghost.skydrops.handlers.ClusterSpawnHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static me.nukeghost.skydrops.SkyDrops.plugin;
import static me.nukeghost.skydrops.utils.ItemUtils.stringToItemStack;
import static org.bukkit.Bukkit.getLogger;

public class LootSet {
    private String id;
    private String title;
    private List<ItemStack> loots = new ArrayList<>();
    private int lootCount;
    private String notice;
    private int firstSpawnDelay;
    private Material outerLayer;
    private List<String> spawnRegions;
    private Location currentLocation;
    private long cooldownStart;
    private long cooldownTime;
    private boolean carve;

    public LootSet(String id) {
        this.id = id;
        this.title = plugin.getConfig().getString(id + ".title");
        this.notice = plugin.getConfig().getString(id + ".notice");
        this.firstSpawnDelay = plugin.getConfig().getInt(id + ".first-spawn-delay") * 20; //ticks
        this.cooldownTime = plugin.getConfig().getInt(id + ".repeat-spawn-delay") * 1000; //ms

        this.spawnRegions = plugin.getConfig().getStringList(id + ".active-spawn-regions");
        this.carve = plugin.getConfig().getBoolean(id + ".carve");

        this.outerLayer = Material.getMaterial(plugin.getConfig().getString(id + ".outer-layer-material"));
        if (outerLayer == null) {
            Bukkit.getLogger().severe("Outer layer material does not exist for " + id);
            outerLayer = Material.OBSIDIAN;
        }


        Set<String> lootStrings = plugin.getConfig().getConfigurationSection(id + ".loots").getKeys(false);
        for (String lootString : lootStrings) { //lootStrings = 1, 2, 3 ...
            String materialString = plugin.getConfig().getString(id + ".loots." + lootString + ".material");
            int materialAmount = plugin.getConfig().getInt(id + ".loots." + lootString + ".amount");
            List<String> materialEnchants = plugin.getConfig().getStringList(id + ".loots." + lootString + ".enchantments");

            ItemStack lootItemStack = stringToItemStack(materialString + "@" + materialAmount);

            for (String enchantmentString : materialEnchants) {
                String[] parts = enchantmentString.split("@");
                try {
                    lootItemStack.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(parts[0].toLowerCase())), Integer.parseInt(parts[1]));
                } catch (Exception ex) {
                    Bukkit.getLogger().severe("An error occured while applying level " + materialAmount + " " + enchantmentString + " for " + id);
                    Bukkit.getLogger().severe((Supplier<String>) ex);
                }
            }

            this.loots.add(lootItemStack);
        }
        this.lootCount = loots.size();

    }

    public void dropLoot() {
        cooldownStart = System.currentTimeMillis();
        System.out.println(this.getId() + " " + this.getTitle());
        ClusterSpawnHandler.spawnCluster(this, outerLayer, carve);
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public List<ItemStack> getLoots() {
        return loots;
    }

    public int getLootCount() {
        return lootCount;
    }

    public String getNotice() {
        return notice;
    }

    public int getFirstSpawnDelay() {
        return firstSpawnDelay;
    }

    public long getRepeatSpawnDelay() {
        return cooldownTime;
    }

    public Material getOuterLayer() {
        return outerLayer;
    }

    public List<String> getSpawnRegions() {
        return spawnRegions;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public long getCooldownStart() {
        return cooldownStart;
    }

    public void setCooldownStart(long cooldownStart) {
        this.cooldownStart = cooldownStart;
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(long cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean isCarve() {
        return carve;
    }
}
