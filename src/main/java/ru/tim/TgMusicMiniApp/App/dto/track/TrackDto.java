package ru.tim.TgMusicMiniApp.App.dto.track;


import lombok.Value;

/**
 * DTO for {@link ru.tim.TgMusicMiniApp.App.entity.track.Track}
 */
@Value
public class TrackDto {
    Long id;
    String title;
    String artist;
    String duration;
    Long listPlace;
}