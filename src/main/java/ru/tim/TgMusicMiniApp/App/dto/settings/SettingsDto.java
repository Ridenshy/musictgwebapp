package ru.tim.TgMusicMiniApp.App.dto.settings;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeType;

@Value
public class SettingsDto {
    @NotNull
    String tgUserId;
    @NotNull
    TypeName typeName;
    @NotNull
    TypeType typeType;
    @NotNull
    Boolean active;

    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    Integer amount;
}
