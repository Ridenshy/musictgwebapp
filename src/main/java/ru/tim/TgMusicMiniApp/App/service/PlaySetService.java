package ru.tim.TgMusicMiniApp.App.service;


import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.entity.Album.Album;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;

import java.util.List;

public interface PlaySetService {

    @Transactional
    void generateStandardPlaySet(Long userId);

    @Transactional
    void generateStartWithPlaySet(Long userId, Long trackId );

    @Transactional
    void generatePackPlaySet(Long userId, List<Long> trackIds);

    @Transactional
    void generateAlbumPlaySet(Album album);

    @Transactional
    void updateAlreadyPlayed(Long userId, Integer newAmount, Integer newLastAmount);

    @Transactional(readOnly = true)
    PlaySet getPlaySet(Long userId);

    @Transactional
    void createNewUserPlaySet(Long userId);


}
