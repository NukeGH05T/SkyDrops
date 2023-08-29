package me.nukeghost.skydrops.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderUtils {

    public static String parsePlaceholders(String input, String coords) {
        Pattern pattern = Pattern.compile("\\{([A-Z_]+)\\}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String placeholder = matcher.group(0); // e.g., {PLAYER_NAME}, {PLAYER_X}, etc.
            String placeholderKey = matcher.group(1); // e.g., PLAYER_NAME, PLAYER_X, etc.

            // Perform actions based on the placeholder key
            switch (placeholderKey) {
                case "DROP_LOC":
                    // Get the player's display name
                    input = input.replace(placeholder, coords);
                    break;
            }
        }
        return input;
    }
}
