package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

@Value
public class UpdatedAlbumDto {
    @NotNull
    String id;

    @NotNull
    @Length(min = 1, max = 50)
    String name;

    String gradientId;

    String iconId;

}
