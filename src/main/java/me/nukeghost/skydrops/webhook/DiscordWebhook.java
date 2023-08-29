package me.nukeghost.skydrops.webhook;

import com.itsradiix.DiscordWebHookMessage;
import com.itsradiix.embed.Embed;

import static me.nukeghost.skydrops.SkyDrops.webhookURL;

public class DiscordWebhook {
    public static void sendDiscordWebhook(String title, String decription) {
        DiscordWebHookMessage message = new DiscordWebHookMessage.Builder()
                .username("SkyDrops")
                .avatar_url("https://media.discordapp.net/attachments/1123977501480329247/1146170519041880065/Sky_Drops.jpg")
                .embed(new Embed.Builder()
                        .title(title)
                        .description(decription)
                        .color("#10FBBB")
                        .build())
                .build();

        DiscordWebHookMessage.sendMessage(webhookURL, message);
    }
}
