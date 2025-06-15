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
    List<TrackDto> getAllUserTracks(String userId);

    @Transactional(readOnly = true)
    List<Track> getAllUserTracksEntity(Long userId);

    @Transactional
    List<TrackDto> deleteTrack(String trackId, String userId);

    @Transactional
    List<TrackDto> updateListPlace(Integer newPlace, String trackId, String userId);

    @Transactional(readOnly = true)
    Integer getTracksAmount(Long userId);



}
