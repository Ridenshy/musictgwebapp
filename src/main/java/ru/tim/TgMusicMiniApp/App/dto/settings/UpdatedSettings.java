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
    TypeName typeName;
    TypeType typeType;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    Integer amount;
}