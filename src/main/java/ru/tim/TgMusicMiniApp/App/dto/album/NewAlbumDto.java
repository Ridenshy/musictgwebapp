package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;

/**
 * DTO for {@link Album}
 */
@Value
public class NewAlbumDto {

    @NotNull
    String tgUserId;

    @NotNull
    @Length(min = 1, max = 50)
    String name;

    String gradientId;

    String iconId;

}