package me.nukeghost.skydrops.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getLogger;

public class ItemUtils {
    public static ItemStack stringToItemStack(String string) {
        String parts[] = string.split("@");
        
        if (Material.matchMaterial(parts[0].trim().toUpperCase()) == null) {
            getLogger().severe("Loot string error in " + string);
            return null;
        }

        return new ItemStack(Material.getMaterial(parts[0]), Integer.parseInt(parts[1]));
    }
}
