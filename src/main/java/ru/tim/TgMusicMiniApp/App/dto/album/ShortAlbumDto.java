package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * DTO for {@link ru.tim.TgMusicMiniApp.App.entity.Album.Album}
 */

@Value
public class ShortAlbumDto {

    @NotNull
    String id;

    @NotNull
    String name;
}