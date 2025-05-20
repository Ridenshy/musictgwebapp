package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.dto.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

public interface SettingsService {

    @Transactional(readOnly = true)
    SettingsDto getSettingsDto(Long userId, String typeName);

    @Transactional(readOnly = true)
    BotSettingsDto getBotSettingsDto(Long userId);

    @Transactional(readOnly = true)
    Settings getTypeSettings(Long userId, TypeName typeName);

    @Transactional
    void updateTypeAmount(Long userId, String typeName, Integer newAmount);

    @Transactional
    void updateTypeType(Long userId, String typeName, String newTypeType);

    @Transactional
    void createTypesSettings(Long userId);


}
