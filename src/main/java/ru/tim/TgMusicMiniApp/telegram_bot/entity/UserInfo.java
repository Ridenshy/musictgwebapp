package ru.tim.TgMusicMiniApp.telegram_bot.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class UserInfo {

    @Id
    @Column(unique = true, nullable = false)
    Long tgUserId;
}
