package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UpdatedAlbumDto {
    @NotNull
    String id;

    @NotNull
    @Size(min = 1, max = 50)
    String name;

    String gradientId;

    String iconId;

}
