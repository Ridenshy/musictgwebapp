package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.App.entity.playset.PlaySet;

public interface PlaySetService {

    @Transactional
    void generateStandardPlaySet(Long userId);

    @Transactional
    void generateStartWithPlaySet(Long userId);

    @Transactional
    void generatePackPlaySet(Long userId);

    @Transactional
    void updateAlreadyPlayed(Long userId, Integer newAmount);

    @Transactional(readOnly = true)
    PlaySet getPlaySet(Long userId);

    @Transactional
    void createNewUserPlaySet(Long userId);


}
