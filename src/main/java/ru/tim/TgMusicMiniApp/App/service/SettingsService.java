package ru.tim.TgMusicMiniApp.App.service;


import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.dto.settings.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.settings.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.settings.UpdatedSettings;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

import java.util.List;

public interface SettingsService {

    @Transactional(readOnly = true)
    List<SettingsDto> getAllUserSettings(String userId);

    @Transactional(readOnly = true)
    SettingsDto getSettingsDto(Long userId, TypeName typeName);

    @Transactional
    SettingsDto setActiveSettings(SettingsDto settingsDto);

    @Transactional(readOnly = true)
    BotSettingsDto getBotSettingsDto(Long userId);

    @Transactional(readOnly = true)
    Settings getTypeSettings(Long userId, TypeName typeName);

    @Transactional
    SettingsDto updateSettings(UpdatedSettings settings);

    @Transactional
    void createTypesSettings(Long userId);


}
