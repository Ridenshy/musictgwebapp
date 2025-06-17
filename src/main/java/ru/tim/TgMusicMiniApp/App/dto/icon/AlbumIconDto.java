package ru.tim.TgMusicMiniApp.App.dto.icon;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AlbumIconDto {

    @NotNull
    String path;

}
