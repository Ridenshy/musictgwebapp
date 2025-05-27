package ru.tim.TgMusicMiniApp.App.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.tim.TgMusicMiniApp.App.dto.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrackMapper {

    TrackDto toTrackDro(TgUserTrack track);

}