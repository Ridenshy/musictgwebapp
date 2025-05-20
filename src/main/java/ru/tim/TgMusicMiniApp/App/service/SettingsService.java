package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.BotSettingsDto;

public interface SettingsService {

    @Transactional
    void createSettings();

    @Transactional(readOnly = true)
    SettingsDto getUserSettings(Long userId);

    @Transactional(readOnly = true)
    BotSettingsDto getBotSettings(Long userId);

    @Transactional
    void setTypeSettings(Long userId, String typeName);

}
