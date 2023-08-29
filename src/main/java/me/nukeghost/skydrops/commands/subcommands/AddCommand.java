package me.nukeghost.skydrops.commands.subcommands;

import me.nukeghost.skydrops.commands.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static me.nukeghost.skydrops.SkyDrops.plugin;
import static me.nukeghost.skydrops.utils.MessagingUtils.sendMessage;

public class AddCommand extends SubCommand {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Adds the currently held item to selected loot-set";
    }

    @Override
    public String getSyntax() {
        return "/sd add <loot-set>";
    }

    @Override
    public String getPermissionNode() {
        return "sd.manage";
    }

    @Override
    public void perform(Player p, String[] args) {
        if (p.getInventory().getItemInMainHand() == null) {
            sendMessage(p, ChatColor.YELLOW + "You must have something in your hand to use this command!");
            return;
        }

        ItemStack item = p.getInventory().getItemInMainHand().clone();

        String itemCode = item.getType() + "@" + item.getAmount() + "@1";

        List<String> loadedList = plugin.getConfig().getStringList(args[1] + ".loots");
        loadedList.add(itemCode);
        plugin.getConfig().set(args[1] + ".loots", loadedList);

        ClickEvent ce = new ClickEvent(ClickEvent.Action.OPEN_URL, itemCode);
        ComponentBuilder componentBuilder = new ComponentBuilder(new TextComponent(itemCode)).event(ce);
        p.spigot().sendMessage(componentBuilder.create());
    }
}
