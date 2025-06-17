package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;
import ru.tim.TgMusicMiniApp.App.dto.gradient.AlbumGradientDto;
import ru.tim.TgMusicMiniApp.App.dto.icon.AlbumIconDto;
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

    AlbumIconDto icon;

    AlbumGradientDto gradient;

    @Positive
    Integer playListPlace;

    List<String> trackIds;
}