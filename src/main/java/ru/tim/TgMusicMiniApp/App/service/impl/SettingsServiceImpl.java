package ru.tim.TgMusicMiniApp.App.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.SettingsMapper;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.repo.SettingsRepository;
import ru.tim.TgMusicMiniApp.App.repo.TypeSettingsRepository;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;
    private final TypeSettingsRepository typeSettingsRepository;
    private final SettingsMapper settingsMapper;

    @Override
    public void createSettings(Long userId) {
        if(!settingsRepository.existsByTgUserId(userId))
        {
            Settings settings = Settings.builder()
                    .tgUserId(userId)
                    .typeSettings(typeSettingsRepository.getTypeSettingsForSettings(userId, SettingsType.STANDARD))
                    .build();
            settingsRepository.save(settings);
        }
    }

    @Override
    public SettingsDto getSettingsDto(Long userId) {
        Optional<Settings> settings = settingsRepository.findById(userId);
        //createSettings
        return settings.map(value -> settingsMapper.toSettingsDto(value)).orElse(null);
    }

    @Override
    public Settings getSettingsEntity(Long userId) {
        return settingsRepository.getSettingsEntity(userId);
    }

    @Override
    public void setTypeSettings(Long userId, String typeName) {
        settingsRepository.updateSettingsType(userId, typeName);
    }
}
