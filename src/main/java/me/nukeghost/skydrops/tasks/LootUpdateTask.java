package me.nukeghost.skydrops.tasks;

import me.nukeghost.skydrops.SkyDrops;
import org.bukkit.scheduler.BukkitRunnable;

import static me.nukeghost.skydrops.SkyDrops.lootSets;

public class LootUpdateTask extends BukkitRunnable {
    SkyDrops plugin;

    public LootUpdateTask(SkyDrops plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (int i = 0;i < lootSets.size(); i++) {
            long remainingCooldown = System.currentTimeMillis() - lootSets.get(i).getCooldownStart();
            if (remainingCooldown >= lootSets.get(i).getCooldownTime()) {
                lootSets.get(i).dropLoot();
            }
        }

    }


}
