package me.nukeghost.skydrops.handlers;

import me.nukeghost.skydrops.data.LootSet;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static me.nukeghost.skydrops.SkyDrops.lootSets;

public class GenerationHandler {
    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1 - min) + min;
    }
    public static LootSet getRandomLootSet() {
        return lootSets.get(getRandomNumber(0, lootSets.size()) - 1);
    }

    public static ItemStack getRandomLootFromSet(LootSet lootSet) {
        return lootSet.getLoots().get(getRandomNumber(0, lootSet.getLootCount() - 1));
    }

    public static ItemStack getRandomLootFromRandomSet() {
        LootSet lootSet = getRandomLootSet();
        return lootSet.getLoots().get(getRandomNumber(0, lootSet.getLootCount() - 1));
    }
}
