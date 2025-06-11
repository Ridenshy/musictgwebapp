package ru.tim.TgMusicMiniApp.App.configuration;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "encrypt")
public record EncryptionProp(@NotEmpty String secretKey,
                             @NotEmpty String salt) {
}
