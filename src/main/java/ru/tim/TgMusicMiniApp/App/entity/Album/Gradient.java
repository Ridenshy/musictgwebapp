package ru.tim.TgMusicMiniApp.App.entity.Album;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Gradient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long telegramId;

    @Column(nullable = false)
    private String hexColor1;

    @Column(nullable = false)
    private String hexColor2;

    @Column(nullable = false)
    private String hexColor3;

}
