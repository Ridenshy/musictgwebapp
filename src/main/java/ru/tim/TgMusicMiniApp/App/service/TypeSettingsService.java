package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.dto.TypeSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;
import ru.tim.TgMusicMiniApp.App.entity.settings.TypeSettings;

public interface TypeSettingsService {

    @Transactional(readOnly = true)
    TypeSettingsDto getTypeSettingsDto(Long userId, String typeName);

    @Transactional(readOnly = true)
    TypeSettings getTypeSettings(Long userId, SettingsType settingsType);

    @Transactional
    void updateTypeAmount(Long userId, String typeName, Integer newAmount);

    @Transactional
    void updateTypeType(Long userId, String typeName, String newTypeType);

    @Transactional
    void createTypesSettings(Long userId);


}
