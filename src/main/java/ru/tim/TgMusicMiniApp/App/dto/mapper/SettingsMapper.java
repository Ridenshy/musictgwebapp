package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tim.TgMusicMiniApp.App.dto.BotSettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SettingsMapper {

    BotSettingsDto toBotSettingsDto(Settings settings);
}
