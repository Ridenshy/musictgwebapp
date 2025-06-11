package ru.tim.TgMusicMiniApp.App.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tim.TgMusicMiniApp.App.dto.settings.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.settings.SettingsDto;
import ru.tim.TgMusicMiniApp.App.dto.settings.UpdatedSettings;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SettingsMapper {

    BotSettingsDto toBotSettingsDto(Settings settings);

    @Mapping(target = "tgUserId", expression = "java(encryptedUserId)")
    SettingsDto toSettingsDto(Settings settings, String encryptedUserId);

}
