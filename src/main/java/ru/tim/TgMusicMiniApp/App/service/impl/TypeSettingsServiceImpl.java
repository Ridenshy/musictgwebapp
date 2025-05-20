package ru.tim.TgMusicMiniApp.App.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.TypeSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;
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

    }
}
