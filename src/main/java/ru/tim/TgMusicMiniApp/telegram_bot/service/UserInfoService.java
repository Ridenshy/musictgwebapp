package ru.tim.TgMusicMiniApp.telegram_bot.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserInfoService {

    @Transactional(readOnly = true)
    boolean isUserExists(Long userId);

    @Transactional
    void addNewUser(Long userId);

    @Transactional(readOnly = true)
    List<Long> getAllUniqueUserIds();

}
