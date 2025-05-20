package ru.tim.TgMusicMiniApp.App.dto;

import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.enums.SettingsType;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

@Value
public class TypeSettingsDto {

    Long tgUserId;

    Integer amount;

    SettingsType typeName;

    TypeType typeType;
}
