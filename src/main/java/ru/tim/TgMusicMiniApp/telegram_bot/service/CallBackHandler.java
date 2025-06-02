package ru.tim.TgMusicMiniApp.telegram_bot.service;

import java.time.LocalDateTime;

public interface CallBackHandler {

    void sendPlaySetTracks(Long userId, String callbackData);

    void sendAudio(Long userId, String fileId, LocalDateTime now);

    void sendMoveKeyboard(Long userId, LocalDateTime now);

    void cleanupChat(Long userId);

}
