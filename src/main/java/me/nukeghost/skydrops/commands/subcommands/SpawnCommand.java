package me.nukeghost.skydrops.commands.subcommands;

import me.nukeghost.skydrops.commands.SubCommand;
import me.nukeghost.skydrops.data.LootSet;
import me.nukeghost.skydrops.handlers.ClusterSpawnHandler;
import org.bukkit.entity.Player;

import static me.nukeghost.skydrops.SkyDrops.lootSets;

public class SpawnCommand extends SubCommand {
    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Spawn the specified drop";
    }

    @Override
    public String getSyntax() {
        return "/sd spawn <loot-set>";
    }

    @Override
    public String getPermissionNode() {
        return "sd.manage";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (args.length < 2) return;

        for (LootSet lootSet : lootSets) {
            if (lootSet.getId().equalsIgnoreCase(args[1])) {
                lootSet.dropLoot();
            }
        }
    }
}
