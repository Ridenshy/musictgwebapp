package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
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
    @Length(min = 1, max = 50)
    String name;

    AlbumIconDto icon;

    AlbumGradientDto gradient;

    @NotNull
    Boolean isIcon;

    @Positive
    Integer playListPlace;

    List<String> trackIds;
}