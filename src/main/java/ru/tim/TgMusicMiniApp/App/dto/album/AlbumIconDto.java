package ru.tim.TgMusicMiniApp.App.dto.album;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AlbumIconDto {

    @NotNull
    String id;

    @NotNull
    String tgUserId;

    @NotNull
    String path;
}
