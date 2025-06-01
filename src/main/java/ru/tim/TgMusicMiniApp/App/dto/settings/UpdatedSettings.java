package ru.tim.TgMusicMiniApp.App.dto.settings;

import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

/**
 * DTO for {@link ru.tim.TgMusicMiniApp.App.entity.settings.Settings}
 */
@Value
public class UpdatedSettings {
    Long tgUserId;
    TypeName typeName;
    TypeType typeType;
    Integer amount;
}