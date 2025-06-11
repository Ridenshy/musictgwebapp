package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.*;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    @Mapping(target = "duration", expression = "java(formatDuration(track.getDuration()))")
    @Mapping(target = "id", expression = "java(encryptedTrackId)")
    TrackDto toTrackDto(TgUserTrack track, String encryptedTrackId);

    default String formatDuration(Integer duration){
        if(duration == null){return null;}
        return String.format("%02d:%02d", duration/60, duration%60);
    }

}