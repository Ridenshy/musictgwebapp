package ru.tim.TgMusicMiniApp.App.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
@RequiredArgsConstructor
public class EncryptionConfig {

    private final EncryptionProp encryptorProp;

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.delux(encryptorProp.secretKey(), encryptorProp.salt());
    }

}
