package ru.tim.TgMusicMiniApp.App.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.SettingsMapper;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.repo.SettingsRepository;
import ru.tim.TgMusicMiniApp.App.service.SettingsService;
import ru.tim.TgMusicMiniApp.App.service.TypeSettingsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    SettingsRepository settingsRepository;
    SettingsMapper settingsMapper;

    @Override
    public void createSettings() {

    }

    @Override
    public SettingsDto getUserSettings(Long userId) {
        Optional<Settings> settings = settingsRepository.findById(userId);
        if(settings.isPresent()){
            return settingsMapper.toSettingsDto(settings.get());
        }else{
            //createSettings
            return null;
        }
    }

    @Override
    public BotSettingsDto getBotSettings(Long userId) {
        Optional<Settings> settings = settingsRepository.findById(userId);
        if(settings.isPresent()){
            return settingsMapper.toBotSettingsDto(settings.get());
        }
        return null;
    }


    @Override
    public void setTypeSettings(Long userId, String typeName) {
        settingsRepository.updateSettingsType(userId, typeName);
    }
}
