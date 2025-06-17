package ru.tim.TgMusicMiniApp.App.dto.gradient;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.annotation.hex_color.HexColor;

@Value
public class UpdatedGradientDto {

    @NotNull
    String id;

    @NotNull
    @HexColor
    String hexColor1;

    @NotNull
    @HexColor
    String hexColor2;

    @NotNull
    @HexColor
    String hexColor3;

}
