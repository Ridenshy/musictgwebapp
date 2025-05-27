package ru.tim.TgMusicMiniApp.telegram_bot.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class BotMessage {
    @Id
    private Integer messageId;
    private Long chatId;
    private Boolean hasNextReply;
}
