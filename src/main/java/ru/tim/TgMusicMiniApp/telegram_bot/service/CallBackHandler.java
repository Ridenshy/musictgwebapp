package ru.tim.TgMusicMiniApp.telegram_bot.service;

public interface CallBackHandler {

    void sendPlaySetTracks(Long userId, String callbackData);

    void sendAudio(Long userId, String fileId);

    void sendMoveKeyboard(Long userId);

    void cleanupChat(Long userId);

}
