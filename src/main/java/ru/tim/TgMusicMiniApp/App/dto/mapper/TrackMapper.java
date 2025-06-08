package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    @Mapping(target = "duration", expression = "java(formatDuration(track.getDuration()))")
    TrackDto toTrackDto(TgUserTrack track);

    default String formatDuration(Integer duration){
        if(duration == null){return null;}
        return String.format("%02d:%02d", duration/60, duration%60);
    }

}