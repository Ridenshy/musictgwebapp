package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;

/**
 * DTO for {@link Album}
 */
@Value
public class NewAlbumDto {
    @NotNull
    String tgUserId;
    @NotNull
    @Size(min = 2, max = 50)
    String name;

    AlbumGradientDto gradient;

    @NotNull
    Boolean isIcon;
}