package ru.tim.TgMusicMiniApp.telegram_bot.configuraction;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bot")
public record BotProperty(@NotEmpty String token, @NotEmpty String name) {
}
