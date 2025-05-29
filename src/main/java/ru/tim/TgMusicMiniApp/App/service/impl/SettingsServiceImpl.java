package ru.tim.TgMusicMiniApp.App.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.SettingsMapper;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.repo.SettingsRepository;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;
    private final SettingsMapper settingsMapper;

    @Override
    public SettingsDto getSettingsDto(Long userId, String typeName) {
        return null;
    }

    @Override
    public BotSettingsDto getBotSettingsDto(Long userId) {
        return settingsMapper.toBotSettingsDto(settingsRepository.getActiveSettings(userId));
    }

    @Override
    public Settings getTypeSettings(Long userId, TypeName typeName) {
        return settingsRepository.getSettings(userId, typeName);
    }

    @Override
    public void updateTypeAmount(Long userId, String typeName, Integer newAmount) {

    }

    @Override
    public void updateTypeType(Long userId, String typeName, String newTypeType) {

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
