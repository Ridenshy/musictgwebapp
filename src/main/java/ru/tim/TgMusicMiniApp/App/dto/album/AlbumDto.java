package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.annotation.hex_color.HexColor;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;

import java.util.List;

/**
 * DTO for {@link Album}
 */
@Value
public class AlbumDto {
    @NotNull
    String id;
    @NotNull
    String tgUserId;
    @NotNull
    @Size(min = 1, max = 50)
    String name;

    @NotNull
    String albumIconPath;

    @NotNull
    @HexColor
    String hexColor1;
    @NotNull
    @HexColor
    String hexColor2;
    @NotNull
    @HexColor
    String hexColor3;

    @Positive
    Integer playListPlace;

    List<String> trackIds;
}