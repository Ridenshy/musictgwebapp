package ru.tim.TgMusicMiniApp.App.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.TrackDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.TrackMapper;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.repo.TrackRepository;
import ru.tim.TgMusicMiniApp.App.service.TrackService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;
    private final TrackMapper mapper;

    @Override
    public void saveTgUserTrack(TgUserTrack track) {
        trackRepository.save(track);
    }

    @Override
    public List<TrackDto> getAllUserTracks(Long userId) {
        List<Track> trackList = trackRepository.getAllTracks(userId);
        return trackList.stream()
                .map(this::mapTrack)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getAllUserTracksEntity(Long userId) {
        return trackRepository.getAllTracks(userId);
    }


    @Override
    public List<TrackDto> deleteTrack(Long trackId, Long userId) {
        Track trackToDelete = trackRepository.findByIdAndTgUserId(trackId, userId);
        int deletePos = trackToDelete.getListPlace();
        trackRepository.delete(trackToDelete);
        trackRepository.updatePlaceAfterDelete(userId, deletePos);
        List<Track> updatedTracks = trackRepository.getAllTracks(userId);
        return updatedTracks.stream()
                .map(this::mapTrack)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackDto> updateListPlace(Integer newPlace, Long trackId, Long userId) {
        Track movedTrack = trackRepository.findByIdAndTgUserId(trackId, userId);
        Integer oldPos = movedTrack.getListPlace();

        List<Track> trackList = trackRepository.getAllTracks(userId);
        if(oldPos < newPlace){
            trackList.stream()
                    .filter(track -> track.getListPlace() > oldPos && track.getListPlace() <= newPlace)
                    .forEach(track -> {
                        track.setListPlace(track.getListPlace() - 1);
                        trackRepository.save(track);
                    });

        }else if(oldPos > newPlace){
            trackList.stream()
                    .filter(track -> track.getListPlace() < oldPos && track.getListPlace() >= newPlace)
                    .forEach(track -> {
                        track.setListPlace(track.getListPlace() + 1);
                        trackRepository.save(track);
                    });
        }
        movedTrack.setListPlace(newPlace);
        trackRepository.save(movedTrack);

        List<Track> updatedTracks = trackRepository.getAllTracks(userId);
        return updatedTracks.stream()
                .map(this::mapTrack)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getTracksAmount(Long userId) {
        return trackRepository.getLastListIndex(userId);
    }


    //метод чтобы замапить все треки в один лист Dto для фронта
    public TrackDto mapTrack(Track track){
        if(track instanceof TgUserTrack){
            return mapper.toTrackDro((TgUserTrack) track);
        }
        else return null;
    }
}
