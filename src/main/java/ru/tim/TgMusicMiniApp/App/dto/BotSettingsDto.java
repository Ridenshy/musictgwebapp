package ru.tim.TgMusicMiniApp.App.dto;

import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;

@Value
public class BotSettingsDto {

    Integer amount;
    TypeName typeName;

}
