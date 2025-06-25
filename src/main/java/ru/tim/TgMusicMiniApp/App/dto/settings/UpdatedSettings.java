package ru.tim.TgMusicMiniApp.App.dto.settings;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

/**
 * DTO for {@link ru.tim.TgMusicMiniApp.App.entity.settings.Settings}
 */
@Value
public class UpdatedSettings {

    @NotNull
    String tgUserId;

    @NotNull
    TypeName typeName;

    @NotNull
    TypeType typeType;

    @NotNull
    @Min(value = 1)
    @Max(value = 50)
    Integer amount;
}