package me.nukeghost.skydrops.commands;

import me.nukeghost.skydrops.commands.subcommands.AddCommand;
import me.nukeghost.skydrops.commands.subcommands.HelpCommand;
import me.nukeghost.skydrops.commands.subcommands.ReloadCommand;
import me.nukeghost.skydrops.commands.subcommands.SpawnCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();

    public CommandManager() {
        subcommands.add(new HelpCommand());
        //subcommands.add(new AddCommand());
        subcommands.add(new ReloadCommand());
        subcommands.add(new SpawnCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 0) {
                HelpCommand help = new HelpCommand();
                help.perform(p, args);
            } else if (args.length > 0) {
                boolean isValidSubcommand = false;
                for (int i = 0; i < this.getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(this.getSubCommands().get(i).getName())) {
                        isValidSubcommand = true;

                        if (this.getSubCommands().get(i).getPermissionNode() == null || p.hasPermission(this.getSubCommands().get(i).getPermissionNode())) {

                            this.getSubCommands().get(i).perform(p, args);

                        } else {
                            p.sendMessage(ChatColor.RED + "You do not have permission to run that command!");
                        }

                        break;
                    }
                }
                if (!isValidSubcommand) {
                    p.sendMessage(ChatColor.RED + "That is not a valid command.");
                    p.sendMessage(ChatColor.WHITE + "Do " + ChatColor.GREEN + "/lm help" + ChatColor.WHITE + " for more info.");
                }
                return true;
            }

        }

        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }

}
