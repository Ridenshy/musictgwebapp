package ru.tim.TgMusicMiniApp.App.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.settings.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.SettingsMapper;
import ru.tim.TgMusicMiniApp.App.dto.settings.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.settings.UpdatedSettings;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.repo.SettingsRepository;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;
    private final SettingsMapper settingsMapper;

    @Override
    public List<SettingsDto> getAllUserSettings(Long userId) {
        return settingsRepository.getAllUserSettings(userId)
                .stream().map(settingsMapper::toSettingsDto).toList();
    }



    @Override
    public SettingsDto getSettingsDto(Long userId, TypeName typeName) {
        return settingsMapper.toSettingsDto(
                settingsRepository.getSettings(userId, typeName)
        );
    }

    @Override
    public SettingsDto setActiveSettings(SettingsDto settingsDto) {
        settingsRepository.setActiveSettings(settingsDto.getTgUserId(), settingsDto.getTypeName());
        return settingsMapper.toSettingsDto(
                settingsRepository.getActiveSettings(settingsDto.getTgUserId())
        );
    }

    @Override
    public BotSettingsDto getBotSettingsDto(Long userId) {
        return settingsMapper.toBotSettingsDto(
                settingsRepository.getActiveSettings(userId)
        );
    }

    @Override
    public Settings getTypeSettings(Long userId, TypeName typeName) {
        return settingsRepository.getSettings(userId, typeName);
    }

    @Override
    public SettingsDto updateSettings(UpdatedSettings settings) {
        settingsRepository.updateSettings(settings.getTgUserId(),
                settings.getTypeName(),
                settings.getTypeType(),
                settings.getAmount());
        return settingsMapper.toSettingsDto(
                settingsRepository.getSettings(settings.getTgUserId(), settings.getTypeName())
        );
    }

    @Override
    public void createTypesSettings(Long userId) {
        if(!settingsRepository.existsByTgUserId(userId)){
            Settings settingsStandard = Settings.builder()
                    .typeName(TypeName.STANDARD)
                    .typeType(TypeType.STRAIGHT)
                    .active(true)
                    .tgUserId(userId)
                    .amount(10)
                    .build();
            settingsRepository.save(settingsStandard);
            Settings settingsStartWith = Settings.builder()
                    .typeName(TypeName.START_WITH)
                    .typeType(TypeType.STRAIGHT)
                    .tgUserId(userId)
                    .amount(10)
                    .active(false)
                    .build();
            settingsRepository.save(settingsStartWith);
            Settings settingsPack = Settings.builder()
                    .typeName(TypeName.PACK)
                    .typeType(TypeType.STRAIGHT)
                    .tgUserId(userId)
                    .amount(10)
                    .active(false)
                    .build();
            settingsRepository.save(settingsPack);
        }
    }
}
