package ru.tim.TgMusicMiniApp.telegram_bot.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;
import ru.tim.TgMusicMiniApp.telegram_bot.repo.BotMessageRepository;
import ru.tim.TgMusicMiniApp.telegram_bot.service.BotMessageService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BotMessageServiceImpl implements BotMessageService {

    private final BotMessageRepository botMessageRepository;

    @Override
    public List<BotMessage> getAllChatMessages(Long userId) {
        return botMessageRepository.getAllBotMessages(userId);
    }

    @Override
    public void deleteMessage(BotMessage botMessage) {
        botMessageRepository.delete(botMessage);
    }

    @Override
    public void saveBotMessage(BotMessage botMessage) {
        botMessageRepository.save(botMessage);
    }

    @Override
    public List<BotMessage> getExpiredSoonMessages(Long userId) {
        LocalDateTime expireDate = LocalDateTime.now().minusHours(36);
        return botMessageRepository.getExpiredSoonMessages(userId, expireDate);
    }
}
