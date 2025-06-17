package ru.tim.TgMusicMiniApp.App.dto.track;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

/**
 * DTO for {@link ru.tim.TgMusicMiniApp.App.entity.track.Track}
 */
@Value
public class TrackDto {
    @NotNull
    String id;

    @NotNull
    @Size(min = 1, max = 50)
    String title;

    @NotNull
    @Size(min = 1, max = 50)
    String artist;

    @NotNull
    String duration;

    @NotNull
    @Positive
    Long listPlace;
}