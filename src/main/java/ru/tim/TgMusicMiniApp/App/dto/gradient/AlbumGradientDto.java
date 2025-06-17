package ru.tim.TgMusicMiniApp.App.dto.gradient;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.annotation.hex_color.HexColor;
import ru.tim.TgMusicMiniApp.App.entity.Album.Gradient;

/**
 * DTO for {@link Gradient}
 */
@Value
public class AlbumGradientDto {

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