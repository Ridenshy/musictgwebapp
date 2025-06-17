package ru.tim.TgMusicMiniApp.App.dto.icon;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class IconDto {

    @NotNull
    String id;

    @NotNull
    String tgUserId;

    @NotNull
    String path;

}
