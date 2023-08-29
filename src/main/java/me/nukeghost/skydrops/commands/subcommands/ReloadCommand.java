package me.nukeghost.skydrops.commands.subcommands;

import me.nukeghost.skydrops.SkyDrops;
import me.nukeghost.skydrops.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin config";
    }

    @Override
    public String getSyntax() {
        return "/sd reload";
    }

    @Override
    public String getPermissionNode() {
        return "sd.manage";
    }

    @Override
    public void perform(Player p, String[] args) {
        String prefix = SkyDrops.plugin.prefix;
        try {
            SkyDrops.plugin.reloadConfig();

            p.sendMessage(ChatColor.GOLD + prefix + " Configuration reloaded successfully.");
        } catch (Exception ex) {
            System.out.println(ex);
            p.sendMessage(ChatColor.DARK_RED + prefix + " Failed to reload config! Check console for details.");
        }
    }
}
