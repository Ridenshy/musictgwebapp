package ru.tim.TgMusicMiniApp.App.service;

import org.springframework.transaction.annotation.Transactional;

public interface PlaySetService {

    @Transactional
    void generateStandardPlaySet(Long userId);

    @Transactional
    void generateStartWithPlaySet(Long userId);

    @Transactional
    void generatePackPlaySet(Long userId);

    @Transactional
    void updateAlreadyPlayed(Long userId);

    @Transactional
    void dropTracksFromPlaySet(Long userId);




}
