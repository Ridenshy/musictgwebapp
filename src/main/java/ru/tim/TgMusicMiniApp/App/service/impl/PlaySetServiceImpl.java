package ru.tim.TgMusicMiniApp.App.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.App.entity.enums.TypeName;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;
import ru.tim.TgMusicMiniApp.App.entity.settings.Settings;
import ru.tim.TgMusicMiniApp.App.entity.track.Track;
import ru.tim.TgMusicMiniApp.App.repo.PlaySetRepository;
import ru.tim.TgMusicMiniApp.App.repo.TrackRepository;
import ru.tim.TgMusicMiniApp.App.repo.SettingsRepository;
import ru.tim.TgMusicMiniApp.App.service.PlaySetService;

import java.util.*;


@Service
@RequiredArgsConstructor
public class PlaySetServiceImpl implements PlaySetService {

    private final PlaySetRepository playSetRepository;
    private final SettingsRepository settingsRepository;
    private final TrackRepository trackRepository;
    private final Random random = new Random();

    @Override
    public void generateStandardPlaySet(Long userId) {
        dropTracksFromPlaySet(userId);
        Settings settings = settingsRepository
                .getSettings(userId, TypeName.STANDARD);
        List<Track> trackList;

        switch (settings.getTypeType()) {
            case STRAIGHT:
                trackList = trackRepository.getAllTracks(userId);
                break;

            case BACK:
                trackList = trackRepository.getAllTracksDesc(userId);
                break;

            case RANDOM:
                trackList = shuffleTracks(trackRepository.getAllTracks(userId));
                break;

            default:
                throw new IllegalArgumentException("Unknown type");
        }
        PlaySet playSet = playSetRepository.getPlaySet(userId);
        playSet.setTracks(trackList);
        playSet.setAlreadyPlayed(0);
        playSetRepository.save(playSet);
    }

    @Override
    public void generateStartWithPlaySet(Long userId) {

    }

    @Override
    public void generatePackPlaySet(Long userId) {

    }

    @Override
    public void updateAlreadyPlayed(Long userId, Integer newAmount, Integer newLastAmount) {
        playSetRepository.updateAlreadyPlayedAmount(userId, newAmount, newLastAmount);
    }

    @Override
    public PlaySet getPlaySet(Long userId) {
        return playSetRepository.getPlaySet(userId);
    }

    @Override
    public void createNewUserPlaySet(Long userId) {
        if(!isPlaySetExists(userId)){
            PlaySet playSet = PlaySet.builder()
                    .alreadyPlayed(0)
                    .lastDropAmount(0)
                    .tgUserId(userId)
                    .build();
            playSetRepository.save(playSet);
        }
    }

    private void dropTracksFromPlaySet(Long userId) {
        Optional<PlaySet> playSet = playSetRepository.findById(userId);
        playSet.ifPresent(set -> set.getTracks().clear());
    }

    private boolean isPlaySetExists(Long userId){
        return playSetRepository.existsByTgUserId(userId);
    }

    private List<Track> shuffleTracks(List<Track> original) {
        List<Track> copy = new ArrayList<>(original);
        List<Track> randomized = new ArrayList<>(copy.size());

        while (!copy.isEmpty()) {
            int idx = random.nextInt(copy.size());
            randomized.add(copy.remove(idx));
        }
        return randomized;
    }
}
