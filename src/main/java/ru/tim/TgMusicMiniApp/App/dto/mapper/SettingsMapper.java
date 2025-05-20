package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tim.TgMusicMiniApp.App.dto.SettingsDto;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SettingsMapper {

    SettingsDto toSettingsDto(Settings settings);

    @Mapping(target = "amount", source = "typeSettings.amount")
    @Mapping(target = "typeName", source = "typeSettings.typeName")
    BotSettingsDto toBotSettingsDto(Settings settings);

}
