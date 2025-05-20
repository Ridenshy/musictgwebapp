package ru.tim.TgMusicMiniApp.App.service.impl;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.TypeSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;
import ru.tim.TgMusicMiniApp.App.entity.settings.TypeSettings;
import ru.tim.TgMusicMiniApp.App.repo.TypeSettingsRepository;
import ru.tim.TgMusicMiniApp.App.service.TypeSettingsService;

@Service
@RequiredArgsConstructor
public class TypeSettingsServiceImpl implements TypeSettingsService {

    private final TypeSettingsRepository typeSettingsRepository;

    @Override
    public TypeSettingsDto getTypeSettingsDto(Long userId, String typeName) {
        return null;
    }

    @Override
    public TypeSettings getTypeSettings(Long userId, SettingsType settingsType) {
        return typeSettingsRepository.getTypeSettingsForSettings(userId, settingsType);
    }

    @Override
    public void updateTypeAmount(Long userId, String typeName, Integer newAmount) {

    }

    @Override
    public void updateTypeType(Long userId, String typeName, String newTypeType) {

    }

    @Override
    public void createTypesSettings(Long userId) {
        if(!typeSettingsRepository.existsByTgUserId(userId)){
            TypeSettings typeSettingsStandard = TypeSettings.builder()
                    .typeName(SettingsType.STANDARD)
                    .typeType(TypeType.STRAIGHT)
                    .tgUserId(userId)
                    .amount(10)
                    .build();
            typeSettingsRepository.save(typeSettingsStandard);
            TypeSettings typeSettingsStartWith = TypeSettings.builder()
                    .typeName(SettingsType.START_WITH)
                    .tgUserId(userId)
                    .amount(10)
                    .build();
            typeSettingsRepository.save(typeSettingsStartWith);
            TypeSettings typeSettingsPack = TypeSettings.builder()
                    .typeName(SettingsType.PACK)
                    .tgUserId(userId)
                    .build();
            typeSettingsRepository.save(typeSettingsPack);
        }
    }
}
