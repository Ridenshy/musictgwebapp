package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

public interface SettingsService {

    @Transactional
    void createSettings(Long userId);

    @Transactional(readOnly = true)
    SettingsDto getSettingsDto(Long userId);

    @Transactional(readOnly = true)
    Settings getSettingsEntity(Long userId);


    @Transactional
    void setTypeSettings(Long userId, String typeName);

}
