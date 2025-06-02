package ru.tim.TgMusicMiniApp.telegram_bot.service;


import org.springframework.transaction.annotation.Transactional;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;

import java.util.List;


public interface BotMessageService {

    @Transactional(readOnly = true)
    List<BotMessage> getAllChatMessages(Long userId);

    @Transactional
    void deleteMessage(BotMessage botMessage);

    @Transactional
    void saveBotMessage(BotMessage botMessage);

    @Transactional(readOnly = true)
    List<BotMessage> getExpiredSoonMessages(Long userId);



}
