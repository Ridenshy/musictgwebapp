package ru.tim.TgMusicMiniApp.App.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.dto.track.TrackDto;
import ru.tim.TgMusicMiniApp.App.dto.mapper.TrackMapper;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.track.TgUserTrack;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.repo.TrackRepository;
import ru.tim.TgMusicMiniApp.App.service.TrackService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrackServiceImpl implements TrackService {

    private final TrackRepository trackRepository;

    private final TrackMapper mapper;
    private final TextEncryptor textEncryptor;

    @Override
    public void saveTgUserTrack(TgUserTrack track) {
        trackRepository.save(track);
    }

    @Override
    public List<TrackDto> getAllUserTracks(String userId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));
        List<Track> trackList = trackRepository.getAllTracks(decUserId);
        return trackList.stream()
                .map(this::mapTrack)
                .collect(Collectors.toList());
    }

    @Override
    public List<Track> getAllUserTracksEntity(Long userId) {
        return trackRepository.getAllTracks(userId);
    }


    @Override
    public List<TrackDto> deleteTrack(String trackId, String userId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));
        Long decTrackId = Long.parseLong(textEncryptor.decrypt(trackId));

        Track trackToDelete = trackRepository.findByIdAndTgUserId(decTrackId, decUserId);

        for (PlaySet playSet : new ArrayList<>(trackToDelete.getPlaySets())) {
            playSet.getTracks().remove(trackToDelete);
        }
        trackToDelete.getPlaySets().clear();

        int deletePos = trackToDelete.getListPlace();
        trackRepository.delete(trackToDelete);
        trackRepository.updatePlaceAfterDelete(decUserId, deletePos);

        return trackRepository.getAllTracks(decUserId).stream()
                .map(this::mapTrack)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackDto> updateListPlace(Integer newPlace, String trackId, String userId) {
        Long decUserId = Long.parseLong(textEncryptor.decrypt(userId));
        Long decTrackId = Long.parseLong(textEncryptor.decrypt(trackId));

        Track movedTrack = trackRepository.findByIdAndTgUserId(decTrackId, decUserId);
        Integer oldPos = movedTrack.getListPlace();

        List<Track> trackList = trackRepository.getAllTracks(decUserId);
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

        List<Track> updatedTracks = trackRepository.getAllTracks(decUserId);
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
        String encryptedTrackId = textEncryptor.encrypt(track.getId().toString());
        if(track instanceof TgUserTrack){
            return mapper.toTrackDto((TgUserTrack) track, encryptedTrackId);
        }
        else return null;
    }
}
