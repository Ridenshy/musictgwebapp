package ru.tim.TgMusicMiniApp.App.dto;


import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

@Value
public class SettingsDto {

    Long tgUserId;

    Integer amount;

    TypeName typeName;

    TypeType typeType;

    Boolean active;
}
