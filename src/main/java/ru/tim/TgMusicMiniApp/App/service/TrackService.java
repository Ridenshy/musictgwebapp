package ru.tim.TgMusicMiniApp.App.service;


import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;

import java.util.List;

public interface TrackService {

    @Transactional
    void saveTgUserTrack(TgUserTrack track);

    @Transactional(readOnly = true)
    List<TrackDto> getAllUserTracks(Long userId);

    @Transactional(readOnly = true)
    List<Track> getAllUserTracksEntity(Long userId);

    @Transactional
    List<TrackDto> deleteTrack(Long trackId, Long userId);

    @Transactional
    List<TrackDto> updateListPlace(Integer newPlace, Long trackId, Long userId);

    @Transactional(readOnly = true)
    Integer getTracksAmount(Long userId);



}
