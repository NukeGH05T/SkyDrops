package me.nukeghost.skydrops.commands.subcommands;

import me.nukeghost.skydrops.commands.CommandManager;
import me.nukeghost.skydrops.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    public String getName(){
        return "help";
    }

    @Override
    public String getDescription(){
        return "Show all of the commands for the plugin";
    }

    @Override
    public String getSyntax(){
        return "/sd help";
    }

    @Override
    public String getPermissionNode() {
        return null;
    }

    @Override
    public void perform(Player p, String[] args) {

        CommandManager commandManager = new CommandManager();
        p.sendMessage(ChatColor.YELLOW + "===================="+ChatColor.DARK_AQUA + ChatColor.BOLD +"Sky" +
                ChatColor.AQUA + "Drops" + ChatColor.YELLOW + "===================");
        p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "< >" + ChatColor.GREEN + " Required field | " +
                ChatColor.DARK_RED + "" + ChatColor.BOLD + "[ ]" + ChatColor.GREEN + " Optional field");
        for (int i = 0; i < commandManager.getSubCommands().size(); i++){
            if (commandManager.getSubCommands().get(i).getPermissionNode() == null ||
                    p.hasPermission(commandManager.getSubCommands().get(i).getPermissionNode())) {
                p.sendMessage(ChatColor.GREEN + commandManager.getSubCommands().get(i).getSyntax() + ChatColor.GRAY + " - " + ChatColor.WHITE + ChatColor.ITALIC + commandManager.getSubCommands().get(i).getDescription());
            }
        }
        p.sendMessage(ChatColor.YELLOW + "================================================");

    }

}
