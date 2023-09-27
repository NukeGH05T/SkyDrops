package me.nukeghost.skydrops.utils;

import me.nukeghost.skydrops.data.LootSet;
import org.bukkit.Location;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderUtils {

    public static String parsePlaceholders(String input, Location location, LootSet lootSet) {
        Pattern pattern = Pattern.compile("\\{([A-Z_]+)\\}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String placeholder = matcher.group(0); // e.g., {DROP_X}, {DROP_Y}, etc.
            String placeholderKey = matcher.group(1); // e.g., DROP_X, DROP_Y, etc.

            // Perform actions based on the placeholder key
            switch (placeholderKey) {
                case "DROP_X":
                    // Get the player's display name
                    input = input.replace(placeholder, String.valueOf(location.getX()));
                    break;
                case "DROP_Y":
                    // Get the player's display name
                    input = input.replace(placeholder, String.valueOf(location.getY()));
                    break;
                case "DROP_Z":
                    // Get the player's display name
                    input = input.replace(placeholder, String.valueOf(location.getZ()));
                    break;
                case "DROP_WORLD":
                    // Get the player's display name
                    input = input.replace(placeholder, location.getWorld().getName());
                    break;
                case "LOOT_ID":
                    // Get the player's display name
                    input = input.replace(placeholder, lootSet.getId());
                    break;
                case "NEXT_DROP":
                    // Get the player's display name
                    input = input.replace(placeholder, lootSet.remainingTime());
                    break;
            }
        }
        return input;
    }
}
