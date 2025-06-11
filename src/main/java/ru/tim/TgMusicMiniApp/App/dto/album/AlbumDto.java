package ru.tim.TgMusicMiniApp.App.dto.album;

import lombok.Value;

import java.util.List;

/**
 * DTO for {@link ru.tim.TgMusicMiniApp.App.entity.playset.Album}
 */
@Value
public class AlbumDto {
    Long id;
    Long tgUserId;
    String name;
    Long albumIconId;
    Integer playListPlace;
    List<Long> trackIds;
}