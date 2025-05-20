package ru.tim.TgMusicMiniApp.telegram_bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tim.TgMusicMiniApp.telegram_bot.entity.BotMessage;

import java.util.List;

public interface BotMessageRepository extends JpaRepository<BotMessage, Long> {

    @Query("SELECT bm FROM BotMessage bm WHERE bm.chatId = :userId")
    List<BotMessage> getAllBotMessages(Long userId);

}
