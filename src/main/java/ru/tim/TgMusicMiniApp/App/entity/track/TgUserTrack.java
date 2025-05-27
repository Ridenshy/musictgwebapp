package ru.tim.TgMusicMiniApp.App.entity.track;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class TgUserTrack extends Track{ //entity of track, dropped by user in tg bots chat

    @Column(nullable = false)
    private String audioTgId; //telegram message id

}
