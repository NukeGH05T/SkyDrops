package me.nukeghost.skydrops.webhook;

import com.itsradiix.DiscordWebHookMessage;
import com.itsradiix.embed.Embed;

import static me.nukeghost.skydrops.SkyDrops.*;

public class DiscordWebhook {
    public static void sendDiscordWebhook(String title, String decription) {
        DiscordWebHookMessage message = new DiscordWebHookMessage.Builder()
                .username(username == null ? "SkyDrops" : username)
                .avatar_url(avatarURL == null ? "https://media.discordapp.net/attachments/1123977501480329247/1146170519041880065/Sky_Drops.jpg" : avatarURL)
                .embed(new Embed.Builder()
                        .title(title)
                        .description(decription)
                        .color(color == null ? "#10FBBB" : color)
                        .build())
                .build();

        DiscordWebHookMessage.sendMessage(webhookURL, message);
    }
}
